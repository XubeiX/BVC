package ratajczak.artur.vob.RV;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import ratajczak.artur.bvc.R;

/**
 * Created by Artur Ratajczak on 23.05.16.
 */
public class ArticleItemViewHolder extends RecyclerView.ViewHolder {
    private View view;
    private TextView article_title;
    private TextView article_abstract;
    private CircularImageView thumbnail;
    private CheckBox liked;
    private Context context;

    public ArticleItemViewHolder(final View itemView) {
        super(itemView);
        this.view = itemView;
        context = itemView.getContext();
        article_title = (TextView)itemView.findViewById(R.id.article_title);
        article_abstract = (TextView)itemView.findViewById(R.id.article_abstract);
        thumbnail = (CircularImageView)itemView.findViewById(R.id.thumbnail);
        liked = (CheckBox)itemView.findViewById(R.id.like);
    }

    public void bind(ArticleModel articleModel){
        article_title.setText(articleModel.getTitle());
        article_abstract.setText(articleModel.getAbst());
        Picasso.with(context).load(articleModel.getThumbnailUrl()).error(R.drawable.batman).into(thumbnail);
        liked.setChecked(articleModel.isLiked());
    }

    public void setOnClickListener(View.OnClickListener onClickListener){
        view.setOnClickListener(onClickListener);
        liked.setOnClickListener(onClickListener);
    }
}
