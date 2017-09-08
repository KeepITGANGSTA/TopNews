package utils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bean.ChannelBean;
import bean.GsonBean;
import bean.NewsBean;
import bean.TypeBean;

/**
 * Created by 李英杰 on 2017/8/30.
 */

public class ParseUtils {
    private static List<NewsBean> list=new ArrayList<>();
    private static List<TypeBean> channelBeanList=new ArrayList<>();


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
            newsBean.url=dataBean.url;
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
            newsBean.url=dataBean.url;
            list.add(newsBean);
            if (i==9){
                break;
            }
        }
        return list;
    }

    public static List<TypeBean> parseChannel(String channelJson){
        channelBeanList.clear();
        try {
            JSONArray array=new JSONArray(channelJson);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object=array.getJSONObject(i);
                TypeBean channelBean=new TypeBean();
                if (object.getBoolean("isSelect")){
                    channelBean.isChecked=object.getBoolean("isSelect");
                    channelBean.type=object.getString("name");
                    channelBeanList.add(channelBean);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return channelBeanList;
    }

    public static List<com.andy.library.ChannelBean> parseCh(String channelJson){
        List<com.andy.library.ChannelBean> cblist=new ArrayList<>();
        try {
            JSONArray array=new JSONArray(channelJson);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object=array.getJSONObject(i);
                com.andy.library.ChannelBean channelBean=new com.andy.library.ChannelBean(object.getString("name"),object.getBoolean("isSelect"));
                cblist.add(channelBean);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cblist;
    }

}