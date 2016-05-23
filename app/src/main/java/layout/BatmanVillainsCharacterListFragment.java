package layout;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
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

import ratajczak.artur.bvc.ArticleModel;
import ratajczak.artur.bvc.ArticleRVAdapter;
import ratajczak.artur.bvc.JsonParser;
import ratajczak.artur.bvc.R;

/**
 * Created by Artur Ratajczak on 23.05.16.
 */
public class BatmanVillainsCharacterListFragment extends Fragment implements SearchView.OnQueryTextListener{
    private RecyclerView recyclerView;
    private List<ArticleModel> articleModelList;
    private ArticleRVAdapter adapter;

    public BatmanVillainsCharacterListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_batman_villains_character_list, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        AsyncTask JsonParser = new JsonParser(new JsonParser.JsonParserResponse() {
            @Override
            public void processFinish(List<ArticleModel> articleModels) {
                articleModelList = articleModels;
                adapter.setFilter(articleModelList);
            }
        }).execute("test");

        //TODO: in this place download data from server and store it in ArrayList "articleModelList" and create adapter

        adapter = new ArticleRVAdapter(articleModelList);
        recyclerView.setAdapter(adapter);
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


}
