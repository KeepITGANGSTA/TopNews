package utils;

import android.os.AsyncTask;
import android.provider.ContactsContract;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import api.NewsAPI;

/**
 * Created by 李英杰 on 2017/8/31.
 */

public class NewsMore {

    public static String getdfs(String type){
        String parms="?type="+type+"&key="+NewsAPI.KEY;
        try {
            URL url1=new URL(NewsAPI.NEWS_URL);
            HttpURLConnection connection= (HttpURLConnection) url1.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            DataOutputStream dataOutputStream=new DataOutputStream(connection.getOutputStream());
            dataOutputStream.writeBytes(parms);
            dataOutputStream.flush();
            dataOutputStream.close();
            connection.connect();
            if (HttpURLConnection.HTTP_OK==connection.getResponseCode()){
                InputStream inputStream=connection.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                byte[] bytes=new byte[1024];
                int len;
                while ( (len=inputStream.read(bytes))!=-1 ){
                    byteArrayOutputStream.write(bytes,0,len);
                }
                return byteArrayOutputStream.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
