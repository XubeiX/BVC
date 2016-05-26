package ratajczak.artur.bvc.RV;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ratajczak.artur.bvc.R;
import ratajczak.artur.bvc.utils.DownloadThumbnailTask;

/**
 * Created by Artur Ratajczak on 23.05.16.
 */
public class ArticleItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private final int MAXABSTRACTLENGTH = 80;
    
    private TextView article_title;
    private TextView article_abstract;
    private ImageView thumbnail;
    private ViewHolderClicks mListener;

    public static interface ViewHolderClicks{
        void onCardClick(View v);
    }

    public ArticleItemViewHolder(final View itemView, ViewHolderClicks listener) {
        super(itemView);
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
        article_abstract.setText(articleModel.getAbst().substring(0,MAXABSTRACTLENGTH)+"...");
        }catch (StringIndexOutOfBoundsException e){
            article_abstract.setText(articleModel.getAbst());
        }
        new DownloadThumbnailTask(thumbnail).execute(articleModel.getThumbnailUrl());


    }

}
