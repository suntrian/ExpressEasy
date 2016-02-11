package com.gulum.expresseasy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gulum.expresseasy.util.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Administrator on 2016/2/6.
 */
public class TemplateHandler
{
    private Context mContext;

    private SQLiteDatabase db;

    private final String tableName = "sms_template";
    private String dbFilePath;
    private final String txtColName = "template_text";
    private final String dateColName = "date";
    private final String posColName = "position";

    public TemplateHandler(Context context){
        this.mContext = context;
        dbFilePath = mContext.getFilesDir().toString() + "/" + Config.DBFILE;
        File t = new File(dbFilePath);
        if (!t.exists()){
            db = SQLiteDatabase.openOrCreateDatabase(dbFilePath,null);
            createTable();
        }else {
            db =SQLiteDatabase.openDatabase(dbFilePath,null,SQLiteDatabase.OPEN_READWRITE);
        }
    }
    private void createTable(){
        //String sql = "create table if not exists "+ tableName +" (id integer primary key autoincrement, " + txtColName +" TEXT," + dateColName +" integer, "+ posColName + "varchar(50)";
        String sql = "create table if not exists ? (id integer primary key autoincrement, ? integer, ? varchar(50), ? TEXT";
        db.execSQL(sql,new String[]{tableName,dateColName,posColName,txtColName});
    }

    public long insertItem(String template, String pos){
        Long time = new Date().getTime();
        ContentValues values = new ContentValues();
        values.put(txtColName,template);
        values.put(dateColName, time);
        values.put(posColName, pos);
        long line =  db.insert(tableName,null,values);
        return line;
    }
    public List<Template> getAllItems(){
        List<Template> list = new ArrayList<Template>();
        Cursor cursor = db.query(tableName, null, null, null, null, null, "id");
        while (cursor.moveToNext()){
            long id = cursor.getInt(cursor.getColumnIndex("id"));
            long time = cursor.getLong(cursor.getColumnIndex(dateColName));
            String pos = cursor.getString(cursor.getColumnIndex(posColName));
            String text = cursor.getString(cursor.getColumnIndex(txtColName));
            Template tep = new Template(id,time,pos,text);
            list.add(tep);
        }
        cursor.close();
        return list;
    }
    public Template getLatestItem(){
        String sql = "select MAX(id), ?,?,? from ?";
        Cursor cursor = db.rawQuery(sql, new String[]{dateColName, posColName, txtColName, tableName});
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            long id = cursor.getInt(cursor.getColumnIndex("id"));
            long time = cursor.getLong(cursor.getColumnIndex(dateColName));
            String pos = cursor.getString(cursor.getColumnIndex(posColName));
            String text = cursor.getString(cursor.getColumnIndex(txtColName));
            Template tep = new Template(id,time,pos,text);
            return tep;
        }
        return null;
    }
    public int updateItems(int id, String text){
        ContentValues values = new ContentValues();
        values.put(txtColName,text);
        return db.update(tableName,values,"id=?",new String[]{String.valueOf(id)});
    }

    public int deleteItems(int id){
        return db.delete(tableName,"id = ?",new String[]{String.valueOf(id)});
    }

    public void close(){
        db.close();
    }

    public class Template{
        long id ;
        long time;
        String text ;
        String postion;
        public Template(long id, long time , String postion, String text){
            this.id = id;
            this.time = time;
            this.postion = postion;
            this.text = text;
        }
    }

}
