package com.bwie.topnews;

import android.app.Application;
import android.content.Context;

import com.mob.MobSDK;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

import api.NewsAPI;

/**
 * Created by 李英杰 on 2017/8/30.
 */

public class App extends Application {

    {
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
    }

    public static Context appContext ;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext=this;
        initImageLoader();
        initXUtils();
        MobSDK.init(this, NewsAPI.AppKey,NewsAPI.AppSecret);
        UMShareAPI.get(this);

    }

    /**
     * 初始化Xutils
     */
    private void initXUtils() {
        x.Ext.init(this);
    }

    /**
     * 初始化ImageLoader
     */
    private void initImageLoader() {
        DisplayImageOptions options =new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(configuration);
    }
}
