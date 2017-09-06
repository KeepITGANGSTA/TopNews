package utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import bean.GsonBean;
import bean.NewsBean;

/**
 * Created by 李英杰 on 2017/9/6.
 */

public class NoneNetParse {
    private static List<NewsBean> list=new ArrayList<>();
    public static void lixianParse(String json){
        Gson gson=new Gson();
        GsonBean gsonBean = gson.fromJson(json, GsonBean.class);
        GsonBean.ResultBean result1 = gsonBean.result;
        List<GsonBean.ResultBean.DataBean> data = result1.data;
        for (GsonBean.ResultBean.DataBean dataBean : data) {
            NewsBean newsBean=new NewsBean();
            //newsBean.thumbnail_pic_s=dataBean.thumbnail_pic_s;
            String url=dataBean.thumbnail_pic_s;
            Bitmap bitmap = ImageLoader.getInstance().loadImageSync(url);

            list.add(newsBean);
        }
    }

    private void memoryImg(String url,Bitmap bitmap){
        String generate = new Md5FileNameGenerator().generate(url);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        

    }

}
