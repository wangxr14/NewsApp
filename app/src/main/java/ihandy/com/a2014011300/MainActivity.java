package ihandy.com.a2014011300;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;
import android.app.Activity;

import org.json.JSONException;
import org.json.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.io.*;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private List<News> newsList=new ArrayList<News>();
    private List<String> categoryList=new ArrayList<String>();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main);
        //init the categories and news
        initNews();
        //get the tabLayout
        initTabLayout();

        NewsAdapter adapter = new NewsAdapter(MainActivity.this, R.layout.news_item, newsList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News tmp = newsList.get(position);
                String data = tmp.getNewsID() + ".html";
                Intent intent = new Intent(MainActivity.this, ShowPageActivity.class);
                intent.putExtra("filename", data);
                startActivity(intent);
            }
        });
        //getSupportActionBar().hide();

    }

    public void initNews()
    {
        //get categories
        CateThread myThread=new CateThread();
        myThread.start();
        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        categoryList=myThread.categoryList;

        for(int i=1;i<51;i++)
        {
            newsList.add(new News(i,"News "+i,"","","",i));
        }
    }

    public void initTabLayout()
    {
        tabLayout=(TabLayout) findViewById(R.id.tab_title);
        for (int i=0;categoryList.size()>i;i++){
            TabLayout.Tab tmpTab = tabLayout.newTab().setText(categoryList.get(i));
            Log.d("tab:", "" + categoryList.get(i));
            tabLayout.addTab(tmpTab);
        }
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        String str="test data";
        switch (item.getItemId())
        {
            case R.id.option1:
                Intent intent = new Intent(MainActivity.this,Option1Activity.class);
                intent.putExtra("extra data",str);
                startActivity(intent);
                break;
            case R.id.option2:
                Toast.makeText(this,"option 2",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

}
