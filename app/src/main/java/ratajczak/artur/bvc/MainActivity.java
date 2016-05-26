package ratajczak.artur.bvc;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.net.InetAddress;

import ratajczak.artur.bvc.RV.ArticleModel;
import ratajczak.artur.bvc.fragments.BatmanVillainsCharacterListFragment;
import ratajczak.artur.bvc.fragments.DetailCharacterFragment;

public class MainActivity extends AppCompatActivity implements BatmanVillainsCharacterListFragment.BVCListListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    @Override
    public void itemClicked(ArticleModel articleModel) {
        View fragmentContainer = findViewById(R.id.DetailsFragment);

        Bundle bundle = new Bundle();
        bundle.putString(DetailCharacterFragment.BUNDLE_TITLE_TAG, articleModel.getTitle());
        bundle.putString(DetailCharacterFragment.BUNDLE_ABSTRACT_TAG, articleModel.getAbst());
        bundle.putString(DetailCharacterFragment.BUNDLE_ARTICLE_URL, articleModel.getArticleURL());
        bundle.putString(DetailCharacterFragment.BUNDLE_THUMBNAIL_URL,articleModel.getThumbnailUrl());

        if(fragmentContainer != null){
            DetailCharacterFragment detailFragment = new DetailCharacterFragment();
            detailFragment.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.DetailsFragment,detailFragment);
            ft.addToBackStack(null);
            ft.commit();
        }else{
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        }
    }


}
