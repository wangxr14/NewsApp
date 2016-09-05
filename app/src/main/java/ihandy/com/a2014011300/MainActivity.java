package ihandy.com.a2014011300;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

    private ArrayList<ArrayList<News>> newsList=new ArrayList<ArrayList<News>>();
    private List<String> categoryList=new ArrayList<String>();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPageAdapter viewPageAdapter;
    private ArrayList<NewsListFragment> newsFragmentList;
    private ArrayList<Fragment> fragmentList;
    public FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        //init the categories and news
        initCate();
        Log.d("progress:", "init News");
        initNews();
        //get the tabLayout
        initTabLayout();
        initViewPage();
        setDrawer();
        //getSupportActionBar().hide();

    }
    //get the category order in the List
    public int getCateOrder(String x)
    {
        switch (x) {
            case "top_stories": return 0;
            case "technology":return 1;
            case "national":return 2;
            case "sports":return 3;
            case "health":return 4;
            case "world":return 5;
            case "more top stories":return 6;
            case "entertainment": return 7;
            case "business":return 8;
            case "science":return 9;
            default:break;
        }
        return 0;
    }

    public void initCate()
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
    }

    public void initNews()
    {

        for(int i=0;i<categoryList.size();i++)
        {
            NewsThread myThread = new NewsThread();
            myThread.setStatus(categoryList.get(i),true,0);
            myThread.start();
            try {
                myThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            newsList.add(myThread.getNewsList());

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

    public void initViewPage()
    {
        fManager=getSupportFragmentManager();

        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_pager);
        newsFragmentList=new ArrayList<NewsListFragment>();
        fragmentList=new ArrayList<Fragment>();
        for(int i=0;i<categoryList.size();i++)
        {

            NewsListFragment tmp=new NewsListFragment();
            tmp.setNewsList(newsList.get(i));
            //Log.d("newsList "+i,newsList.get(i).get(0).getCategory());
            newsFragmentList.add(tmp);
            fragmentList.add(newsFragmentList.get(i));
        }
        viewPageAdapter=new ViewPageAdapter(fManager,fragmentList,categoryList);
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setDrawer()
    {
        //设置导航栏NavigationView的点击事件
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.drawer_favorite_Item01:

                        break;
                    case R.id.drawer_set_cate_Item02:
                        break;
                    case R.id.drawer_about_Item03:
                        Intent intent = new Intent(MainActivity.this,AboutActivity.class);
                        startActivity(intent);
                        break;
                }
                //menuItem.setChecked(true);//点击了把它设为选中状态
                //mDrawerLayout.closeDrawers();//关闭抽屉
                return true;
            }
        });
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
