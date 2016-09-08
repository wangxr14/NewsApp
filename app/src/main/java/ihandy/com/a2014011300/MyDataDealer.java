package ihandy.com.a2014011300;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ok on 2016/9/8.
 */
public class MyDataDealer {
    private MyDatabaseHelper myDatabaseHelper;

    public MyDataDealer(Context context)
    {
        myDatabaseHelper=new MyDatabaseHelper(context);
    }

    public void insert_news(News news)
    {
        SQLiteDatabase db=myDatabaseHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(News.KEY_ID,news.getNewsID());
        values.put(News.KEY_title,news.getTitle());
        values.put(News.KEY_category,news.getCategory());
        values.put(News.KEY_origin,news.getOrigin());
        values.put(News.KEY_source_url,news.getSource_url());
        values.put(News.KEY_image_url,news.getImage_url());
        values.put(News.KEY_FAVORITE,news.isFavorite());
        db.insert(News.TABLE, null, values);
        db.close();
    }

    public void insert_category(String cate,int isWatched)
    {
        SQLiteDatabase db=myDatabaseHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("category",cate);
        values.put("isWatched",isWatched);
        db.insert(MyDatabaseHelper.CATE_TABLE, null, values);
        db.close();
    }


    public ArrayList<String> getCateList()
    {
        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();
        Cursor cursor=db.query(MyDatabaseHelper.CATE_TABLE, new String[]{"category"}, null, null, null, null, null);
        ArrayList<String> categoryList=new ArrayList<String>();
        while (cursor.moveToNext()) {
            String cate = cursor.getString(0);
            categoryList.add(cate);
        }
        cursor.close();
        db.close();
        return categoryList;
    }

    public ArrayList<String> getWatchedList()
    {
        ArrayList<String> watchedList=new ArrayList<String>();
        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();
        Cursor cursor=db.query(MyDatabaseHelper.CATE_TABLE, new String[]{"category","isWatched"}, "isWatched = 1", null, null, null, null);
        while (cursor.moveToNext()) {
            String cate = cursor.getString(0);
            watchedList.add(cate);
        }
        cursor.close();
        db.close();
        return watchedList;
    }

    public ArrayList<News> getNewsList(String cate)
    {
        ArrayList<News> newsList=new ArrayList<News>();
        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();
        Cursor cursor=db.query(News.TABLE, new String[]{News.KEY_ID,News.KEY_title,News.KEY_category,
                        News.KEY_origin,News.KEY_source_url,News.KEY_image_url,News.KEY_FAVORITE},
                News.KEY_category+"=?", new String[]{cate}, null,null, null);
        while (cursor.moveToNext()) {
            long id=cursor.getLong(0);
            String title=cursor.getString(1);
            String origin=cursor.getString(3);
            String sourceurl=cursor.getString(4);
            String imgurl=cursor.getString(5);
            int isFavorite=cursor.getInt(6);
            News news=new News(id,title,cate,origin,sourceurl,imgurl);
            newsList.add(news);
        }
        cursor.close();
        db.close();
        return newsList;
    }

    public Map<String,ArrayList<News>> getNewsMap(ArrayList<String> categoryList)
    {
        Map<String,ArrayList<News>> tempMap=new HashMap<String,ArrayList<News>>();
        for(int i=0;i<categoryList.size();i++)
        {
            String cate=categoryList.get(i);
            ArrayList<News> tmp=getNewsList(cate);
            tempMap.put(cate, tmp);
            Log.d("news " + cate, "" + tmp.size());
        }
        return tempMap;
    }

    public ArrayList<News> getFavoriteList()
    {
        ArrayList<News> favoriteList=new ArrayList<News>();
        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();
        Cursor cursor=db.query(News.TABLE, new String[]{News.KEY_ID,News.KEY_title,News.KEY_category,
                        News.KEY_origin,News.KEY_source_url,News.KEY_image_url,News.KEY_FAVORITE},
                News.KEY_FAVORITE+"=?", new String[]{"1"}, null, null, null);
        while (cursor.moveToNext()) {
            long id=cursor.getLong(0);
            String title=cursor.getString(1);
            String cate=cursor.getString(2);
            String origin=cursor.getString(3);
            String sourceurl=cursor.getString(4);
            String imgurl=cursor.getString(5);
            int isFavorite=cursor.getInt(6);
            News news=new News(id,title,cate,origin,sourceurl,imgurl);
            favoriteList.add(news);
        }
        cursor.close();
        db.close();
        return favoriteList;
    }

    public void clearTable()
    {
        SQLiteDatabase db=myDatabaseHelper.getWritableDatabase();
        db.delete(News.TABLE,null,null);
        db.delete(MyDatabaseHelper.CATE_TABLE,null,null);
        db.close();
    }


}
