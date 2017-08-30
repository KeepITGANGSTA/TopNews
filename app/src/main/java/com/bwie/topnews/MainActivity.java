package com.bwie.topnews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import adapter.NewsAdapter;
import api.NewsAPI;
import bean.NewsBean;
import utils.ParseUtils;
import view.xlistview.XListView;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements XListView.IXListViewListener{

    @ViewInject(R.id.mXListView) XListView mXListView;
    private NewsAdapter newsAdapter;
    private List<NewsBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        x.view().inject(this);
        mXListView.setPullRefreshEnable(true);
        mXListView.setPullLoadEnable(true);
        mXListView.setXListViewListener(this);

        getData();
    }

    /**
     * 获取数据
     */
    private void getData() {
        final RequestParams requestParams=new RequestParams(NewsAPI.NEWS_URL);
        requestParams.addBodyParameter("type",NewsAPI.TYPE);
        requestParams.addBodyParameter("key",NewsAPI.KEY);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("json=="+result);
                list= ParseUtils.parseJson(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                setAdapter();
            }
        });

    }

    /**
     * 适配
     */
    private void setAdapter(){
        if (newsAdapter==null){
            newsAdapter = new NewsAdapter(MainActivity.this,list);
            mXListView.setAdapter(newsAdapter);
        }else {
            newsAdapter.notifyDataSetChanged();
        }
        mXListView.stopLoadMore();
        mXListView.stopRefresh();
    }


    @Override
    public void onRefresh() {
        setAdapter();
    }

    @Override
    public void onLoadMore() {
        setAdapter();
    }
}
