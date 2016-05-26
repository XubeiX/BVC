package ratajczak.artur.bvc.fragments;


import android.app.Activity;

import android.content.Context;
import android.content.DialogInterface;

import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
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


import ratajczak.artur.bvc.RV.ArticleItemViewHolder;
import ratajczak.artur.bvc.RV.ArticleModel;
import ratajczak.artur.bvc.RV.ArticleRVAdapter;
import ratajczak.artur.bvc.utils.JsonParser;
import ratajczak.artur.bvc.R;

/**
 * Created by Artur Ratajczak on 23.05.16.
 */
public class BatmanVillainsCharacterListFragment extends Fragment implements SearchView.OnQueryTextListener,JsonParser.JsonParserResponse, ArticleItemViewHolder.ViewHolderClicks{
    private RecyclerView recyclerView;
    private List<ArticleModel> articleModelList;
    private ArticleRVAdapter adapter;
    private boolean sortedAlphabetically = false;
    private BVCListListener mClickListener;

    public static interface BVCListListener{
        void itemClicked(ArticleModel articleModel);
    }

    public BatmanVillainsCharacterListFragment() {
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

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        if(isNetworkConnected()){
            AsyncTask JsonParser = new JsonParser(this).execute();

        }else{
            new AlertDialog.Builder(getContext())
                    .setTitle("Error")
                    .setMessage("No Internet access")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        adapter = new ArticleRVAdapter(articleModelList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity;
        if(context instanceof Activity){
            activity = (Activity)context;
         this.mClickListener = (BVCListListener)activity;
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
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_sort :
                if(!sortedAlphabetically){
                    adapter.sortAlphabetically();
                    sortedAlphabetically = true;
                    }else{
                    adapter.reverseOrder();
                    sortedAlphabetically = false;
                }
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
        List<ArticleModel> filtredArticles = filter(articleModelList,query);
        adapter.setFilter(filtredArticles);
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

    private boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


    @Override
    public void processFinish(ArticleModel articleModels) {
        articleModelList.add(articleModels);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCardClick(View v) {
        if(mClickListener!=null){
        int pos = recyclerView.getChildAdapterPosition(v);
        ArticleModel model = adapter.getArticleModeAt(pos);
        mClickListener.itemClicked(model);
        }
    }
}
