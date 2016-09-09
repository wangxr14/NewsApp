package ihandy.com.a2014011300;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by ok on 2016/9/3.
 */
public class News implements Serializable {
    private long news_ID;
    private String title;
    private String category;
    private String origin;
    private String source_url;
    private String image_url;
    private int isMyFavorite=0;

    public static final String TABLE="NEWSDATA";
    public static final String KEY_ID="id";
    public static final String KEY_title="title";
    public static final String KEY_category="category";
    public static final String KEY_origin="origin";
    public static final String KEY_source_url="sourceURL";
    public static final String KEY_image_url="imageURL";
    public static final String KEY_FAVORITE="isFavorite";

    public News(long news_ID,String title, String category, String origin,String source_url,String image_url) {
        this.news_ID=news_ID;
        this.title = title;
        this.category = category;
        this.origin=origin;
        this.source_url=source_url;
        this.image_url = image_url;
    }
    public News(long news_ID,String title, String category, String origin,String source_url,String image_url,int isMyFavorite) {
        this.news_ID=news_ID;
        this.title = title;
        this.category = category;
        this.origin=origin;
        this.source_url=source_url;
        this.image_url = image_url;
        this.isMyFavorite=isMyFavorite;
    }
    public void setNews_ID(long news_ID){
        this.news_ID=news_ID;
    }
    public long getNewsID() {
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
    public void setImage_url(String image_url){
        this.image_url=image_url;
    }
    public String getImage_url() {
        return image_url;
    }
    public void setFavorite(int favorite)
    {
        if(favorite>0)
            isMyFavorite=1;
        else
            isMyFavorite=0;
    }
    public int isFavorite(){
        return isMyFavorite;
    }

}
