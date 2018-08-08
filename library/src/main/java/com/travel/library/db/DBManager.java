package com.travel.library.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.travel.library.utils.LogUtilsLib;

import java.util.HashMap;
import java.util.Iterator;


/**
 * Created by Wisn on 2018/6/12 下午1:35.
 * 当表比较简单的时候使用原生sql
 */
public class DBManager {
    private final String TAG = "DBManager";
    private Context context;
    private SQLiteDatabase mSQLiteDatabase;
    private SQLiteOpenHelper mSQLiteOpenHelper;
    private Cursor mCursor;
    private StringBuffer mSqlBuffer;
    private boolean isSqlSuccessed = false;

    public DBManager(Context context) {
        super();
        this.context = context;
        mSqlBuffer = new StringBuffer();
    }

    /**
     * 创建多表数据库
     *
     * @param dbName    数据库名称
     * @param version   数据库版本
     * @param tablesSql 数据库表名  sql语句
     */
    public void create(String dbName, int version, HashMap<String, String> tablesSql) {
        if (isSqlSuccessed || tablesSql.size() > 0) {
            Iterator<String> iterator = tablesSql.values().iterator();
            while (iterator.hasNext()) {
                String sql = iterator.next();
                if (!isLegalSql(sql)) {
                    LogUtilsLib.e(TAG, sql + " Sql语句不合法");
                }
            }
            if (mSQLiteOpenHelper == null) {
                mSQLiteOpenHelper = new MultiTableHelper(context, dbName, version, tablesSql);
            }
        } else {
            LogUtilsLib.e(TAG, "Sql语句不合法");
        }

    }

    /**
     * 创建单表数据库
     *
     * @param dbName    数据库名称
     * @param version   数据库版本
     * @param tableName 数据库表名
     * @param sql       sql语句
     */
    public void create(String dbName, int version, String tableName, String sql) {
        if (isSqlSuccessed || isLegalSql(sql)) {
            HashMap<String, String> tablesSql = new HashMap<>();
            tablesSql.put(tableName, sql);
            mSQLiteOpenHelper = new MultiTableHelper(context, dbName, version, tablesSql);
        } else {
            LogUtilsLib.e(TAG, sql + " Sql语句不合法");
        }
    }

