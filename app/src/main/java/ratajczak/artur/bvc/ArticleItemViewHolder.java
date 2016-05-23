package ratajczak.artur.bvc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Artur Ratajczak on 23.05.16.
 */
public class ArticleItemViewHolder extends RecyclerView.ViewHolder{

    public TextView article_title;
    public TextView article_abstract;
    public ImageView thumbnail;

    public ArticleItemViewHolder(View itemView) {
        super(itemView);

        article_title = (TextView)itemView.findViewById(R.id.article_title);
        article_abstract = (TextView)itemView.findViewById(R.id.atricle_abstract);
        thumbnail = (ImageView)itemView.findViewById(R.id.thumbnail);
    }

    //TODO: set abstract to max 200
    public void bind(ArticleModel articleModel){
        article_title.setText(articleModel.getTitle());
        article_abstract.setText(articleModel.getAbst());
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
