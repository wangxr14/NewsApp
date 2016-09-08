package ihandy.com.a2014011300;

import android.content.IntentFilter;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

    //private ArrayList<ArrayList<News>> newsList=new ArrayList<ArrayList<News>>();
    private Map<String,ArrayList<News>> newsMap=new HashMap<String,ArrayList<News>>();
    private ArrayList<String> categoryList=new ArrayList<String>();
    private ArrayList<String> watchedCateList=new ArrayList<String>();
    private ArrayList<News> favoritesList=new ArrayList<News>();

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPageAdapter viewPageAdapter;
    private ArrayList<NewsListFragment> newsFragmentList;
    private ArrayList<Fragment> fragmentList;
    //public FragmentManager fManager;
    private MyDataDealer myDataDealer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        //getSupportActionBar().hide();
        toolbar= (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        myDataDealer=new MyDataDealer(this);
        //init the categories and news
        initCate();
        initLists();
        Log.d("progress:", "init News");
        initNews();
        if(categoryList.size()==0)//没网的情况
        {
            loadDataFromSQL();
        }
        //get the tabLayout
        setTabLayout();
        setViewPage();
        setDrawer();
        //
       /* localBroadcastManager=LocalBroadcastManager.getInstance(this);
        intentFilter=new IntentFilter();
        intentFilter.addAction("ihandy.com.a2014011300.LOCAL_BROADCAST");
        myBroadcastReceiver=new MyBroadcastReceiver();
        myBroadcastReceiver.set_List_Map(favoritesList,newsMap);
        localBroadcastManager.registerReceiver(myBroadcastReceiver,intentFilter);*/
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
        categoryList=myThread.getCategoryList();
    }
    public void initLists()
    {
        for(int i=0;i<categoryList.size();++i)
        {
            watchedCateList.add(categoryList.get(i));
        }
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
            newsMap.put(categoryList.get(i), myThread.getNewsList());
        }

    }

    public void loadDataFromSQL()
    {
        Log.d("load","start");
        //读出四个列表
        categoryList=myDataDealer.getCateList();
        Log.d("load","category");
        Log.d("size",""+categoryList.size());
        watchedCateList=myDataDealer.getWatchedList();
        Log.d("load","watched");
        newsMap=myDataDealer.getNewsMap(categoryList);
        Log.d("load","news map");
        favoritesList=myDataDealer.getFavoriteList();
        Log.d("load","favorite");
        Log.d("load","done");
    }
    public void saveDataToSQL()
    {
        myDataDealer.clearTable();
        //save category
        for(int i=0;i<categoryList.size();i++)
        {
            if(watchedCateList.contains(categoryList.get(i)))
            {
                myDataDealer.insert_category(categoryList.get(i),1);
            }
            else myDataDealer.insert_category(categoryList.get(i),0);
        }
        //save news
        Iterator<Map.Entry<String, ArrayList<News>>> it = newsMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, ArrayList<News>> entry = it.next();
            Log.d("key",entry.getKey());
            ArrayList<News> tmp=entry.getValue();
            for(int i=0;i<tmp.size();i++)
            {
                myDataDealer.insert_news(tmp.get(i));
            }
            Log.d("save "+entry.getKey(),""+tmp.size());
        }
    }

    public void setTabLayout()
    {

        tabLayout=(TabLayout) findViewById(R.id.tab_title);
        tabLayout.removeAllTabs();
        for (int i=0;watchedCateList.size()>i;i++){
            TabLayout.Tab tmpTab = tabLayout.newTab().setText(watchedCateList.get(i));
            Log.d("tab:", "" + watchedCateList.get(i));
            tabLayout.addTab(tmpTab);
        }

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    public void setViewPage()
    {
        FragmentManager fManager=getSupportFragmentManager();

        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_pager);
        newsFragmentList=new ArrayList<NewsListFragment>();
        fragmentList=new ArrayList<Fragment>();

        for(int i=0;i<watchedCateList.size();i++)
        {
            NewsListFragment tmp=new NewsListFragment();
            tmp.setNewsList(newsMap.get(watchedCateList.get(i)));
            tmp.setFavoriteList(favoritesList);
            tmp.setCategory(watchedCateList.get(i));
            //Log.d("newsList "+i,newsList.get(i).get(0).getCategory());
            newsFragmentList.add(tmp);
            fragmentList.add(newsFragmentList.get(i));
        }

        viewPageAdapter=new ViewPageAdapter(fManager,fragmentList,watchedCateList);

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
                switch (menuItem.getItemId()) {
                    case R.id.drawer_favorite_Item01:

                        break;
                    case R.id.drawer_set_cate_Item02:
                        Intent intent2 = new Intent(MainActivity.this, SetCategoryActivity.class);
                        intent2.putExtra("category list", categoryList);
                        intent2.putExtra("watched list", watchedCateList);
                        startActivityForResult(intent2, 1);
                        break;
                    case R.id.drawer_about_Item03:
                        Intent intent3 = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(intent3);
                        break;
                }
                //menuItem.setChecked(true);//点击了把它设为选中状态
                //mDrawerLayout.closeDrawers();//关闭抽屉
                return true;
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        Log.d("Intent", "get " + requestCode);
        switch (requestCode)
        {
            //change the category
            case 1:
                if(resultCode==RESULT_OK)
                {
                    this.watchedCateList=data.getStringArrayListExtra("watchedList");
                    setTabLayout();
                    setViewPage();
                }
                break;
            //update favorites
            default:
                if(resultCode==RESULT_OK)
                {
                    Log.d("Intent","get "+2);
                    String cate=data.getStringExtra("category");
                    int pos=data.getIntExtra("position", 0);
                    boolean isFavorite=data.getBooleanExtra("favorite", false);
                    Log.d("Intent",cate+" "+pos);
                    News tmp=newsMap.get(cate).get(pos);

                    //Log.d("Intent"," here");
                    if(isFavorite)
                    {
                        tmp.setFavorite(1);
                        if(!favoritesList.contains(tmp))
                        {
                            favoritesList.add(tmp);
                        }
                    }
                    else
                    {
                        tmp.setFavorite(0);
                        if(favoritesList.contains(tmp))
                        {
                            favoritesList.remove(tmp);
                        }
                    }

                    for(News i:favoritesList)
                    {
                        Log.d("fav",i.getTitle());
                    }
                }
                Log.d("Intent"," ok");
                break;
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        saveDataToSQL();
        Log.d("finish","ok");
    }
}

