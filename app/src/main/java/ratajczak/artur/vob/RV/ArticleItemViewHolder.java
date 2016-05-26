package ratajczak.artur.vob.RV;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ratajczak.artur.bvc.R;

/**
 * Created by Artur Ratajczak on 23.05.16.
 */
public class ArticleItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private final int MAXABSTRACTLENGTH = 40;
    private final int MAXABSTRACTWORDS = 7;
    
    private TextView article_title;
    private TextView article_abstract;
    private ImageView thumbnail;
    private ViewHolderClicks mListener;
    private Context context;

    public static interface ViewHolderClicks{
        void onCardClick(View v);
    }

    public ArticleItemViewHolder(final View itemView, ViewHolderClicks listener) {
        super(itemView);
        context = itemView.getContext();
        mListener = listener;
        article_title = (TextView)itemView.findViewById(R.id.article_title);
        article_abstract = (TextView)itemView.findViewById(R.id.atricle_abstract);
        thumbnail = (ImageView)itemView.findViewById(R.id.thumbnail);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mListener.onCardClick(v);
    }

    public void bind(ArticleModel articleModel){
        article_title.setText(articleModel.getTitle());
        try{
            article_abstract.setText(getNFirstWords(articleModel.getAbst(),MAXABSTRACTWORDS));
        }catch (Exception e){
            article_abstract.setText(articleModel.getAbst());
        }
        Picasso.with(context).load(articleModel.getThumbnailUrl()).error(R.drawable.batman).into(thumbnail);

    }

    private String getNFirstWords(String sentense, int n){
        String[] words = sentense.split("\\s+");
        StringBuffer sb = new StringBuffer();
        for(int i =0; i < n; i++){
            sb.append(words[i]+" ");
        }
        return sb.toString()+"...";
    }

}
