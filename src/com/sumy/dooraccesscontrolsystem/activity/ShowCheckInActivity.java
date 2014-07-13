package com.sumy.dooraccesscontrolsystem.activity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sumy.dooraccesscontrolsystem.R;
import com.sumy.dooraccesscontrolsystem.entity.CheckIn;

/**
 * 显示雇员签到信息
 * 
 * @author sumy
 * 
 */
public class ShowCheckInActivity extends BaseActivity {

    private ListView listview;
    private ArrayList<CheckIn> checkinlist;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_checkin;
    }

    @Override
    protected void initView() {
        checkinlist = checkInDao.findAll();
        listview = (ListView) findViewById(R.id.checkin_listview);
        listview.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, checkinlist));
        listview.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                    final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        ShowCheckInActivity.this);
                builder.setTitle("确定删除此签到信息？")
                        .setPositiveButton("确定", new OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                    int which) {
                                CheckIn checkin = checkinlist.get(position);
                                checkInDao.delete(checkin.getId());
                                checkinlist = checkInDao.findAll();
                                listview.setAdapter(new ArrayAdapter<>(
                                        ShowCheckInActivity.this,
                                        android.R.layout.simple_list_item_1,
                                        checkinlist));
                                listview.invalidate();
                            }
                        }).setNegativeButton("取消", null).show();
                return false;
            }
        });

    }

}
