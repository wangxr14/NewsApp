package ihandy.com.a2014011300;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class SetCategoryActivity extends AppCompatActivity {
    private ArrayList<String> categoryList=new ArrayList<String>();
    private ArrayList<String> watchedCateList=new ArrayList<String>();
    private ArrayList<String> unwatchedCateList=new ArrayList<String>();
    CateListAdapter cateListAdapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_category);
        Intent intent=getIntent();
        categoryList=intent.getStringArrayListExtra("category list");
        watchedCateList=intent.getStringArrayListExtra("watched list");
        for(String i:categoryList)
        {
            if(!watchedCateList.contains(i)) unwatchedCateList.add(i);
        }
        cateListAdapter1=new CateListAdapter(SetCategoryActivity.this,R.layout.cate_item,categoryList);
        cateListAdapter1.watchedCateList=watchedCateList;
        final ListView listView1=(ListView) findViewById(R.id.set_watched_view);
        listView1.setAdapter(cateListAdapter1);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tmp = categoryList.get(position);
                Log.d("str",tmp);

                ImageView imageView=(ImageView)listView1.getChildAt((int)id).findViewById(R.id.cate_img);
                Log.d("id",""+id);
                if(watchedCateList.contains(tmp))
                {
                    watchedCateList.remove(tmp);
                    imageView.setImageResource(R.drawable.not_selected);
                }
                else
                {
                    watchedCateList.add(tmp);
                    imageView.setImageResource(R.drawable.selected);
                }
                cateListAdapter1.watchedCateList=watchedCateList;
                for(String i:watchedCateList)
                {
                    Log.d("watched ", i);
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent();
        intent.putExtra("watchedList",watchedCateList);
        setResult(RESULT_OK, intent);
        Log.d("send","ok");
        finish();
    }

}
