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
    public static List<NewsBean> parseJson(String json){
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
}
