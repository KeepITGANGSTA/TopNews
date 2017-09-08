package com.bwie.topnews;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;
import com.kson.slidingmenu.SlidingMenu;
import com.kson.slidingmenu.app.SlidingFragmentActivity;
import com.umeng.socialize.UMShareAPI;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.List;

import adapter.NewsAdapter;
import api.NewsAPI;
import bean.NewsBean;
import bean.TypeBean;
import dao.MyDao;
import fragment.MenuLeftFragment;
import fragment.MenuRightFragment;
import utils.ParseUtils;
import utils.SharedPreferencesUtil;
import view.HorizontalScrol;
import view.xlistview.XListView;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @ViewInject(R.id.left_iv) ImageView left_iv;
    @ViewInject(R.id.right_iv) ImageView right_iv;
    @ViewInject(R.id.horizontalScrol) HorizontalScrol horizontalScrol;
    private NewsAdapter newsAdapter;
    private List<Fragment> fragments=new ArrayList<>();
    private List<TypeBean> types=new ArrayList<>();
    private List<NewsBean> list;
    private SlidingMenu slidingMenu;
    private ImageView iv_channel;
    private List<ChannelBean> channelBeanList=new ArrayList<>();
    private List<TypeBean> cbb;
    private String channel;
    private String channel1;
    private String s1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        initView();
        initMenu();
        s1 = SharedPreferencesUtil.getPreferencesValue("channel");
        System.out.println("似的似的发射点发射点发射点！！！！！！"+ s1);
        if (s1.equals("")){
            initData();
        }else {
            System.out.println("是否为空~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+ s1);
            cbb = ParseUtils.parseChannel(s1);
            for (int i = 0; i < cbb.size(); i++) {
                TypeBean cb=cbb.get(i);
                System.out.println("ssdfsdfsf"+cb.isChecked);
                if (cb.isChecked){
                    if (cb.type.equals("时尚")){
                        cb.type_id="shishang";

                    }else if (cb.type.equals("财经")){
                        cb.type_id="caijing";
                    }else if (cb.type.equals("科技")){
                        cb.type_id="keji";
                    }else if (cb.type.equals("体育")){
                        cb.type_id="tiyu";
                    }else if (cb.type.equals("娱乐")){
                        cb.type_id="yule";
                    }else if (cb.type.equals("国内")){
                        cb.type_id="guonei";
                    }else if (cb.type.equals("国际")){
                        cb.type_id="guoji";
                    }else if (cb.type.equals("社会")){
                        cb.type_id="shehui";
                    }else if (cb.type.equals("推荐")){
                        cb.type_id="top";
                    }else if (cb.type.equals("财经")){
                        cb.type_id="caijing";
                    }

                }
                types.add(cb);

            }
            horizontalScrol.loadData(types,getSupportFragmentManager());
        }

    }

    private void initView() {
        left_iv.setOnClickListener(this);
        right_iv.setOnClickListener(this);
        iv_channel = (ImageView) findViewById(R.id.iv_channel);
        iv_channel.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {

        TypeBean tj=new TypeBean();
        tj.type="推荐";
        tj.type_id="top";
        types.add(tj);
        ChannelBean tuijian=new ChannelBean("推荐",true);
        channelBeanList.add(tuijian);

        TypeBean sh=new TypeBean();
        sh.type="社会";
        sh.type_id="shehui";
        types.add(sh);
        ChannelBean shehui=new ChannelBean("社会",true);
        channelBeanList.add(shehui);

        TypeBean gj=new TypeBean();
        gj.type="国际";
        gj.type_id="guoji";
        types.add(gj);
        ChannelBean guoji=new ChannelBean("国际",true);
        channelBeanList.add(guoji);

        TypeBean gn=new TypeBean();
        gn.type="国内";
        gn.type_id="guonei";
        types.add(gn);
        ChannelBean guonei=new ChannelBean("国内",true);
        channelBeanList.add(guonei);

        TypeBean yl=new TypeBean();
        yl.type="娱乐";
        yl.type_id="yule";
        types.add(yl);
        ChannelBean yule=new ChannelBean("娱乐",true);
        channelBeanList.add(yule);


        TypeBean ty=new TypeBean();
        ty.type="体育";
        ty.type_id="tiyu";
        types.add(ty);
        ChannelBean tiyu=new ChannelBean("体育",true);
        channelBeanList.add(tiyu);

        TypeBean js=new TypeBean();
        js.type="军事";
        js.type_id="junshi";
        types.add(js);
        ChannelBean junshi=new ChannelBean("军事",true);
        channelBeanList.add(junshi);

        TypeBean kj=new TypeBean();
        kj.type="科技";
        kj.type_id="keji";
        types.add(kj);
        ChannelBean keji=new ChannelBean("科技",true);
        channelBeanList.add(keji);

        TypeBean cj=new TypeBean();
        cj.type="财经";
        cj.type_id="caijing";
        types.add(cj);
        ChannelBean caijing=new ChannelBean("财经",true);
        channelBeanList.add(caijing);

        TypeBean ssh=new TypeBean();
        ssh.type="时尚";
        ssh.type_id="shishang";
        types.add(ssh);
        ChannelBean shishang=new ChannelBean("时尚",true);
        channelBeanList.add(shishang);


        horizontalScrol.loadData(types,getSupportFragmentManager());
    }

    /**
     * 设置侧滑菜单
     */
    private void initMenu() {
        slidingMenu = new SlidingMenu(this);
        //侧滑属性
        // slidingMenu = getSlidingMenu();
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        slidingMenu.setBehindOffsetRes(R.dimen.BehindOffsetRes);
        slidingMenu.setFadeDegree(0.35f);
        //设置做布局
        slidingMenu.setMenu(R.layout.left_menu_content);
       // setBehindContentView(R.layout.left_menu_content);
        getSupportFragmentManager().beginTransaction().replace(R.id.left_menu_content,new MenuLeftFragment()).commit();

        //右菜单
        slidingMenu.setSecondaryMenu(R.layout.right_menu_content);
        getSupportFragmentManager().beginTransaction().replace(R.id.right_menu_content,new MenuRightFragment()).commit();
        slidingMenu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.left_iv:
                slidingMenu.showMenu();
                break;
            case R.id.right_iv:
                slidingMenu.showSecondaryMenu();
                break;
            case R.id.iv_channel:
                //ChannelActivity.startChannelActivity(this,channelBeanList);
                String s2 = SharedPreferencesUtil.getPreferencesValue("channel");
                if (s2.equals("")){
                    ChannelActivity.startChannelActivity(this,channelBeanList);
                }else {
                    List<ChannelBean> channelBeen = ParseUtils.parseCh(s2);
                    ChannelActivity.startChannelActivity(this,channelBeen);
                }

                break;
        }
    }

    /**
     * 有盟复写onActivityResult
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);


        if (resultCode==101){
            String json = data.getStringExtra("json");
            if (TextUtils.isEmpty(json)){
                Toast.makeText(this, "空", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, json, Toast.LENGTH_SHORT).show();
                System.out.println("频道==========="+json);
                types.clear();
                SharedPreferencesUtil.putPreferences("channel",json);
                channel1 = SharedPreferencesUtil.getPreferencesValue("channel");
/*                MyDao.getSingleton(this).insertLixian("channel",json);
                channel1 = MyDao.getSingleton(this).search("channel");*/
                System.out.println("数据库=========="+channel1);
                cbb = ParseUtils.parseChannel(channel1);

                for (int i = 0; i < cbb.size(); i++) {
                    TypeBean cb=cbb.get(i);
                    System.out.println("ssdfsdfsf"+cb.isChecked);
                    if (cb.isChecked){
                        if (cb.type.equals("时尚")){
                            cb.type_id="shishang";
                        }else if (cb.type.equals("财经")){
                            cb.type_id="caijing";
                        }else if (cb.type.equals("科技")){
                            cb.type_id="keji";
                        }else if (cb.type.equals("体育")){
                            cb.type_id="tiyu";
                        }else if (cb.type.equals("娱乐")){
                            cb.type_id="yule";
                        }else if (cb.type.equals("国内")){
                            cb.type_id="guonei";
                        }else if (cb.type.equals("国际")){
                            cb.type_id="guoji";
                        }else if (cb.type.equals("社会")){
                            cb.type_id="shehui";
                        }else if (cb.type.equals("推荐")){
                            cb.type_id="top";
                        }else if (cb.type.equals("财经")){
                            cb.type_id="caijing";
                        }

                    }
                    types.add(cb);

                }
                for (int i = 0; i < types.size(); i++) {
                    System.out.println("添加！！！！！！！！！！！！！！！！！！"+types.get(i).type_id);
                }
                horizontalScrol.loadData(types,getSupportFragmentManager());
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //types.clear();
    }
}