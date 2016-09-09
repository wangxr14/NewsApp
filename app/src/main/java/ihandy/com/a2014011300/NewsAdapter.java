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

import com.squareup.picasso.Picasso;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    private long newsID;
    private Bitmap bitmap;
    private Context context;

    public NewsAdapter(Context context, int textViewResourceId ,List<News> objects)
    {
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
        this.context=context;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent)
    {
        News news=getItem(position);
        newsID=news.getNewsID();
        View view;
        if(convertView==null){
            view=LayoutInflater.from(getContext()).inflate(resourceId, null);
        }
        else
        {
            view=convertView;
        }
        TextView newsContent=(TextView)view.findViewById(R.id.news_title);
        ImageView newsImg=(ImageView)view.findViewById(R.id.news_img);
        TextView newsOrigin=(TextView)view.findViewById(R.id.news_origin);

        newsContent.setText(news.getTitle());
        newsOrigin.setText(news.getOrigin());
        Picasso.with(context).load(news.getImage_url()).into(newsImg);
        /*try {
            FileInputStream fileInputStream=getContext().openFileInput(newsID+".png");
            bitmap=BitmapFactory.decodeStream(fileInputStream);
            newsImg.post(new Runnable() {
                @Override
                public void run() {
                    newsImg.setImageBitmap(bitmap);
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            final String path=news.getImage_url();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Bitmap bm=getPicture(path);
                    newsImg.post(new Runnable() {
                        @Override
                        public void run() {
                            newsImg.setImageBitmap(bm);
                        }
                    });
                }
            }).start();
        }*/

 /*       final String path=news.getImage_url();
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
        }).start();*/
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
            inputStream.close();
            FileOutputStream fileOutputStream = getContext().openFileOutput(newsID + ".png", Context.MODE_PRIVATE);
            bm.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  bm;
    }
}
