package ratajczak.artur.bvc.utils;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import ratajczak.artur.bvc.RV.ArticleModel;

/**
 * Created by Artur Ratajczak on 23.05.16.
 */
public class JsonParser extends AsyncTask<String, Void, String>{
    private static final String TAG = "JsonParser";

    //base url
    private static String URL = "http://batman.wikia.com/api/v1/Articles/Top?expand=1";
    //urs parameters
    private static String CATEGORY = "&category=Villains";
    private static String LIMIT = "&limit=50";

    //JSON node name
    private static final String TAG_ARRAY_ITEMS = "items";
    private static final String TAG_TITLE = "title";
    private static final String TAG_ABSTRACT = "abstract";
    private static final String TAG_THUMBNAIL = "thumbnail";
    private static final String TAG_ARTICLE_URL = "url";

    public interface JsonParserResponse{
        void processFinish(ArticleModel articleModels);

    }


    private String urlToParse;
    private InputStream inputStream;
    private JsonParserResponse delegate;
    private StringBuilder JsonString;


    public JsonParser(JsonParserResponse delegate){
        this.delegate = delegate;
        urlToParse = URL + CATEGORY + LIMIT;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        ArrayList<ArticleModel> articles = new ArrayList<>();

        try {
            URL url = new URL(urlToParse);

            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.addRequestProperty("Content-Type","application/json; charset=utf-8");
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();

            try{

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
                JsonString = new StringBuilder();

                String line;
                while((line = bufferedReader.readLine()) != null){
                    JsonString.append(line+"\n");
                }

                inputStream.close();

            }catch (Exception e){
                Log.e(TAG,e.getMessage());
                e.printStackTrace();
            }

            inputStream.close();

        }catch (MalformedURLException e){
            Log.e(TAG,e.getMessage());
            e.printStackTrace();
        }catch (IOException e){
            Log.e(TAG,e.getMessage());
            e.printStackTrace();
        }
        return JsonString.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_ARRAY_ITEMS);
            for (int i = 0; i<jsonArray.length(); i++){
                String title = jsonArray.getJSONObject(i).getString(TAG_TITLE);
                String abst = jsonArray.getJSONObject(i).getString(TAG_ABSTRACT);
                String thumbnail = jsonArray.getJSONObject(i).getString(TAG_THUMBNAIL);
                String articleUrl = jsonArray.getJSONObject(i).getString(TAG_ARTICLE_URL);

                ArticleModel model = new ArticleModel(title,abst,thumbnail,articleUrl);
                delegate.processFinish(model);
            }
        }catch (JSONException e){
            Log.e(TAG,e.getMessage());
            e.printStackTrace();
        }
    }
}
