package com.bwie.topnews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class DetailsActivity extends SwipeBackActivity {

    private String title;
    private String date;
    private String authorName;
    private String url;
    private TextView tv_title_details;
    private TextView tv_date_details;
    private TextView tv_authorName_details;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setSwipeBackEnable(true);
        SwipeBackLayout swipeBackLayout = getSwipeBackLayout();
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        swipeBackLayout.setEdgeSize(300);


        initData();
        initView();

    }

    /**
     * 初始化控件
     */
    private void initView() {
        tv_title_details = (TextView) findViewById(R.id.tv_title_details);
        tv_date_details = (TextView) findViewById(R.id.tv_date_details);
        tv_authorName_details = (TextView) findViewById(R.id.tv_authorName_details);
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        tv_title_details.setText(title);
        tv_date_details.setText(date);
        tv_authorName_details.setText(authorName);
        webView.loadUrl(url);
    }

    /**
     * 获取数据
     */
    private void initData() {
        title = getIntent().getStringExtra("title");
        date = getIntent().getStringExtra("date");
        authorName = getIntent().getStringExtra("authorName");
        url = getIntent().getStringExtra("url");
    }
}
