package ihandy.com.a2014011300;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FavoritePageActivity extends AppCompatActivity {

    private ListView listView;
    private NewsAdapter adapter;
    private ArrayList<News> favoriteList;
    private Map<String,ArrayList<News>> newsMap=new HashMap<String,ArrayList<News>>();
    private ArrayList<String> categoryList=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_page);

        Intent intent=getIntent();
        favoriteList= (ArrayList<News>) intent.getSerializableExtra("favorites");
        categoryList=intent.getStringArrayListExtra("categoryList");
        for(int i=0;i<categoryList.size();i++)
        {
            ArrayList<News> tmp=(ArrayList<News>) intent.getSerializableExtra(categoryList.get(i));
            newsMap.put(categoryList.get(i),tmp);
        }
        Log.d("favorite list","size "+favoriteList.size());
        for (int i =0;i<favoriteList.size();i++) {
            Log.d("favorite list", favoriteList.get(i).getTitle() + " is fav " + favoriteList.get(i).isFavorite());
        }
        adapter=new NewsAdapter(FavoritePageActivity.this,R.layout.news_item,favoriteList);
        listView=(ListView)findViewById(R.id.list_view_favorite);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News tmp = favoriteList.get(position);
                Intent intent = new Intent(FavoritePageActivity.this,ShowPageActivity.class);
                Log.d("activity", "FavoriteActivity");
                intent.putExtra("news", tmp);
                Log.d("Intent", "favorite page   fav " + tmp.isFavorite());
                intent.putExtra("activity","FavoriteActivity");
                startActivityForResult(intent, 3);
            }
        });
    }


    public void setListView()
    {

    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        Log.d("Intent"+FavoritePageActivity.this.toString(), "get " + requestCode);
        switch (requestCode)
        {
            case 3:
                if(resultCode==RESULT_OK)
                {
                    News tmp=(News) data.getSerializableExtra("news");
                    Log.d("Intent","Map size "+newsMap.size());
                    Log.d("Intent","cate "+tmp.getCategory());
                    //find this news in newsmap
                    ArrayList<News> tmpList=newsMap.get(tmp.getCategory());
                    int pos=0;
                    for(int i =0;i<tmpList.size();i++)
                    {
                        if(tmpList.get(i).getNewsID()==tmp.getNewsID())
                        {
                            pos=i;
                            break;
                        }
                    }
                    //Log.d("Intent"," here");
                    newsMap.get(tmp.getCategory()).get(pos).setFavorite(tmp.isFavorite());
                    boolean flag=false;//mark if in the list
                    for (int i =0;i<favoriteList.size();i++)
                    {
                        if(favoriteList.get(i).getNewsID()==tmp.getNewsID())//is in favoriteList
                        {
                            flag=true;
                            if(tmp.isFavorite()==0)
                            {
                                favoriteList.remove(i);
                            }
                        }
                    }
                    if(!flag)
                    {
                        if(tmp.isFavorite()==1)
                        {
                            favoriteList.add(tmp);
                        }
                    }

                    for (int i =0;i<favoriteList.size();i++) {
                        Log.d("fav in favoriteActivity", favoriteList.get(i).getTitle()+" is fav "+favoriteList.get(i).isFavorite() );
                    }
                    //set adapter
                    adapter=new NewsAdapter(FavoritePageActivity.this,R.layout.news_item,favoriteList);
                    listView.setAdapter(adapter);
                }
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(FavoritePageActivity.this,MainActivity.class);
        intent.putExtra("favorites",favoriteList);
        Iterator<Map.Entry<String, ArrayList<News>>> it = newsMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, ArrayList<News>> entry = it.next();
            ArrayList<News> tmp=entry.getValue();
            intent.putExtra(entry.getKey(),tmp);
        }
        setResult(RESULT_OK, intent);
        //Log.d("Intent", category + " " + position);
        Log.d("intent","favorite page sent!");
        finish();
    }
}
