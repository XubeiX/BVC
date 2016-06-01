package ratajczak.artur.vob.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ratajczak.artur.vob.RV.ArticleModel;
import ratajczak.artur.vob.RV.ArticleRVAdapter;
import ratajczak.artur.bvc.R;

/**
 * Created by Artur Ratajczak on 23.05.16.
 */
public class VillainsOfBatmanListFragment extends Fragment implements SearchView.OnQueryTextListener, ArticleRVAdapter.ViewHolderClicks {
    private RecyclerView recyclerView;
    private List<ArticleModel> articleModelList;
    private ArticleRVAdapter adapter;
    private boolean sortedAlphabetically = false;
    private boolean showLiked = false;
    private VOBActionsListener mClickListener;
    private SwipeRefreshLayout swipeRefreshLayout;

    public interface VOBActionsListener{
        void onCardClicked(ArticleModel articleModel);
        void likeArticle(int articleID);
        void unlikeArticle(int articleID);
        void refreshList();
    }

    public VillainsOfBatmanListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_batman_villains_character_list, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        articleModelList = new ArrayList<>();
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_layout_fragment_BVCList);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(showLiked == false){
                    articleModelList.clear();
                    adapter.notifyDataSetChanged();
                    sortedAlphabetically = false;
                    mClickListener.refreshList();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        adapter = new ArticleRVAdapter(articleModelList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity;
        if(context instanceof Activity){
            activity = (Activity)context;
            try {
                this.mClickListener = (VOBActionsListener) activity;
            }catch (ClassCastException e){
                throw new ClassCastException(activity.toString() + " must implement VOBActionListener");
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mClickListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu,menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        adapter.setFilter(articleModelList);
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        showLiked = false;
                        sortedAlphabetically = false;
                        return true;
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_sort :
                if(!sortedAlphabetically){
                    adapter.sortAlphabetically();
                   }else{
                    adapter.reverseOrder();
                   }
                sortedAlphabetically = !sortedAlphabetically;
                return true;
            case R.id.menu_liked :
                if(!showLiked){
                    adapter.showLiked();
                    item.setTitle(getResources().getString(R.string.menu_showAll));
                }else{
                    adapter.setFilter(articleModelList);
                    item.setTitle(getResources().getString(R.string.menu_showLiked));
                }
                showLiked = !showLiked;
                sortedAlphabetically = false;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        startFilter(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        startFilter(query);
        return true;
    }

    private void startFilter(String query){
        List<ArticleModel> filteredArticles = filter(articleModelList, query);
        adapter.setFilter(filteredArticles);
    }

    private List<ArticleModel> filter(List<ArticleModel> articles, String query){
        List<ArticleModel> filteredList = new ArrayList<>();
        for (ArticleModel article:articles) {
            String articleTitle = article.getTitle().toLowerCase();
            if(articleTitle.contains(query.toLowerCase())){
                filteredList.add(article);
            }
        }
        return filteredList;
    }

    public void addArticle(ArticleModel articleModels) {
        articleModelList.add(articleModels);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCardClick(int position) {
        ArticleModel model = adapter.getArticleModeAt(position);
        mClickListener.onCardClicked(model);
    }

    @Override
    public void onLikeClick(int position) {
            ArticleModel model = adapter.getArticleModeAt(position);
            model.setLiked(!model.isLiked());
            if(model.isLiked()){
                mClickListener.likeArticle(model.getID());
            }else{
                mClickListener.unlikeArticle(model.getID());
            }
    }
}
