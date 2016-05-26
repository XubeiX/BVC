package ratajczak.artur.vob;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import ratajczak.artur.bvc.R;
import ratajczak.artur.vob.RV.ArticleModel;
import ratajczak.artur.vob.fragments.VillainsOfBatmanListFragment;
import ratajczak.artur.vob.fragments.DetailCharacterFragment;

public class MainActivity extends AppCompatActivity implements VillainsOfBatmanListFragment.VOBActionsListener {

    private View fragmentContainer;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentContainer = findViewById(R.id.DetailsFragment);

        if(fragmentContainer!=null){
            bundle = prepareBundle(welcomeArticle());
            updateDetailCharacterFragment(bundle);
        }
    }

    @Override
    public void onCardClicked(ArticleModel articleModel) {
        bundle = prepareBundle(articleModel);

        if(fragmentContainer != null){
            updateDetailCharacterFragment(bundle);
        }else{
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        }
    }

    @Override
    public void likeArticle(int articleID) {
        Toast.makeText(getApplicationContext(),String.valueOf(articleID)+ "LIKE",Toast.LENGTH_LONG).show();
    }

    @Override
    public void unlikeArticle(int articleID) {
        Toast.makeText(getApplicationContext(),String.valueOf(articleID)+ "UNLIKE",Toast.LENGTH_LONG).show();
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
}
