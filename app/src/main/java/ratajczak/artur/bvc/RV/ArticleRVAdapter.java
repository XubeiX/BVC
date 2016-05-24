package ratajczak.artur.bvc.RV;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ratajczak.artur.bvc.R;

/**
 * Created by Artur Ratajczak on 23.05.16.
 */
public class ArticleRVAdapter extends RecyclerView.Adapter<ArticleItemViewHolder> {

    private List<ArticleModel> articles;
    private ArticleItemViewHolder.ViewHolderClicks mListener;

    public ArticleRVAdapter(List<ArticleModel> articles, ArticleItemViewHolder.ViewHolderClicks listener){
        this.articles = articles;
        mListener = listener;
    }



    @Override
    public ArticleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row,parent,false);
        ArticleItemViewHolder viewHolder = new ArticleItemViewHolder(view, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ArticleItemViewHolder holder, int position) {
        holder.bind(articles.get(position));
    }

    @Override
    public int getItemCount() {
        return articles == null ? 0 : articles.size();
    }

    public void setFilter(List<ArticleModel> articleModels){
        articles = new ArrayList<>(articleModels);
        notifyDataSetChanged();
    }

    public void sortAlphabetically(){
        Collections.sort(articles);
        notifyDataSetChanged();
    }

    public void reverseOrder(){
        Collections.reverse(articles);
        notifyDataSetChanged();
    }

    public ArticleModel getArticleModeAt(int index){
        return articles.get(index);
    }


}
