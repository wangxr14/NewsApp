package ihandy.com.a2014011300;

/**
 * Created by ok on 2016/9/3.
 */
public class News {
    private int news_ID;
    private String title;
    private String category;
    private String origin;
    private String source_url;
    private int imageID;

    public News(int news_ID,String title, String category, String origin,String source_url,int imageID) {
        this.news_ID=news_ID;
        this.title = title;
        this.category = category;
        this.origin=origin;
        this.source_url=source_url;
        this.imageID = imageID;
    }

    public void setNews_ID(int news_ID){
        this.news_ID=news_ID;
    }
    public int getNewsID() {
        return news_ID;
    }
    public void setCategory(String category){
        this.category=category;
    }
    public String getCategory() {
        return category;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public String getTitle() {
        return title;
    }
    public void setOrigin(String origin){
        this.origin=origin;
    }
    public String getOrigin() {
        return origin;
    }
    public void setSource_url(String source_url){
        this.source_url=source_url;
    }
    public String getSource_url() {
        return source_url;
    }
    public void setImageID(int imageID){
        this.imageID=imageID;
    }
    public int getImageID() {
        return imageID;
    }
}
