package fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.bwie.topnews.DetailsActivity;
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
import dao.MyDao;
import utils.NetUtils;
import utils.NewsMore;
import utils.ParseUtils;
import view.xlistview.XListView;

/**
 * Created by 李英杰 on 2017/8/30.
 */
@ContentView(R.layout.news_fragment)
public class NewsFragment extends Fragment implements XListView.IXListViewListener,AdapterView.OnItemClickListener{

    private View RootView;
    private NewsAdapter newsAdapter;
    private List<NewsBean> list;
    @ViewInject(R.id.mXListView)XListView mXListView;
    private String type;
    private String mType="null";




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (RootView==null){
            RootView= x.view().inject(this,inflater,container);
        }else {
            ViewGroup viewGroup= (ViewGroup) RootView.getParent();
            if (viewGroup!=null){
                viewGroup.removeView(RootView);
            }
        }
        return RootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mXListView.setPullRefreshEnable(true);
        mXListView.setPullLoadEnable(true);
        mXListView.setXListViewListener(this);
        mXListView.setOnItemClickListener(this);
        type = getArguments().getString("type");
        if (type!=null){
            System.out.println("+_+_+++++++++++++_+_+_+"+type);
            new NetUtils(getContext(), new NetUtils.NetWork() {
                @Override
                public void NetWorkWifi() {
                    Toast.makeText(getContext(), "WiFi", Toast.LENGTH_SHORT).show();
                    if (mType.equals(type)){
                        getData();
                    }else {
                        getData();
                    }
                }

                @Override
                public void NetWorkPhone() {
                    Toast.makeText(getContext(), "手机", Toast.LENGTH_SHORT).show();
                    if (mType.equals(type)){
                        getData();
                    }else {
                        getData();
                    }
                }

                @Override
                public void NoneNetWork() {
                    Toast.makeText(getContext(), "网络不可用", Toast.LENGTH_SHORT).show();
                    String search = MyDao.getSingleton(getContext()).search(type);
                    list= ParseUtils.parseJson(search, type);
                    setAdapter();

                }
            });
        }




/*        if (mType!=null && !mType.equals(type)){

        }*/

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

                list= ParseUtils.parseJson(result,type);
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

        new Thread(){
            @Override
            public void run() {
                super.run();
                String json=NewsMore.getdfs(type);
                list=ParseUtils.parseMore(json);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setAdapter();
                    }
                });
            }
        }.start();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
       NewsBean newsBean= list.get(i);
        String url=newsBean.url;
        String title=newsBean.title;
        String date=newsBean.date;
        String authorName=newsBean.author_name;
        Intent intent=new Intent(getContext(), DetailsActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("date",date);
        intent.putExtra("authorName",authorName);
        intent.putExtra("url",url);
        startActivity(intent);
    }
}