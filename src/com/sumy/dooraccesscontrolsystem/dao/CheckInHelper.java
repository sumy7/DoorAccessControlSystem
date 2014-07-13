package com.sumy.dooraccesscontrolsystem.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLite数据库Helper类
 * 
 * @author sumy
 * 
 */
public class CheckInHelper extends SQLiteOpenHelper {

    /**
     * 
     * @param context
     *            上下文
     */
    public CheckInHelper(Context context) {
        // 参数依次为：上下文、数据库名称、游标工厂、数据库版本号
        super(context, "checkInDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 数据库创建时被调用一次，完成初始化工作
        // 创建表
        String sql = "create table checkin (id integer primary key autoincrement, name varchar(20),time varchar(100))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 版本号发生变化时被调用，完成数据库变更升级
    }

}
