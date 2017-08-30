package com.bwie.topnews;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.x;

/**
 * Created by 李英杰 on 2017/8/30.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        initXUtils();
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
