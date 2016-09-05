package ihandy.com.a2014011300;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

/**
 * Created by ok on 2016/9/4.
 */
public class NewsListFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView newsListView;
    private List<News> newsList;
    private NewsAdapter adapter;

    public void setNewsList(List<News> newsList)
    {
        this.newsList=newsList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        adapter = new NewsAdapter(getActivity(), R.layout.news_item, newsList);
        View view = inflater.inflate(R.layout.news_list, container,false);
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view,int position,long id){
        News tmp = newsList.get(position);
        //String data = tmp.getNewsID() + ".html";
        String data =tmp.getSource_url();
        Intent intent = new Intent(getActivity(),ShowPageActivity.class);
        intent.putExtra("filename", data);
        startActivity(intent);
    }

}
