package com.bwie.topnews;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.kson.slidingmenu.SlidingMenu;
import com.kson.slidingmenu.app.SlidingFragmentActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import adapter.NewsAdapter;
import api.NewsAPI;
import bean.NewsBean;
import bean.TypeBean;
import fragment.MenuLeftFragment;
import fragment.MenuRightFragment;
import utils.ParseUtils;
import view.HorizontalScrol;
import view.xlistview.XListView;

@ContentView(R.layout.activity_main)
public class MainActivity extends SlidingFragmentActivity implements View.OnClickListener {

    @ViewInject(R.id.left_iv) ImageView left_iv;
    @ViewInject(R.id.right_iv) ImageView right_iv;
    @ViewInject(R.id.horizontalScrol) HorizontalScrol horizontalScrol;
    private NewsAdapter newsAdapter;
    private List<Fragment> fragments=new ArrayList<>();
    private List<TypeBean> types=new ArrayList<>();
    private List<NewsBean> list;
    private SlidingMenu slidingMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        x.view().inject(this);
        initView();
        initMenu();
        initData();
    }

    private void initView() {
        left_iv.setOnClickListener(this);
        right_iv.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        TypeBean tj=new TypeBean();
        tj.type="推荐";
        tj.type_id="top";
        types.add(tj);

        TypeBean sh=new TypeBean();
        sh.type="社会";
        sh.type_id="shehui";
        types.add(sh);

        TypeBean gj=new TypeBean();
        gj.type="国际";
        gj.type_id="guoji";
        types.add(gj);

        TypeBean gn=new TypeBean();
        gn.type="国内";
        gn.type_id="guonei";
        types.add(gn);

        TypeBean yl=new TypeBean();
        yl.type="娱乐";
        yl.type_id="yule";
        types.add(yl);

        TypeBean ty=new TypeBean();
        ty.type="体育";
        ty.type_id="tiyu";
        types.add(ty);

        TypeBean js=new TypeBean();
        js.type="军事";
        js.type_id="junshi";
        types.add(js);

        TypeBean kj=new TypeBean();
        kj.type="科技";
        kj.type_id="keji";
        types.add(kj);

        TypeBean cj=new TypeBean();
        cj.type="财经";
        cj.type_id="caijing";
        types.add(cj);

        TypeBean ssh=new TypeBean();
        ssh.type="时尚";
        ssh.type_id="shishang";
        types.add(ssh);
        horizontalScrol.loadData(types,getSupportFragmentManager());
    }

    /**
     * 设置侧滑菜单
     */
    private void initMenu() {
        //设置做布局
        setBehindContentView(R.layout.left_menu_content);
        getSupportFragmentManager().beginTransaction().replace(R.id.left_menu_content,new MenuLeftFragment()).commit();
        //侧滑属性
        slidingMenu = getSlidingMenu();
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        slidingMenu.setBehindOffsetRes(R.dimen.BehindOffsetRes);
        slidingMenu.setFadeDegree(0.35f);

        //右菜单
        slidingMenu.setSecondaryMenu(R.layout.right_menu_content);
        getSupportFragmentManager().beginTransaction().replace(R.id.right_menu_content,new MenuRightFragment()).commit();
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
        }
    }
}
