package ratajczak.artur.vob;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import android.widget.EditText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ratajczak.artur.bvc.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.startsWith;

import junit.framework.AssertionFailedError;

/**
 * Created by xubeix on 03.06.16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    //"cat" return only one item
    @Test
    public void menuSearchTest(){
        onView(withId(R.id.action_search)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText("cat"));

        onView(withId(R.id.article_title)).check(matches(withText(containsString("Catwoman"))));
    }

    @Test
    public void AlphabeticallySortTest(){
        onView(withId(R.id.menu_sort)).perform(click());
        onView(withId(R.id.recyclerview))
                .check(matches(TestUtills.atPosition(0, hasDescendant(withText(startsWith("A"))))));
    }

    @Test
    public void reveseSortTest(){
        onView(withId(R.id.menu_sort)).perform(click(),click());
        onView(withId(R.id.recyclerview))
                .check(matches(TestUtills.atPosition(0, hasDescendant(withText(startsWith("W"))))));
    }

    @Test
    public void LikeButtonChangeTextAfterClick(){
        onView(withId(R.id.menu_liked)).check(matches(withText(R.string.menu_showLiked))).perform(click()).check(matches(withText(R.string.menu_showAll)));
    }


    @Test
    public void LikeClick(){
        //onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(0, TestUtills.clickChildViewWithId(R.id.like)));
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Anarky")), TestUtills.clickChildViewWithId(R.id.like)));
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Catwoman")), TestUtills.clickChildViewWithId(R.id.like)));
        confirmLikeAfterRestart();
    }


    private void confirmLikeAfterRestart(){
        onView(withId(R.id.menu_liked)).check(matches(withText(R.string.menu_showLiked))).perform(click());
        onView(withId(R.id.menu_sort)).perform(click());
        onView(withId(R.id.recyclerview)).check(matches(TestUtills.atPosition(0, hasDescendant(withText("Anarky")))));
    }

    //was liked before
    @Test(expected = AssertionFailedError.class)
    public void unlikeArticle(){
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Catwoman")), TestUtills.clickChildViewWithId(R.id.like)));
        confirmunlikeAfterRestart();
    }


    private void confirmunlikeAfterRestart(){
        onView(withId(R.id.menu_liked)).check(matches(withText(R.string.menu_showLiked))).perform(click());
        onView(withId(R.id.menu_sort)).perform(click());
        onView(withId(R.id.recyclerview)).check(matches(TestUtills.atPosition(0, hasDescendant(withText("Catwoman")))));
    }

}
