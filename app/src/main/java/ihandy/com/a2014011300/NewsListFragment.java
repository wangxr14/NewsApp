package ihandy.com.a2014011300;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ok on 2016/9/4.
 */
public class NewsListFragment extends Fragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ListView newsListView;
    private List<News> newsList;
    private NewsAdapter adapter;
    private ArrayList<News> favoriteList;
    private String category;
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private Handler handler;

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public void setFavoriteList(ArrayList<News> favoriteList) {
        this.favoriteList = favoriteList;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new NewsAdapter(getActivity(), R.layout.news_item, newsList);
        View view = inflater.inflate(R.layout.news_list, container, false);
        mSwipeRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshWidget.setOnRefreshListener(this);
        mSwipeRefreshWidget.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        News tmp = newsList.get(position);
        Intent intent = new Intent(getActivity(), ShowPageActivity.class);
        Log.d("activity", "mainActivity");
        Log.d("Intent", "main fragment  over  fav " + tmp.isFavorite());
        intent.putExtra("news", tmp);
        intent.putExtra("activity", "mainActivity");
        Log.d("Intent", category + " " + position);
        Log.d("intent", "sent to show!");
        getActivity().startActivityForResult(intent, 2);
    }

    @Override
    public void onRefresh() {
        //newsList.clear();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //Toast.makeText(getContext(),"refresh",Toast.LENGTH_SHORT).show();

          }
        }, 60);
       /* Runnable runnable=new Runnable() {
            @Override
            public void run() {
                NewsThread newsThread=new NewsThread();
                newsThread.setStatus(category, false, 0);
                /*newsThread.start();
                try {
                    //newsThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                newsList=newsThread.getNewsList();
                adapter.notifyDataSetChanged();
            }
        };*/
//        Toast.makeText(getContext(),"refresh",Toast.LENGTH_SHORT).show();
       // handler.postDelayed(runnable, 3000);

        //handler.removeCallbacks(runnable);
        mSwipeRefreshWidget.setRefreshing(false);
        Toast.makeText(getContext(),"no new data",Toast.LENGTH_SHORT).show();
    }
}
