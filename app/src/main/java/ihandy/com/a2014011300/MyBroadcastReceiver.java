package ihandy.com.a2014011300;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

/**
 * Created by ok on 2016/9/7.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {

    private ArrayList<News> favoritesList=new ArrayList<News>();
    private Map<String,ArrayList<News>> newsMap=new HashMap<String,ArrayList<News>>();

    public void set_List_Map(ArrayList<News> favoritesList,Map<String,ArrayList<News>> newsMap)
    {
        this.favoritesList=favoritesList;
        this.newsMap=newsMap;
    }

    @Override
    public void onReceive(Context context,Intent intent)
    {
        Toast.makeText(context,"Receive!",Toast.LENGTH_SHORT).show();
        String cate=intent.getStringExtra("category");
        int pos=intent.getIntExtra("position", 0);
        boolean isFavorite=intent.getBooleanExtra("favorite", false);
        News tmp=newsMap.get(cate).get(pos);
        if(isFavorite)
        {
            if(!favoritesList.contains(tmp))
            {
                favoritesList.add(tmp);
            }
        }
        else
        {
            if(favoritesList.contains(tmp))
            {
                favoritesList.remove(tmp);
            }
        }

        for(News i:favoritesList)
        {
            Log.d("fav", i.getTitle());
        }
    }

}
