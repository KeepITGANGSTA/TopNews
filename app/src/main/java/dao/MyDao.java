package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import sql.SQLdao;

/**
 * Created by 李英杰 on 2017/9/5.
 */

public class MyDao {


    private static SQLdao sqLdao;
    private static Context context;


    private MyDao(){}
    private static final MyDao singleton =new MyDao();
    public  static MyDao getSingleton(Context context){
        sqLdao = new SQLdao(context);
        return singleton;
    }


    //添加
    public static void insertLixian(String name,String json){
        SQLiteDatabase writableDatabase = sqLdao.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("name",name);
        cv.put("json",json);
        writableDatabase.insert("lixianLoad",null,cv);
        writableDatabase.close();
    }

    //查询
    public static String search(String name){
        SQLiteDatabase writableDatabase = sqLdao.getWritableDatabase();
        Cursor lixianLoad = writableDatabase.query("lixianLoad", new String[]{"json"}, "name=?", new String[]{name}, null, null, null);
        if (lixianLoad.moveToNext()){
            return lixianLoad.getString(lixianLoad.getColumnIndex("json"));
        }
        return null;
    }

    //删除
    public static void delete(String name){
        SQLiteDatabase writableDatabase = sqLdao.getWritableDatabase();
        writableDatabase.delete("lixianLoad","name=?",new String[]{name});
        writableDatabase.close();
    }

    //清空
    public static void clearCache(){
        SQLiteDatabase writableDatabase = sqLdao.getWritableDatabase();
        writableDatabase.delete("lixianLoad",null,null);
        writableDatabase.close();
    }

}
