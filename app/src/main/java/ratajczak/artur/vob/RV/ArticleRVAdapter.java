package ratajczak.artur.vob.RV;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ratajczak.artur.bvc.R;

/**
 * Created by Artur Ratajczak on 23.05.16.
 */
public class ArticleRVAdapter extends RecyclerView.Adapter<ArticleItemViewHolder> {
    private List<ArticleModel> articles;
    private final ViewHolderClicks mListener;

    public interface ViewHolderClicks{
        void onCardClick(int position);
        void onLikeClick(int position);
    }

    public ArticleRVAdapter(List<ArticleModel> articles, ViewHolderClicks listener){
        this.articles = articles;
        mListener = listener;
    }

    @Override
    public ArticleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        return new ArticleItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ArticleItemViewHolder holder,final int position) {
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v instanceof CheckBox)
                    mListener.onLikeClick(position);
                else
                    mListener.onCardClick(position);
            }
        });
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

    public void showLiked(){
        ArrayList<ArticleModel> liked = new ArrayList<>();
        for (ArticleModel article : articles) {
            if(article.isLiked())
                liked.add(article);
        }
        setFilter(liked);
    }
}
