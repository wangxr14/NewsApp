package ihandy.com.a2014011300;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ShowPageActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private WebView webView;
    private MenuItem fav;
    private Menu menu;
    private News news;
    private String url;
    private String parentActivity;
    //private boolean isFavorite=false;
    //private String category;
    //private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_page);
        toolbar=(Toolbar)findViewById(R.id.toolbar_show_page);
        setSupportActionBar(toolbar);

        Intent intent=getIntent();
        //final String filename=intent.getStringExtra("filename");
        news=(News)intent.getSerializableExtra("news");
        url=news.getSource_url();
        parentActivity=intent.getStringExtra("activity");
        Log.d("Intent", parentActivity);
        Log.d("Intent", "show page   fav "+news.isFavorite());
        webView=(WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_share:
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/*");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                        intent.putExtra(Intent.EXTRA_TEXT, "SPOT! " + url);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(Intent.createChooser(intent, getTitle()));
                        break;
                    case R.id.action_favorite:

                        if (news.isFavorite() == 1) {
                            news.setFavorite(0);
                            Log.d("fav", "false");
                            menuItem.setIcon(R.drawable.favorite_not_selected);
                            Toast.makeText(ShowPageActivity.this, "Cancel Favorite!", Toast.LENGTH_SHORT).show();
                        } else {
                            news.setFavorite(1);
                            Log.d("fav", "true");
                            menuItem.setIcon(R.drawable.favorite_selected);
                            Toast.makeText(ShowPageActivity.this, "Add Favorite!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }

                return true;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_page_menu, menu);
        fav=menu.findItem(R.id.action_favorite);
        if (news.isFavorite()==1) fav.setIcon(R.drawable.favorite_selected);
        else fav.setIcon(R.drawable.favorite_not_selected);
        return  true;
    }


    @Override
    public void onBackPressed()
    {
        Log.d("Intent", "show page  over  fav "+news.isFavorite());
        if(parentActivity.equals("mainActivity"))
        {
            Log.d("Intent", "to main 1");
            Intent intent = new Intent(ShowPageActivity.this,MainActivity.class);
            Log.d("Intent", "to main 2");
            intent.putExtra("news", news);
            Log.d("Intent", "to main 3");
            setResult(RESULT_OK, intent);

        }
        else
        {
            Log.d("Intent", "to fav 1");
            Intent intent = new Intent(ShowPageActivity.this,FavoritePageActivity.class);
            Log.d("Intent", "to fav 2");
            intent.putExtra("news", news);
            Log.d("Intent", "to fav 3");
            setResult(RESULT_OK, intent);
        }

        Log.d("intent","show page intent sent!");

        super.onBackPressed();
    }

}
