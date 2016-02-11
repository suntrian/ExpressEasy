package com.gulum.expresseasy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.gulum.expresseasy.util.Config;

import java.io.File;

/**
 * Created by Administrator on 2016/2/11.
 */
public class SendlogHandler
{
    private Context mContext;
    private SQLiteDatabase db;
    private String dbFilePath;

    private final String logTableName = "sendlog";
    private final String[] colName = {"sendtime","position","phonenum","agentid"};

    public SendlogHandler(Context context)
    {
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
        String sql = "create table if not exists ? (id integer primary key autoincrement, ? integer, ? varchar(50), ? varchar(11), ? integer";
        db.execSQL(sql,colName);
    }




}
