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
    private String category;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_page);
        toolbar=(Toolbar)findViewById(R.id.toolbar_show_page);
        setSupportActionBar(toolbar);

        Intent intent=getIntent();
        final String filename=intent.getStringExtra("filename");
        isFavorite=intent.getBooleanExtra("favorite",false);
        category=intent.getStringExtra("category");
        position=intent.getIntExtra("position",0);

        webView=(WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(filename);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_share:
                        Intent intent=new Intent(Intent.ACTION_SEND);
                        intent.setType("text/*");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                        intent.putExtra(Intent.EXTRA_TEXT, "Big News From Clout! "+filename);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(Intent.createChooser(intent, getTitle()));
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

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(ShowPageActivity.this,MainActivity.class);
        intent.putExtra("favorite",isFavorite);
        intent.putExtra("category",category);
        intent.putExtra("position",position);
        setResult(RESULT_OK, intent);
        Log.d("Intent", category + " " + position);
        Log.d("intent","sent!");
        finish();
    }

}
