package com.sumy.dooraccesscontrolsystem.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sumy.dooraccesscontrolsystem.entity.CheckIn;

/**
 * 对数据库进行增删改查操作
 * 
 * @author sumy
 * 
 */
public class CheckInDao {
    private CheckInHelper helper;

    public CheckInDao(Context context) {
        super();
        this.helper = new CheckInHelper(context);
    }

    /**
     * 插入数据
     * 
     * @param checkIn
     *            要插入到数据库的实体类
     * @return 新插入的数据的ID，如果出错返回 -1
     */
    public int insert(CheckIn checkIn) {
        // 数据库数据操作类
        ContentValues values = new ContentValues();
        // 把数据封装到数据库操作类中
        values.put("name", checkIn.getName());
        values.put("time", checkIn.getDate());
        // 通过 Helper 获取数据库
        SQLiteDatabase database = helper.getWritableDatabase();
        // 插入数据
        int id = (int) database.insert("checkin", null, values);
        return id;
    }

    /**
     * 获取数据库中的所有签到条目
     * 
     * @return 获取到的签到条目，如果出错返回 null
     */
    public ArrayList<CheckIn> findAll() {
        ArrayList<CheckIn> checkinlist = new ArrayList<CheckIn>();
        // 通过 Helper 获取数据库
        SQLiteDatabase database = helper.getReadableDatabase();
        // 获取结果的游标
        Cursor cursor = database.query("checkin", null, null, null, null, null,
                null);
        // 通过游标读出所有数据保存到列表中
        while (cursor.moveToNext()) {
            CheckIn checkin = new CheckIn(cursor.getString(1),
                    cursor.getString(2));
            checkin.setId(cursor.getInt(0));
            checkinlist.add(checkin);

        }
        return checkinlist;
    }

    /**
     * 根据 ID 来删除条目
     * 
     * @param id
     *            要删除的ID
     * @return 删除的条目数
     */
    public int delete(int id) {
        SQLiteDatabase database = helper.getWritableDatabase();
        String[] args = { Integer.toString(id) };
        int cnt = database.delete("checkin", "id =?", args);
        return cnt;
    }
}
