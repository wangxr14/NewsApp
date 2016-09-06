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
    private boolean isFavorite=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_page);
        toolbar=(Toolbar)findViewById(R.id.toolbar_show_page);
        setSupportActionBar(toolbar);

        Intent intent=getIntent();
        String filename=intent.getStringExtra("filename");
        isFavorite=intent.getBooleanExtra("favorite",false);
        webView=(WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        Log.d("filename", filename);
        webView.loadUrl("file:///android_asset/test.html");
        Log.d("load", "ok");

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_share:
                        break;
                    case R.id.action_favorite:

                        if (isFavorite) {
                            isFavorite = false;
                            Log.d("fav", "true");
                            menuItem.setIcon(R.drawable.favorite_not_selected);
                            Toast.makeText(ShowPageActivity.this, "Cancel Favorite!", Toast.LENGTH_SHORT).show();
                        } else {
                            isFavorite = true;
                            Log.d("fav", "false");
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
        return  true;
    }


}
