package ihandy.com.a2014011300;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.*;

import java.io.IOException;

public class ShowPageActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private WebView webView;
    private MenuItem fav;
    private Menu menu;
    private News news;
    private String url;
    private String parentActivity;
    private IWXAPI api;
    private static final String APP_ID="wx5e85e455cbd317ae";
    private Bitmap bitmap=null;
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
        Log.d("Picasso", "get");
        /*try {
            bitmap=Picasso.with(this).load(news.getImage_url()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Log.d("WX","reg");
        regToWX();

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_share:
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/*");

                        intent.putExtra(Intent.EXTRA_SUBJECT, "shot!");
                        intent.putExtra(Intent.EXTRA_TEXT, "have a big news");
                        //intent.putExtra(Intent.EXTRA_STREAM, news.getImage_url());
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
                    case R.id.action_share_to_WX:
                        sendMsg();
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
        Log.d("Intent", "show page  over  fav " + news.isFavorite());
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

    private void regToWX()
    {
        api=WXAPIFactory.createWXAPI(this,APP_ID,true);
        api.registerApp(APP_ID);
    }

    private void sendMsg() {
        if (!api.isWXAppInstalled()) {
            Toast.makeText(ShowPageActivity.this, "您还未安装微信客户端",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        //WXWebpageObject webpage = new WXWebpageObject();
        //webpage.webpageUrl=url;
        //WXMediaMessage msg = new WXMediaMessage(webpage);
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);

        msg.title = news.getTitle();
        msg.description = news.getSource_url();
        Bitmap thumb = BitmapFactory.decodeResource(getResources(),
                R.drawable.share);
        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }
}
