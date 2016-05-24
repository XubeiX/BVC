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

    //TODO: set abstract to max 200
    public void bind(ArticleModel articleModel){
        article_title.setText(articleModel.getTitle());
        try{
        article_abstract.setText(articleModel.getAbst().substring(0,MAXABSTRACTLENGTH)+"...");
        }catch (StringIndexOutOfBoundsException e){
            article_abstract.setText(articleModel.getAbst());
        }
            new DownloadThumbnailTask(thumbnail).execute(articleModel.getThumbnailUrl());


    }
    //TODO: download each time or just once?
    private class DownloadThumbnailTask extends AsyncTask<String, Void, Bitmap>{
        int HTTPOffset = 7;
        ImageView thumbnail;
        public DownloadThumbnailTask(ImageView thumbnail){
            this.thumbnail = thumbnail;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            //added WWW because URL don't recognize a protocol
            //TODO: load default Bitmap not null (default - thumbnail no exist)
            Bitmap icon = null;

            try{
                StringBuilder imageUrl = new StringBuilder(urls[0]).insert(HTTPOffset,"www.");
                URL url = new URL(imageUrl.toString());
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setDoInput(true);
                connection.connect();

                InputStream inputStream = connection.getInputStream();

                icon = BitmapFactory.decodeStream(inputStream);

               //inputStream.close();
            }catch (Exception e){
                Log.e("Thumbnail", e.getMessage());
                e.printStackTrace();
            }
            return icon;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            thumbnail.setImageBitmap(bitmap);
        }
    }
}
