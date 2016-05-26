package ratajczak.artur.bvc.RV;

import android.graphics.Bitmap;

/**
 * Created by Artur Ratajczak on 23.05.16.
 */
public class ArticleModel implements Comparable<ArticleModel> {
    private String title;
    private String abst;
    private String thumbnailUrl;
    private String articleURL;


    public ArticleModel(String title, String abst, String thumbnailUrl, String articleUrl) {
        this.title = title;
        this.abst = abst;
        this.thumbnailUrl = thumbnailUrl;
        this.articleURL = articleUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getAbst() {
        return abst;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getArticleURL() {
        return articleURL;
    }

    @Override

    public int compareTo(ArticleModel another) {
        int compare = this.title.compareTo(another.getTitle());
        return compare == 0 ? this.title.compareTo(another.getTitle()) : compare;
    }
}
