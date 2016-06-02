package ratajczak.artur.vob;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import ratajczak.artur.bvc.BuildConfig;
import ratajczak.artur.bvc.R;
import ratajczak.artur.vob.fragments.DetailCharacterFragment;


/**
 * Created by Artur Ratajczak on 02.06.16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class DetailFragmentTest {
    private ActivityController<DetailActivity> controller;
    private DetailActivity activity;

    @Before
    public void setUp(){
        controller = Robolectric.buildActivity(DetailActivity.class);
    }

    @After
    public void tearDown(){
        controller.pause().stop().destroy();
    }

    @Test
    public void createActivityWithIntentContainingBundle(){
        Bundle bundle = new Bundle();
        bundle.putString(DetailCharacterFragment.BUNDLE_TITLE_TAG,"Test");
        bundle.putString(DetailCharacterFragment.BUNDLE_ABSTRACT_TAG,"abstract description");

        Intent intent = new Intent(RuntimeEnvironment.application,DetailActivity.class);
        intent.putExtras(bundle);

        activity = controller.withIntent(intent).create().start().resume().visible().get();

        DetailCharacterFragment fragment = (DetailCharacterFragment) activity.getSupportFragmentManager().findFragmentById(R.id.DetailsFragment);
        String IntentSendTitle = ((TextView)fragment.getView().findViewById(R.id.article_title_detail)).getText().toString();
        String AbstrtactSendTitle = ((TextView)fragment.getView().findViewById(R.id.article_abstract_detail)).getText().toString();

        Button button = (Button)fragment.getView().findViewById(R.id.ReadMoreButton);

        Assert.assertEquals(Button.INVISIBLE,button.getVisibility());
        Assert.assertSame(bundle.getString(DetailCharacterFragment.BUNDLE_TITLE_TAG),IntentSendTitle);
        Assert.assertSame(bundle.getString(DetailCharacterFragment.BUNDLE_ABSTRACT_TAG),AbstrtactSendTitle);
    }

    //The default value for the title fiels in Fragment is ""
    @Test
    public void createActivityWithoutContainingBundle(){
        activity = controller.create().start().resume().visible().get();

        DetailCharacterFragment fragment = (DetailCharacterFragment) activity.getSupportFragmentManager().findFragmentById(R.id.DetailsFragment);
        String IntentSendTitle = ((TextView)fragment.getView().findViewById(R.id.article_title_detail)).getText().toString();

        Assert.assertEquals("",IntentSendTitle);
    }

    //button is visible when bundle contains url
    @Test
    public void checkFragmentReadMoreButtonIsAvailable(){
        String url = "http://image.freepik.com/free-icon/upload-arrow_318-79454.jpg";
        Bundle bundle = new Bundle();
        bundle.putString(DetailCharacterFragment.BUNDLE_ARTICLE_URL, url);


        Intent intent = new Intent(RuntimeEnvironment.application,DetailActivity.class);
        intent.putExtras(bundle);

        activity = controller.withIntent(intent).create().start().resume().visible().get();

        DetailCharacterFragment fragment = (DetailCharacterFragment) activity.getSupportFragmentManager().findFragmentById(R.id.DetailsFragment);
        Button button = (Button)fragment.getView().findViewById(R.id.ReadMoreButton);

        Assert.assertEquals(Button.VISIBLE,button.getVisibility());
    }

}
