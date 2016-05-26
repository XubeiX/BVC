package ratajczak.artur.bvc.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Artur Ratajczak on 26.05.16.
 */
public class DownloadThumbnailTask extends AsyncTask<String, Void, Bitmap> {
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
