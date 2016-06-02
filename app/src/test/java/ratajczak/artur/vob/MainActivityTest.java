package ratajczak.artur.vob;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;
import android.view.Menu;

import org.apache.tools.ant.Main;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowSQLiteConnection;
import org.robolectric.util.ActivityController;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;

import ratajczak.artur.bvc.BuildConfig;
import ratajczak.artur.bvc.R;
import ratajczak.artur.vob.RV.ArticleModel;
import ratajczak.artur.vob.RV.ArticleRVAdapter;
import ratajczak.artur.vob.fragments.VillainsOfBatmanListFragment;
import ratajczak.artur.vob.utils.DatabaseHelper;
import ratajczak.artur.vob.utils.DatabaseManager;

import static org.robolectric.Shadows.shadowOf;

/**
 * Created by Artur Ratajczak on 02.06.16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class MainActivityTest {
    private ActivityController<MainActivity> controller;
    private MainActivity activity;


    @Before
    public void setUp(){
        controller = Robolectric.buildActivity(MainActivity.class);
        activity = controller.create().start().resume().visible().get();


    }

    @After
    public void tearDown(){
        controller.pause().stop().destroy();

    }



    @Test
    public void menuItemsTest(){
        Menu menu = shadowOf(activity).getOptionsMenu();
        Assert.assertEquals(activity.getApplicationContext().getString(R.string.menu_showLiked),menu.findItem(R.id.menu_liked).getTitle());
    }

    @Test
    public void menuSearchingTest(){
        ArticleRVAdapter adapter = null;
       VillainsOfBatmanListFragment fragment = (VillainsOfBatmanListFragment)activity.getSupportFragmentManager().findFragmentById(R.id.BVCFragment);
        try {
            Field f = fragment.getClass().getDeclaredField("adapter");
            f.setAccessible(true);
            adapter = (ArticleRVAdapter)f.get(fragment);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        Menu menu = shadowOf(activity).getOptionsMenu();

        ArrayList<ArticleModel> fullList = new ArrayList<>();
        for(int i = 0; i < adapter.getItemCount(); i++)
            fullList.add(adapter.getArticleModeAt(i));

        for (ArticleModel a: fullList) {
            Log.e("info",a.getTitle());
        }
    }

}
