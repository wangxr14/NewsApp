package ihandy.com.a2014011300;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by ok on 2016/9/3.
 */
public class NewsAdapter extends ArrayAdapter<News>{
    private int resourceId;

    public NewsAdapter(Context context, int textViewResourceId ,List<News> objects)
    {
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent)
    {
        News news=getItem(position);
        View view;
        if(convertView==null){
            view=LayoutInflater.from(getContext()).inflate(resourceId, null);
        }
        else
        {
            view=convertView;
        }
        TextView newsContent=(TextView)view.findViewById(R.id.news_title);
        final ImageView newsImg=(ImageView)view.findViewById(R.id.news_img);
        TextView newsOrigin=(TextView)view.findViewById(R.id.news_origin);

        newsContent.setText(news.getTitle());
        newsOrigin.setText(news.getOrigin());
        final String path=news.getImage_url();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap=getPicture(path);
                newsImg.post(new Runnable() {
                    @Override
                    public void run() {
                        newsImg.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
        return view;
    }

    public Bitmap getPicture(String path){
        Bitmap bm=null;
        try{
            URL url=new URL(path);
            URLConnection connection=url.openConnection();
            connection.connect();
            InputStream inputStream=connection.getInputStream();
            bm= BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  bm;
    }
}
