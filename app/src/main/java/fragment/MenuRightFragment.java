package fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.topnews.NoneNetDownloadActivity;
import com.bwie.topnews.R;

import org.w3c.dom.Text;

import dao.MyDao;
import utils.SharedPreferencesUtil;

/**
 * Created by 李英杰 on 2017/8/30.
 */

public class MenuRightFragment extends Fragment implements View.OnClickListener{
    private View mRootView;
    private TextView tv_noneNetDownload;
    private RelativeLayout relativeLayout;
    private RelativeLayout rel_clearCache;
    private TextView tv_cache;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView==null){
            mRootView=inflater.inflate(R.layout.right_menu_layout,container,false);
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        tv_noneNetDownload = mRootView.findViewById(R.id.tv_noneNetDownload);
        tv_noneNetDownload.setOnClickListener(this);
        relativeLayout = mRootView.findViewById(R.id.rel_NoneNet);
        rel_clearCache = mRootView.findViewById(R.id.rel_clearCache);
        rel_clearCache.setOnClickListener(this);
        relativeLayout.setOnClickListener(this);
        tv_cache = mRootView.findViewById(R.id.tv_cache);
        tv_cache.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_noneNetDownload:
                startActivity(new Intent(getContext(), NoneNetDownloadActivity.class));
                break;
            case R.id.rel_NoneNet:
                final TextView textView=relativeLayout.findViewById(R.id.tv_feiwifi);
                final SharedPreferences preferences = SharedPreferencesUtil.getPreferences();
                final SharedPreferences.Editor edit = preferences.edit();
                boolean rb_best1 = preferences.getBoolean("rb_best",false);
                boolean rb_better1 = preferences.getBoolean("rb_better", false);
                boolean rb_terrible1 = preferences.getBoolean("rb_terrible", false);


                AlertDialog.Builder alertDialog=new AlertDialog.Builder(getContext());
                alertDialog.setTitle("非WiFi网络流量");
                View view1= LayoutInflater.from(getContext()).inflate(R.layout.alertdialog_feiwifi,null);
                alertDialog.setView(view1);
                final AlertDialog show = alertDialog.show();
                RelativeLayout best=view1.findViewById(R.id.rel_best);
                RelativeLayout better=view1.findViewById(R.id.rel_better);
                RelativeLayout terrible=view1.findViewById(R.id.rel_terrible);
                final RadioButton rb_best=view1.findViewById(R.id.rb_bese);
                final RadioButton rb_better=view1.findViewById(R.id.rb_better);
                final RadioButton rb_terrible=view1.findViewById(R.id.rb_terrible);
                final TextView tv_best=view1.findViewById(R.id.tv_best);
                final TextView tv_better=view1.findViewById(R.id.tv_better);
                final TextView tv_terrible=view1.findViewById(R.id.tv_terrible);

                rb_best.setChecked(rb_best1);
                rb_better.setChecked(rb_better1);
                rb_terrible.setChecked(rb_terrible1);
                best.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferencesUtil.putPreferences("state","best");
                        rb_best.setChecked(true);
                        edit.putBoolean("rb_best",true).commit();
                        edit.putBoolean("rb_better",false).commit();
                        edit.putBoolean("rb_terrible",false).commit();
                        textView.setText(tv_best.getText().toString());
                        show.dismiss();
                    }
                });
                better.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferencesUtil.putPreferences("state","better");
                        rb_better.setChecked(true);
                        edit.putBoolean("rb_best",false).commit();
                        edit.putBoolean("rb_better",true).commit();
                        edit.putBoolean("rb_terrible",false).commit();
                        textView.setText(tv_better.getText().toString());
                        show.dismiss();
                    }
                });
                terrible.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferencesUtil.putPreferences("state","terrible");
                        rb_terrible.setChecked(true);
                        edit.putBoolean("rb_best",false).commit();
                        edit.putBoolean("rb_better",false).commit();
                        edit.putBoolean("rb_terrible",true).commit();
                        textView.setText(tv_terrible.getText().toString());
                        show.dismiss();
                    }
                });
                alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                break;
            case R.id.rel_clearCache:
                MyDao.getSingleton(getContext()).clearCache();
                Toast.makeText(getContext(), "清空完成", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}