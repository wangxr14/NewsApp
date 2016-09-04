package ihandy.com.a2014011300;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.TextView;

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
        newsContent.setText(news.getTitle());
        return view;
    }

}
