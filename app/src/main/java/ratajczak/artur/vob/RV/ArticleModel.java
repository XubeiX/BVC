package ratajczak.artur.vob.RV;

import android.support.annotation.NonNull;

/**
 * Created by Artur Ratajczak on 23.05.16.
 */
public class ArticleModel implements Comparable<ArticleModel> {
    private int ID;
    private String title;
    private String abst;
    private String thumbnailUrl;
    private String articleURL;
    private boolean liked = false;

    public ArticleModel(String title, String abst){
        this.title = title;
        this.abst = abst;
    }

    public ArticleModel(int ID,String title, String abst, String thumbnailUrl, String articleUrl) {
        this.ID = ID;
        this.title = title;
        this.abst = abst;
        this.thumbnailUrl = thumbnailUrl;
        this.articleURL = articleUrl;
    }

    public int getID(){ return  ID; }

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

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    @Override
    public int compareTo(@NonNull ArticleModel another) {
        int compare = this.title.compareTo(another.getTitle());
        return compare == 0 ? this.title.compareTo(another.getTitle()) : compare;
    }
}
