package com.travel.library.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.travel.library.utils.LogUtilsLib;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Wisn on 2018/6/12 下午1:31.
 * 当表比较简单的时候使用原生sql
 */
public class MultiTableHelper extends SQLiteOpenHelper {
    private final String TAG = "MultiTableHelper";
    private HashMap<String, String> tableSql;

    /**
     * 初始化构造函数
     *
     * @param context
     * @param name    数据库名
     * @param factory 游标工厂（基本不用）
     * @param version 版本号
     */
    public MultiTableHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 初始化构造函数
     *
     * @param context
     * @param dbName    数据库名
     * @param version   版本号
     * @param tablesSql 表名   SQL语句
     */
    public MultiTableHelper(Context context, String dbName, int version, HashMap<String, String> tablesSql) {
        this(context, dbName, null, version);
        this.tableSql = tablesSql;
    }

    // 当调用SQLiteDatabase中的getWritableDatabase()函数的时候会检测表是否存在，如果不存在onCreate将被调用创建表,否则将不会在被调用。
    @Override
    public void onCreate(SQLiteDatabase db) {
        if (db != null && tableSql != null) {
            Iterator<Map.Entry<String, String>> iterator = tableSql.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                String key = next.getKey();
                String value = next.getValue();
                LogUtilsLib.d(TAG, "tableName =" + key);
                LogUtilsLib.d(TAG, "sql=" + value);
//                db.execSQL("create table if not exists " + key + value);
                db.execSQL("create table " + key + value);
            }
        }
    }

    // 版本更新
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtilsLib.d(TAG, "oldVersion=" + oldVersion);
        LogUtilsLib.d(TAG, "newVersion=" + newVersion);
        if (db != null) {
            // 如果表存在就删除
            Iterator<Map.Entry<String, String>> iterator = tableSql.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                String key = next.getKey();
                String value = next.getValue();
                LogUtilsLib.d(TAG, "tableName =" + key);
                LogUtilsLib.d(TAG, "sql=" + value);
                db.execSQL("drop table if exists " + key);
            }
            // 重新初始化
            onCreate(db);
        }
    }

}
