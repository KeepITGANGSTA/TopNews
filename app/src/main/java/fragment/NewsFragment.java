package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.topnews.MainActivity;
import com.bwie.topnews.R;

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

/**
 * Created by 李英杰 on 2017/8/30.
 */
@ContentView(R.layout.news_fragment)
public class NewsFragment extends Fragment implements XListView.IXListViewListener{

    private View RootView;
    private NewsAdapter newsAdapter;
    private List<NewsBean> list;
    @ViewInject(R.id.mXListView)XListView mXListView;
    private String type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (RootView==null){
            RootView= x.view().inject(this,inflater,container);
        }
        return RootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        type = getArguments().getString("type");
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
        requestParams.addBodyParameter("type",type);
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
            newsAdapter = new NewsAdapter(getContext(),list);
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