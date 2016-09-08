package ihandy.com.a2014011300;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by ok on 2016/9/8.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    public String CREATE_NEWS_TABLE="CREATE TABLE NEWSDATA"+"("
            +News.KEY_ID+" INTEGER ,"
            +News.KEY_title+" TEXT, "
            +News.KEY_category+" TEXT, "
            +News.KEY_origin+" TEXT, "
            +News.KEY_source_url+" TEXT, "
            +News.KEY_image_url+" TEXT,"
            +News.KEY_FAVORITE+" INTEGER)";
    public String CREATE_CATE_TABLE="CREATE TABLE CATEGORYDATA"+"(CATEGORY TEXT ,ISWATCHED INTERGER)";
    public static final String DATABASE_NAME="news.db";
    public static final String CATE_TABLE="CATEGORYDATA";
    private static final int DATABASE_VERSION=4;

    public  MyDatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //建立两张表，一张保存新闻信息，一张保存分类信息
        db.execSQL(CREATE_NEWS_TABLE);
        db.execSQL(CREATE_CATE_TABLE);
        //创建成功
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop if table exists NEWSDATA");
        db.execSQL("drop if table exists CATEGORYDATA");
        onCreate(db);
    }
}
