package utils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import bean.GsonBean;
import bean.NewsBean;

/**
 * Created by 李英杰 on 2017/8/30.
 */

public class ParseUtils {
    private static List<NewsBean> list=new ArrayList<>();
    public static List<NewsBean> parseJson(String json,String type){
        list.clear();
        Gson gson=new Gson();
        GsonBean gsonBean = gson.fromJson(json, GsonBean.class);
        GsonBean.ResultBean result1 = gsonBean.result;
        List<GsonBean.ResultBean.DataBean> data = result1.data;
        for (GsonBean.ResultBean.DataBean dataBean : data) {
            NewsBean newsBean=new NewsBean();
            newsBean.title=dataBean.title;
            newsBean.date=dataBean.date;
            newsBean.author_name=dataBean.author_name;
            newsBean.thumbnail_pic_s=dataBean.thumbnail_pic_s;
            list.add(newsBean);
        }
        return list;
    }

/*    private static List<NewsBean> parse(String json,List<NewsBean> list){

        return list;
    }*/

    public static List<NewsBean> parseMore(String json){
        int i=0;

        Gson gson=new Gson();
        GsonBean gsonBean = gson.fromJson(json, GsonBean.class);
        GsonBean.ResultBean result1 = gsonBean.result;
        List<GsonBean.ResultBean.DataBean> data = result1.data;

        for (GsonBean.ResultBean.DataBean dataBean : data) {
            i++;
            NewsBean newsBean=new NewsBean();
            newsBean.title=dataBean.title;
            newsBean.date=dataBean.date;
            newsBean.author_name=dataBean.author_name;
            newsBean.thumbnail_pic_s=dataBean.thumbnail_pic_s;
            list.add(newsBean);
            if (i==9){
                break;
            }
        }
        return list;
    }

}
