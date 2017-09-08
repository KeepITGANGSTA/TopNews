package com.bwie.topnews;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import adapter.RecyclerViewAdapter;
import api.NewsAPI;
import bean.TypeBean;
import dao.MyDao;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import utils.NetUtils;

public class NoneNetDownloadActivity extends SwipeBackActivity implements View.OnClickListener{

    private RecyclerView mRecyclerView;
    private List<TypeBean> list=new ArrayList<>();
    private RecyclerViewAdapter recyclerViewAdapter;
    private TextView back;
    private ImageView iv_settingBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_none_net_download);
        initView();
        initData();
        setSwipeBackEnable(true);
        SwipeBackLayout swipeBackLayout = getSwipeBackLayout();
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        swipeBackLayout.setEdgeSize(300);


    }

    /**
     *  初始化数据
     */
    private void initData() {
        TypeBean tj=new TypeBean();
        tj.type="推荐";
        tj.type_id="top";
        list.add(tj);

        TypeBean sh=new TypeBean();
        sh.type="社会";
        sh.type_id="shehui";
        list.add(sh);

        TypeBean gj=new TypeBean();
        gj.type="国际";
        gj.type_id="guoji";
        list.add(gj);

        TypeBean gn=new TypeBean();
        gn.type="国内";
        gn.type_id="guonei";
        list.add(gn);

        TypeBean yl=new TypeBean();
        yl.type="娱乐";
        yl.type_id="yule";
        list.add(yl);

        final TypeBean ty=new TypeBean();
        ty.type="体育";
        ty.type_id="tiyu";
        list.add(ty);

        TypeBean js=new TypeBean();
        js.type="军事";
        js.type_id="junshi";
        list.add(js);

        TypeBean kj=new TypeBean();
        kj.type="科技";
        kj.type_id="keji";
        list.add(kj);

        TypeBean cj=new TypeBean();
        cj.type="财经";
        cj.type_id="caijing";
        list.add(cj);

        TypeBean ssh=new TypeBean();
        ssh.type="时尚";
        ssh.type_id="shishang";
        list.add(ssh);

        recyclerViewAdapter = new RecyclerViewAdapter(this,list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setOnItemClickListenner(new RecyclerViewAdapter.OnItemClickListenner() {
            @Override
            public void onItemClickListenner(int position, View view) {
                CheckBox checkBox=view.findViewById(R.id.cb_cycleCb);
                TypeBean typeBean = list.get(position);
                if (checkBox.isChecked()){
                    checkBox.setChecked(false);
                    typeBean.isChecked=false;
                }else {
                    checkBox.setChecked(true);
                    typeBean.isChecked=true;
                }

                //修改原有的list数据，根据position，设置新的对象，更新list
                list.set(position,typeBean);
                for (TypeBean bean : list) {
                    System.out.println("Checked======="+bean.isChecked);
                }
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecycleView);
        back = (TextView) findViewById(R.id.tv_SettingLoad);
        iv_settingBack = (ImageView) findViewById(R.id.iv_SettingBack);
        back.setOnClickListener(this);
        iv_settingBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_SettingLoad:
                new NetUtils(this, new NetUtils.NetWork() {
                    @Override
                    public void NetWorkWifi() {
                        loadData();
                    }

                    @Override
                    public void NetWorkPhone() {
                        AlertDialog.Builder alertDialog=new AlertDialog.Builder(NoneNetDownloadActivity.this);
                        alertDialog.setMessage("移动网络离线下载会使用较多流量，是否继续？");
                        alertDialog.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                loadData();
                            }
                        });
                        alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        alertDialog.show();

                    }

                    @Override
                    public void NoneNetWork() {
                        Toast toast=Toast.makeText(NoneNetDownloadActivity.this,"当前网络不可用",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }
                });

                break;
            case R.id.iv_SettingBack:

                break;
        }
    }

    /**
     * 离线下载
     */
    private void loadData() {
        if (list!=null && list.size()>0){
            for (TypeBean typeBean : list) {
                if (typeBean.isChecked){
                    loadNewsData(typeBean.type_id);
                }
            }
        }

    }

    /**
     * 下载数据
     * @param type_id
     */
    private void loadNewsData(final String type_id) {
        RequestParams requestParams=new RequestParams(NewsAPI.NEWS_URL);
        requestParams.addBodyParameter("type",type_id);
        requestParams.addBodyParameter("key",NewsAPI.KEY);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //存入数据库
                System.out.println("result====="+result);
                MyDao.getSingleton(NoneNetDownloadActivity.this).insertLixian(type_id,result);
                Toast.makeText(NoneNetDownloadActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
