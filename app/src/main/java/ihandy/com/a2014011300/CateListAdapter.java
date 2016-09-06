package ihandy.com.a2014011300;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ok on 2016/9/5.
 */
public class CateListAdapter extends ArrayAdapter<String> {
    private int resourceId;
    public ArrayList<String> watchedCateList=new ArrayList<String>();
    public CateListAdapter(Context context,int textViewResourceId ,ArrayList<String> objects)
    {
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent)
    {
        String cate=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView cateText=(TextView)view.findViewById(R.id.cate_text);
        ImageView imageView=(ImageView)view.findViewById(R.id.cate_img);
        if(watchedCateList.contains(cate)) imageView.setImageResource(R.drawable.selected);
        else imageView.setImageResource(R.drawable.not_selected);
        cateText.setText(cate);
        return view;
    }
}
