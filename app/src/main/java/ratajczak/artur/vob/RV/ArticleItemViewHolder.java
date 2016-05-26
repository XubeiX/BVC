package ratajczak.artur.vob.RV;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ratajczak.artur.bvc.R;

/**
 * Created by Artur Ratajczak on 23.05.16.
 */
public class ArticleItemViewHolder extends RecyclerView.ViewHolder {
    private final int MAXABSTRACTWORDS = 7;
    private View view;
    private TextView article_title;
    private TextView article_abstract;
    private ImageView thumbnail;
    private CheckBox liked;
    private Context context;

    public ArticleItemViewHolder(final View itemView) {
        super(itemView);
        this.view = itemView;
        context = itemView.getContext();
        article_title = (TextView)itemView.findViewById(R.id.article_title);
        article_abstract = (TextView)itemView.findViewById(R.id.article_abstract);
        thumbnail = (ImageView)itemView.findViewById(R.id.thumbnail);
        liked = (CheckBox)itemView.findViewById(R.id.like);
    }

    public void bind(ArticleModel articleModel){
        article_title.setText(articleModel.getTitle());
        try{
            //article_abstract.setText(getNFirstWords(articleModel.getAbst(),MAXABSTRACTWORDS));
            article_abstract.setText(articleModel.getAbst());
        }catch (Exception e){
            article_abstract.setText(articleModel.getAbst());
        }
        Picasso.with(context).load(articleModel.getThumbnailUrl()).error(R.drawable.batman).into(thumbnail);
        liked.setChecked(articleModel.isLiked());
    }

    private String getNFirstWords(String sentence, int n){
        String[] words = sentence.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for(int i =0; i < n; i++){
            sb.append(words[i]).append(" ");
        }
        return sb.toString()+"...";
    }

    public void setOnClickListener(View.OnClickListener onClickListener){
        view.setOnClickListener(onClickListener);
        liked.setOnClickListener(onClickListener);
    }
}
