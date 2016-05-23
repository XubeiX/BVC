package ratajczak.artur.bvc;

/**
 * Created by Artur Ratajczak on 23.05.16.
 */
public class ArticleModel {
    private String title;
    private String abst;
    private String thumbnailUrl;

    public ArticleModel(String title, String abst, String thumbnailUrl) {
        this.title = title;
        this.abst = abst;
        this.thumbnailUrl = thumbnailUrl;
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
}