    /**
     * 是否为合法Sql语句
     */
    private boolean isLegalSql(String sql) {
        if (sql != null && sql.length() > 1) {
            if ("(".equals(sql.charAt(0) + "") && ")".equals(sql.charAt(sql.length() - 1) + "")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 添加主键
     */
    public DBManager addPrimaryKey() {
        mSqlBuffer.append("_id INTEGER PRIMARY KEY autoincrement,");
        return this;
    }

    /**
     * 创建TEXT型字段
     *
     * @param key 字段名
     */
    public DBManager addText(String key) {
        mSqlBuffer.append(key + " text,");
        return this;
    }

    /**
     * 创建BLOB型字段
     *
     * @param key 字段名
     */
    public DBManager addBlob(String key) {
        mSqlBuffer.append(key + " blob,");
        return this;
    }


    /**
     * 创建INTEGER型字段
     *
     * @param key 字段名
     */
    public DBManager addInteger(String key) {
        mSqlBuffer.append(key + " INTEGER,");
        return this;
    }

    /**
     * 获取SQL语句
     */
    public String getSql() {
        String sql = null;
        if (mSqlBuffer.length() > 0) {
            sql = mSqlBuffer.toString();
            sql = sql.substring(0, sql.length() - 1);
            sql = "( " + sql + " )";
            LogUtilsLib.i(TAG, "getSql: " + sql);
            mSqlBuffer = new StringBuffer();
            isSqlSuccessed = true;
        }
        return sql;
    }

    /**
     * 执行一条sql语句
     *
     * @param sql
     */
    public void mExecSQL(String sql) {
        mSQLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
        mSQLiteDatabase.execSQL(sql);
//        closeCursor();
    }

    /**
     * 增加数据
     *
     * @param tableName      表名
     * @param nullColumnHack 非空字段名
     * @param values         数据源
     */
    public void mInsert(String tableName, String nullColumnHack, ContentValues values) {
        mSQLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
        mSQLiteDatabase.insert(tableName, nullColumnHack, values);
//        closeCursor();
    }

    /**
     * 删除数据
     *
     * @param tableName   表名
     * @param whereClause （eg:"_id=?"）
     * @param whereArgs   （eg:new String[] { "01" } ）
     */
    public void mDelete(String tableName, String whereClause, String[] whereArgs) {
        mSQLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
        mSQLiteDatabase.delete(tableName, whereClause, whereArgs);
    }

    /**
     * 更新
     *
     * @param tableName
     * @param values
     * @param whereClause
     * @param whereArgs
     */
    public void mUpdate(String tableName, ContentValues values, String whereClause, String[] whereArgs) {
        mSQLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
        mSQLiteDatabase.update(tableName, values, whereClause, whereArgs);
    }

    /**
     * 更新
     *
     * @param tableName   表名
     * @param values      更新的数据
     * @param whereClause 更新的条件（eg:_id = 01）
     */
    public void mUpdate(String tableName, ContentValues values, String whereClause) {
        mSQLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
        mSQLiteDatabase.update(tableName, values, whereClause, null);
    }

    /**
     * 查询
     *
     * @param tableName
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param orderBy
     *
     * @return mCursor 游标
     */
    public Cursor mQuery(String tableName, String[] columns, String selection,
                         String[] selectionArgs, String groupBy, String having,
                         String orderBy) {
        mSQLiteDatabase = mSQLiteOpenHelper.getReadableDatabase();
        mCursor = mSQLiteDatabase.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy);
        return mCursor;
    }

    /**
     * 查询全部(查询后需要在调用的类中手动调用closeAll()方法来关闭全部函数)
     *
     * @param tableName 表名
     * @param orderBy   排序方式（asc升序，desc降序）
     *
     * @return mCursor 游标
     */
    public Cursor mQueryAll(String tableName, String orderBy) {
        mSQLiteDatabase = mSQLiteOpenHelper.getReadableDatabase();
        mCursor = mSQLiteDatabase.query(tableName, null, null, null, null, null, orderBy);
        return mCursor;
    }

    /**
     * 从数据库中删除表
     *
     * @param tableName 表名
     */
    public void mDropTable(String tableName) {
        mSQLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
        mSQLiteDatabase.execSQL("drop table if exists " + tableName);
        LogUtilsLib.e(TAG, "已删除" + tableName + "表");
//        closeCursor();
    }

    /**
     * 删除表中的全部数据
     *
     * @param tableName 表名
     */
    public void mDeleteTable(String tableName) {
        mSQLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
        mSQLiteDatabase.execSQL("delete from " + tableName);
        LogUtilsLib.e(TAG, "已清空" + tableName + "表");
//        closeCursor();
    }

    /**
     * 判断某张表是否存在
     *
     * @param tableName 表名
     *
     * @return true 存在
     */
    public boolean isTableExist(String tableName) {
        boolean result = false;
        if (tableName == null) {
            return false;
        }
        try {
            mSQLiteDatabase = mSQLiteOpenHelper.getReadableDatabase();
            String sql = "select count(*) as c from sqlite_master where type ='table' and name ='" + tableName.trim() + "' ";
            mCursor = mSQLiteDatabase.rawQuery(sql, null);
            if (mCursor.moveToNext()) {
                int count = mCursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            LogUtilsLib.e(TAG, tableName + "表不存在");
        }
//        closeCursor();
        return result;
    }

    /**
     * 获取表中有多少条数据
     *
     * @param tableName
     *
     * @return
     */
    public int getDataNum(String tableName) {
        mSQLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
        mCursor = mSQLiteDatabase.query(tableName, null, null, null, null, null, null);
        int num = mCursor.getCount();
//        closeCursor();
        return num;
    }

    /**
     * 数据库是否存在要查询的这条数据
     *
     * @param tableName  表名
     * @param columnName 需要查询字段
     * @param data       需要查询数据
     *
     * @return
     */
    public boolean hasThisData(String tableName, String columnName, String data) {
        boolean result = false;
        mSQLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
        mCursor = mSQLiteDatabase.query(tableName, null, null, null, null, null, null);
        while (mCursor.moveToNext()) {
            String columnValues = mCursor.getString(mCursor.getColumnIndex(columnName));
            // 有这条数据  
            if (data.equals(columnValues)) {
                result = true;
                break;
            }
        }
//        closeCursor();
        return result;
    }

    public void beginTransaction() {
        if (mSQLiteDatabase != null) {
            mSQLiteDatabase.beginTransaction();
        }
    }

    public void endTransaction() {
        if (mSQLiteDatabase != null) {
            mSQLiteDatabase.endTransaction();
        }
    }

    public void setTransactionSuccessful() {
        if (mSQLiteDatabase != null) {
            mSQLiteDatabase.setTransactionSuccessful();
        }
    }

    /**
     * 关闭全部
     */
    public void closeCursor() {
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
    }

    public void closeDatabase() {
        if (mSQLiteOpenHelper != null) {
            mSQLiteOpenHelper.close();
        }
        if (mSQLiteDatabase != null && mSQLiteDatabase.isOpen()) {
            mSQLiteDatabase.close();
        }
    }
}
