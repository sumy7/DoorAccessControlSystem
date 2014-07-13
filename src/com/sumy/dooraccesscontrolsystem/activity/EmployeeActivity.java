package com.sumy.dooraccesscontrolsystem.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.sumy.dooraccesscontrolsystem.R;
import com.sumy.dooraccesscontrolsystem.adapter.EmployeeAdapter;
import com.sumy.dooraccesscontrolsystem.entity.CheckIn;

/**
 * 模拟雇员签到
 * 
 * @author sumy
 * 
 */
public class EmployeeActivity extends BaseActivity {

    private ListView listView;
    private Button btn_photo;
    private Button btn_pic;
    private EditText et_name;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_input_empolyee;
    }

    @Override
    protected void initView() {

        listView = (ListView) findViewById(R.id.listViewEmpolyee);
        btn_photo = (Button) findViewById(R.id.buttonEmpolyeePhoto);
        btn_pic = (Button) findViewById(R.id.buttonEmpolyeePic);
        et_name = (EditText) findViewById(R.id.editTextEmpolyeeName);

        btn_photo.setVisibility(View.GONE);
        btn_pic.setVisibility(View.GONE);
        et_name.setVisibility(View.GONE);

        EmployeeAdapter employeeAdapter = new EmployeeAdapter(this,
                R.layout.activity_input_empolyee_listitem,
                doorSystem.getUserlist());
        listView.setAdapter(employeeAdapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // 获取点击项目信息
                String name = doorSystem.getUserlist().get(position).getName();
                String time = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                        .format(new Date());
                // 存入数据库
                CheckIn checkin = new CheckIn(name, time);
                checkInDao.insert(checkin);
                showToast("已签到  姓名：" + name + " 时间：" + time);
                setResult(RESULT_OK);
                finish();
            }
        });
    }

}
