package ratajczak.artur.vob;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import ratajczak.artur.bvc.R;
import ratajczak.artur.vob.RV.ArticleModel;
import ratajczak.artur.vob.fragments.VillainsOfBatmanListFragment;
import ratajczak.artur.vob.fragments.DetailCharacterFragment;
import ratajczak.artur.vob.utils.BatmanWikiaArticleJsonParser;
import ratajczak.artur.vob.utils.DatabaseHelper;

public class MainActivity extends AppCompatActivity implements VillainsOfBatmanListFragment.VOBActionsListener, BatmanWikiaArticleJsonParser.JsonParserResponse {

    private DatabaseHelper dataBase;
    private VillainsOfBatmanListFragment ListFragmentContainer;
    private View DetailsFragmentContainer;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DetailsFragmentContainer = findViewById(R.id.DetailsFragment);

        ListFragmentContainer = (VillainsOfBatmanListFragment)getSupportFragmentManager().findFragmentById(R.id.BVCFragment);

        dataBase = new DatabaseHelper(this);

        if(DetailsFragmentContainer!=null){
            bundle = prepareBundle(welcomeArticle());
            updateDetailCharacterFragment(bundle);
        }
    }



    @Override
    protected void onStart() {
        super.onStart();

        if(isNetworkConnected() && ListFragmentContainer!=null){
            new BatmanWikiaArticleJsonParser(this).execute();
        }else{
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("No Internet access")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }



    @Override
    public void onCardClicked(ArticleModel articleModel) {
        bundle = prepareBundle(articleModel);

        if(DetailsFragmentContainer != null){
            updateDetailCharacterFragment(bundle);
        }else{
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    public void likeArticle(int articleID) {
          dataBase.insertArticle(articleID);
    }

    @Override
    public void unlikeArticle(int articleID) {
        dataBase.deleteArticle(articleID);
    }

    @Override
    public void ArticleParsed(ArticleModel articleModels) {
        //TODO:ten sposób zawiesza UI (należy sprawdzać w wątku czy artykuł jest ulubiony (baza danych musi zezwolić na wiele połączeń))
        //if(dataBase.articleIsLiked(articleModels.getID()))
        //    articleModels.setLiked(true);
        ListFragmentContainer.addArticle(articleModels);
    }

    @Override
    public void refreshList() {
        new BatmanWikiaArticleJsonParser(this).execute();
    }


    private void updateDetailCharacterFragment(Bundle bundle){
        DetailCharacterFragment detailFragment = new DetailCharacterFragment();
        detailFragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.DetailsFragment, detailFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private Bundle prepareBundle(ArticleModel articleModel){
        Bundle bundle = new Bundle();
        bundle.putString(DetailCharacterFragment.BUNDLE_TITLE_TAG, articleModel.getTitle());
        bundle.putString(DetailCharacterFragment.BUNDLE_ABSTRACT_TAG, articleModel.getAbst());
        bundle.putString(DetailCharacterFragment.BUNDLE_ARTICLE_URL, articleModel.getArticleURL());
        bundle.putString(DetailCharacterFragment.BUNDLE_THUMBNAIL_URL,articleModel.getThumbnailUrl());
        bundle.putBoolean(DetailCharacterFragment.BUNDLE_LIKED_TAG,articleModel.isLiked());
        return bundle;
    }

    private ArticleModel welcomeArticle(){
        String welcome = getResources().getString(R.string.detailsFragmentWelcom);
        String info = getResources().getString(R.string.detailsFragmentAbstract);
       return new ArticleModel(welcome,info);
    }

    private boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
