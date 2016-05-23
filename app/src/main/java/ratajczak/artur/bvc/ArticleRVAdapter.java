package ratajczak.artur.bvc;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artur Ratajczak on 23.05.16.
 */
public class ArticleRVAdapter extends RecyclerView.Adapter<ArticleItemViewHolder> {

    private List<ArticleModel> articles;

    public ArticleRVAdapter(List<ArticleModel> articles){
        this.articles = articles;
    }

    @Override
    public ArticleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row,parent,false);
        return new ArticleItemViewHolder(view);
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
}
