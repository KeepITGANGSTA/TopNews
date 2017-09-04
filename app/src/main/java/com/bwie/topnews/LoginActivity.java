package com.bwie.topnews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.MobSDK;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import api.NewsAPI;
import bean.UserInfo;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import utils.SharedPreferencesUtil;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView login_ivBack;
    private EditText login_edPhone;
    private TextView login_tvSend;
    private EditText login_edVeriCode;
    private TextView login_tvEnter;
    private CheckBox login_cbClause;
    private TextView login_tvAcntMsd;
    private ImageView login_ivWeiXin;
    private ImageView login_ivQQ;
    private ImageView login_ivWeiBo;
    private ImageView login_ivKn;
    private ImageView login_ivMsg;
    private int time=6;
    private int delayed=1000;
    private Handler handler=new Handler();
    Runnable task=new Runnable() {
        @Override
        public void run() {
            time--;
            if (time==0){
                handler.removeCallbacks(task);
                time=5;
                login_tvSend.setEnabled(true);
                login_tvSend.setText("重新获取");
            }else {
                login_tvSend.setEnabled(false);
                login_tvSend.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                login_tvSend.setText(time+"s");
                handler.postDelayed(this,delayed);
            }
        }
    };
    private EventHandler eh;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh); //注销回调接口
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initView();
        registerSMS();
    }

    /**
     * 注册短信监听
     */
    private void registerSMS() {
        //回调完成
        //提交验证码成功
        //获取验证码成功
        //返回支持发送验证码的国家列表
        eh = new EventHandler(){

            @Override
            public void afterEvent(int event, int result, Object data) {

                if (data instanceof Throwable){
                    Throwable throwable= (Throwable) data;
                    final String msg=throwable.getMessage();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        //回调完成
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            //提交验证码成功
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "提交验证码成功", Toast.LENGTH_SHORT).show();
                                    UserInfo userInfo=new UserInfo();
                                    userInfo.phone=login_edPhone.getText().toString();
                                    userInfo.id=login_edPhone.getText().toString();
                                    SharedPreferencesUtil.putPreferences("UserPhone",userInfo.phone);
                                    SharedPreferencesUtil.putPreferences("UserId",userInfo.id);
                                    SharedPreferencesUtil.putPreferences("isFirst","true");
                                }
                            });
                            Intent success=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(success);
                            finish();
                        }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                            //获取验证码成功
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                            //返回支持发送验证码的国家列表
                        }
                    }else{
                        ((Throwable)data).printStackTrace();
                    }
                }


            }
        };
        SMSSDK.registerEventHandler(eh); //注册
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_ivBack:
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.login_edPhone:

                break;
            case R.id.login_tvSend: //发送验证码
                String phone=login_edPhone.getText().toString();
                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(this, "输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                handler.postDelayed(task,delayed);
                SMSSDK.getVerificationCode(NewsAPI.ChinaArea,login_edPhone.getText().toString()); // 向手机发送验证码
                break;
            case R.id.login_edVeriCode:
                break;
            case R.id.login_tvEnter:
                if (TextUtils.isEmpty(login_edPhone.getText().toString())) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(login_edVeriCode.getText().toString())) {
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!login_cbClause.isChecked()){
                    Toast.makeText(this, "请阅读用户协议和隐私条款", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 提交
                SMSSDK.submitVerificationCode(NewsAPI.ChinaArea,login_edPhone.getText().toString(),login_edVeriCode.getText().toString());
                break;
            case R.id.login_cbClause:
                break;
            case R.id.login_tvAcntMsd:
                break;
            case R.id.login_ivWeiXin:
                break;
            case R.id.login_ivQQ: //QQ登录
                Toast.makeText(this, "qqdenglu", Toast.LENGTH_SHORT).show();
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.QQ,umAuthListener);
                break;
            case R.id.login_ivWeiBo:
                Toast.makeText(this, "微博登录", Toast.LENGTH_SHORT).show();
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.SINA, umAuthListener);

                break;
            case R.id.login_ivKn:
                break;
            case R.id.login_ivMsg:
                break;
        }
    }

    /**
     * 有盟授权回调
     */
    UMAuthListener umAuthListener=new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
        }


        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            String name = map.get("name");
            String gender=map.get("gender");
            String iconurl = map.get("iconurl");
            Toast.makeText(LoginActivity.this, name+","+gender+"sdfsd", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            String message = throwable.getMessage();
            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
        }
    };

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
    }

    private void initView() {
        login_ivBack = (ImageView) findViewById(R.id.login_ivBack);
        login_edPhone = (EditText) findViewById(R.id.login_edPhone);
        login_tvSend = (TextView) findViewById(R.id.login_tvSend);
        login_edVeriCode = (EditText) findViewById(R.id.login_edVeriCode);
        login_tvEnter = (TextView) findViewById(R.id.login_tvEnter);
        login_cbClause = (CheckBox) findViewById(R.id.login_cbClause);
        login_tvAcntMsd = (TextView) findViewById(R.id.login_tvAcntMsd);
        login_ivWeiXin = (ImageView) findViewById(R.id.login_ivWeiXin);
        login_ivQQ = (ImageView) findViewById(R.id.login_ivQQ);
        login_ivWeiBo = (ImageView) findViewById(R.id.login_ivWeiBo);
        login_ivKn = (ImageView) findViewById(R.id.login_ivKn);
        login_ivMsg = (ImageView) findViewById(R.id.login_ivMsg);

        login_ivBack.setOnClickListener(this);
        login_edPhone.setOnClickListener(this);
        login_tvSend.setOnClickListener(this);
        login_edVeriCode.setOnClickListener(this);
        login_tvEnter.setOnClickListener(this);
        login_cbClause.setOnClickListener(this);
        login_tvAcntMsd.setOnClickListener(this);
        login_ivWeiXin.setOnClickListener(this);
        login_ivQQ.setOnClickListener(this);
        login_ivWeiBo.setOnClickListener(this);
        login_ivKn.setOnClickListener(this);
        login_ivMsg.setOnClickListener(this);
    }



}
