package ratajczak.artur.vob.utils;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import ratajczak.artur.vob.RV.ArticleModel;

/**
 * Created by Artur Ratajczak on 23.05.16.
 */

public class BatmanWikiaArticleJsonParser extends AsyncTask<String, Void, Void>{

    public interface JsonParserResponse{
        void ArticleParsed(ArticleModel articleModels);
    }

    private static final String TAG = "BWAJsonParser";

    //base url
    private static final String URL = "http://batman.wikia.com/api/v1/Articles/Top?expand=1";
    //urs parameters
    private static final String CATEGORY = "&category=Villains";
    private static final String LIMIT = "&limit=50";

    //JSON node name
    private static final String TAG_ARRAY_ITEMS = "items";
    private static final String TAG_TITLE = "title";
    private static final String TAG_ABSTRACT = "abstract";
    private static final String TAG_THUMBNAIL = "thumbnail";
    private static final String TAG_ARTICLE_URL = "url";
    private static final String TAG_ID = "id";

    private final String urlToParse;
    private final JsonParserResponse delegate;

    public BatmanWikiaArticleJsonParser(JsonParserResponse delegate){
        this.delegate = delegate;
        urlToParse = URL + CATEGORY + LIMIT;
    }

    @Override
    protected Void doInBackground(String... params) {
        InputStream inputStream;
        StringBuilder batmanArticlesJSON = new StringBuilder();

        try {
            URL url = new URL(urlToParse);

            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.addRequestProperty("Content-Type","application/json; charset=utf-8");
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
            String line;
            while((line = bufferedReader.readLine()) != null){
                batmanArticlesJSON.append(line).append("\n");
            }
        }catch (IOException e){
            Log.e(TAG,e.getMessage());
            e.printStackTrace();
        }

        startParallelJsonParser(batmanArticlesJSON.toString());
        return null;
    }

    private void startParallelJsonParser(String jsonString){
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_ARRAY_ITEMS);
            for (int i = 0; i<jsonArray.length(); i++){
                JSONObject singleArticle = jsonArray.getJSONObject(i);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
                    new parseBatmanArticleJSON().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,singleArticle);
                }else{
                    new parseBatmanArticleJSON().execute(singleArticle);
                }
            }
        }catch (JSONException e){
            Log.e(TAG,e.getMessage());
            e.printStackTrace();
        }
    }


    private class parseBatmanArticleJSON extends AsyncTask<JSONObject,Void,ArticleModel>{

        @Override
        protected ArticleModel doInBackground(JSONObject... params) {
            ArticleModel model = null;
            try {
                JSONObject object = params[0];
                String title = object.getString(TAG_TITLE);
                String abst = object.getString(TAG_ABSTRACT);
                String thumbnail = object.getString(TAG_THUMBNAIL);
                String articleUrl = object.getString(TAG_ARTICLE_URL);
                int ID = object.getInt(TAG_ID);
                model = new ArticleModel(ID,title,abst,thumbnail,articleUrl);
            }catch (JSONException e){
                Log.e(TAG,e.getMessage());
                e.printStackTrace();
            }
            return model;
        }

        @Override
        protected void onPostExecute(ArticleModel articleModel) {
            if(articleModel!=null)
                delegate.ArticleParsed(articleModel);
        }
    }
}
