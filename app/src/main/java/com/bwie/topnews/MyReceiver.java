package com.bwie.topnews;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())){
            //用户点击推送消息时要处理的逻辑

            Toast.makeText(context, "我是点击推送消息", Toast.LENGTH_SHORT).show();
            System.out.println("！！！！！！！！！！！！！！！！我是点击推送消息@@@@@@@@@@@@@@@@@@@@@@@@@@");
/*            Intent intent1=new Intent(context,NoneNetDownloadActivity.class);
            context.startActivity(intent1);*/
        }

/*        // an Intent broadcast.
        throw new UnsupportedOperationException("Not yet implemented");*/
    }
}
