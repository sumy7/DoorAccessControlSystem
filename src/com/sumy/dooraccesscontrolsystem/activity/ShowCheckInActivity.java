package com.sumy.dooraccesscontrolsystem.activity;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sumy.dooraccesscontrolsystem.R;

/**
 * 显示雇员签到信息
 * 
 * @author sumy
 * 
 */
public class ShowCheckInActivity extends BaseActivity {

    private ListView listview;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_checkin;
    }

    @Override
    protected void initView() {
        listview = (ListView) findViewById(R.id.checkin_listview);
        listview.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, checkInDao.findAll()));
    }

}
