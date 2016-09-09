package ihandy.com.a2014011300;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ok on 2016/9/4.
 */
public class NewsThread extends Thread{
    private ArrayList<News> newsList=new ArrayList<News>();
    private String category;
    private boolean isUpdateAction=true;
    private long news_ID;

    public void setStatus(String category,boolean isUpdateAction,long news_ID)
    {
        this.category=category;
        this.isUpdateAction=isUpdateAction;
        this.news_ID=news_ID;
    }

    public ArrayList<News> getNewsList()
    {
        return newsList;
    }

    public void run()
    {

        String newsURL="http://assignment.crazz.cn/news/query?locale=en&category="+category;
        if(!isUpdateAction)
        {
            newsURL=newsURL+"&max_news_id="+news_ID;
        }
        try {
            URL url=new URL(newsURL);
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if(connection.getResponseCode()==200)
            {
                InputStream is = connection.getInputStream(); // 获取输入流
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    bout.write(buffer, 0, len);
                }
                bout.close();
                is.close();
                byte[] data=bout.toByteArray();
                String json = new String(data); // 把字符串组转换成字符串
                JSONObject jsonObject = new JSONObject(json);
                JSONObject dataObject = jsonObject.getJSONObject("data");
                Log.d("progress:","get News " +category);
                JSONArray newsObject=dataObject.getJSONArray("news");
                //Log.d("news size:", ""+newsObject.length());
                //Log.d("news content",newsObject.toString());
                for(int i=0;i<newsObject.length();i++)
                {
                    jsonObject=newsObject.getJSONObject(i);
                  //  Log.d("news content "+i,jsonObject.toString());
                    long ll=jsonObject.getLong("news_id");
                   // Log.d("ID "+i,""+ll);
                    String title=jsonObject.getString("title");
                    //Log.d("title "+i,title);
                    String cate=jsonObject.getString("category");
                    cate=category;
                    String origin=jsonObject.getString("origin");
                    //Log.d("origin "+i,origin);
                    String sourceURL=jsonObject.getJSONObject("source").getString("url");
                    //Log.d("source " + i, sourceURL);
                    String imageURL=jsonObject.getJSONArray("imgs").getJSONObject(0).getString("url");
                    //Log.d("image "+i,imageURL);
                    News news=new News(ll,title,cate,origin,sourceURL,imageURL);
                    if(!isUpdateAction)
                    {
                        int pos=newsList.size();
                        newsList.add(pos,news);
                    }
                    else newsList.add(news);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
