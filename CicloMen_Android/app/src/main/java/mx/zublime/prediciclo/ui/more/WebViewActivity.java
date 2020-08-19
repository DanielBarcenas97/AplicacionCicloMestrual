package mx.zublime.prediciclo.ui.more;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.zublime.prediciclo.R;

public class WebViewActivity extends AppCompatActivity {

    private static final String URL_KEY = "url_key";
    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        getData();
    }

    private void getData(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            loadPage(bundle.getString(URL_KEY));
        }
    }

    private void  loadPage(String url){
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }


    public static Intent createIntent(Context packge, String url){
        Intent intent = new Intent(packge,WebViewActivity.class);
        intent.putExtra(URL_KEY,url);
        return  intent;
    }
}
