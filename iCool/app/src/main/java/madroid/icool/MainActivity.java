package madroid.icool;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by HND6KOR on 8/17/2018.
 */

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ProgressBar mProgressbar;
    private WebView mWebView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String ICOOL_URL = "Your URL Comes here";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mWebView = findViewById(R.id.main_content);
        mProgressbar = findViewById(R.id.progressbar);
        mSwipeRefreshLayout = findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        loadUrl();
    }

    private void loadUrl() {
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100 && mProgressbar.getVisibility() == ProgressBar.GONE) {
                    mProgressbar.setVisibility(ProgressBar.VISIBLE);
                }

                mProgressbar.setProgress(progress);
                if (progress == 100) {
                    mProgressbar.setVisibility(ProgressBar.GONE);
                }
            }

        });
        mWebView.setWebViewClient(new MyBrowser());
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(ICOOL_URL);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        loadUrl();
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            mProgressbar.setVisibility(ProgressBar.GONE);
        }
    }
}
