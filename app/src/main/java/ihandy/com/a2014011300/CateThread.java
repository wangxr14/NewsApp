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
public class CateThread extends Thread {
    private ArrayList<String> categoryList=new ArrayList<String>();;

    public ArrayList<String> getCategoryList()
    {
        return categoryList;
    }

    public void run()
    {
        long time=System.currentTimeMillis();
        String categoryURL="http://assignment.crazz.cn/news/en/category?timestamp="+time;
        //String categoryURL="http://assignment.crazz.cn/news/en/category?timestamp=11111111111";
        Log.d("network",categoryURL);
        try {
            URL url=new URL(categoryURL);
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int x=connection.getResponseCode();
            Log.d("network",x+"");
            if(connection.getResponseCode()==200)
            {
                Log.d("time2:", "" + time);
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
                JSONObject cateObject=dataObject.getJSONObject("categories");
               // JSONArray cateObject=dataObject.getJSONArray("categories");
                Log.d("cate:", dataObject.toString());
                Iterator<String> iterator = cateObject.keys();
                String key = null;
                String value = null;
                while (iterator.hasNext()) {
                    key = iterator.next();
                    Log.d("key:", key);
                    value = cateObject.getString(key);
                    Log.d("category:", value);
                    categoryList.add(key);
                }
            }
            else
            {
                Log.d("network","not ok");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
