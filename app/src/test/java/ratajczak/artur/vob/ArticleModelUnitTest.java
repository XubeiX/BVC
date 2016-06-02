package ratajczak.artur.vob;


import org.junit.Test;
import ratajczak.artur.vob.RV.ArticleModel;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */

public class ArticleModelUnitTest{

    @Test
    public void compareToTest(){
        ArticleModel modelA = new ArticleModel("Model A","abstract");
        ArticleModel modelB = new ArticleModel("Model X","abstract");

        assertEquals(0,modelA.compareTo(modelA));
        assertTrue(modelB.compareTo(modelA)>=1);
        assertTrue(modelA.compareTo(modelB)<=-1);
    }
}