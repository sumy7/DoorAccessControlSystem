package com.sumy.dooraccesscontrolsystem.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.sumy.dooraccesscontrolsystem.R;
import com.sumy.dooraccesscontrolsystem.adapter.EmployeeAdapter;
import com.sumy.dooraccesscontrolsystem.entity.Employee;
import com.sumy.dooraccesscontrolsystem.entity.Manager;
import com.sumy.dooraccesscontrolsystem.entity.User;

/**
 * 录入雇员信息
 * 
 * @author sumy
 * 
 */
public class InputEmployeeActivity extends BaseActivity implements
        OnClickListener {
    private static int EMPLOYEE_PHOTO_REQUEST_CODE = 2333;
    private static int EMPLOYEE_ALBUM_REQUEST_CODE = 8010;

    private ArrayList<User> userlist;

    private ListView listView;
    private Button btn_photo;
    private Button btn_pic;
    private EditText et_name;

    private EmployeeAdapter employeeAdapter;

    // 临时存储录入的雇员信息
    private String employeeNum;
    private String employeeName;
    private String employeeCardID;
    private String employeePhoto;

    // 临时变量存储要删除的位置
    private int currentLongClickPosition;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_input_empolyee;
    }

    @Override
    protected void initView() {
        userlist = doorSystem.getUserlist();

        listView = (ListView) findViewById(R.id.listViewEmpolyee);
        btn_photo = (Button) findViewById(R.id.buttonEmpolyeePhoto);
        btn_pic = (Button) findViewById(R.id.buttonEmpolyeePic);
        et_name = (EditText) findViewById(R.id.editTextEmpolyeeName);

        employeeAdapter = new EmployeeAdapter(this,
                R.layout.activity_input_empolyee_listitem, userlist);
        listView.setAdapter(employeeAdapter);

        btn_photo.setOnClickListener(this);
        btn_pic.setOnClickListener(this);

        listView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Log.i("mytag", "position:" + position);
                // 排除初始管理员
                if (position == 0) {
                    listView.setOnCreateContextMenuListener(null);
                    return false;
                }
                showOptionMenu(position);
                return false;
            }
        });

    }

    /**
     * 为长按项目创建上下文菜单
     * 
     * @param position
     *            长按项目的位置编号
     */
    protected void showOptionMenu(final int position) {
        // 标记当前长按的条目
        currentLongClickPosition = position;
        // 显示listview的条目菜单监听器
        listView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v,
                    ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("请选择操作");
                // 判断雇员是否有卡
                User user = userlist.get(position);
                if (user instanceof Employee) {
                    Employee employee = (Employee) user;
                    if (employee.isHasCard()) {
                        menu.add(0, 0, Menu.NONE, "删除");
                    } else {
                        menu.add(0, 1, Menu.NONE, "制卡");
                        menu.add(0, 0, Menu.NONE, "删除");
                    }
                }
                if (user instanceof Manager) {
                    menu.add(0, 0, Menu.NONE, "删除");
                }
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case 0:
            // 删除
            showDeleteDialog();
            break;
        case 1:
            // 制卡
            Intent intent = new Intent(this, NFCInputActivity.class);
            intent.putExtra("position", currentLongClickPosition);
            startActivity(intent);
            break;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * 创建并显示确认删除对话框
     */
    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("是否删除？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        User user = userlist.get(currentLongClickPosition);
                        if (user instanceof Employee) {
                            // Employee employee = (Employee) user;
                            // 删除图片文件
                            // File photofile = new File(employee.getPhoto());
                            // photofile.delete();
                            userlist.remove(currentLongClickPosition);
                        }
                        // 刷新列表
                        employeeAdapter.notifyDataSetChanged();
                    }

                }).setNegativeButton("取消", null).show();
    }

    @Override
    public void onClick(View v) {
        // 获取输入框内容
        employeeName = et_name.getText().toString().trim();
        // 判断是否为空
        if (TextUtils.isEmpty(employeeName)) {
            showToast("请输入用户名");
            return;
        }
        // 产生随机ID
        employeeCardID = "" + UUID.randomUUID().toString();
        // 给照片一个存储路径
        File tempFile = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                System.currentTimeMillis() + ".jpg");
        employeePhoto = tempFile.getPath();

        Intent intent;
        switch (v.getId()) {
        case R.id.buttonEmpolyeePhoto:
            // 创建隐式意图调用系统相机
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri uri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, EMPLOYEE_PHOTO_REQUEST_CODE);
            break;
        case R.id.buttonEmpolyeePic:
            // 创建意图启动图库
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "选择一张图片"),
                    EMPLOYEE_ALBUM_REQUEST_CODE);
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EMPLOYEE_PHOTO_REQUEST_CODE
                && resultCode == RESULT_OK) {
            // 创建新的雇员信息，存入DoorSystem中
            employeeNum = "" + doorSystem.getNextUserNumber();
            Employee employee = new Employee(employeeNum, employeeName,
                    employeeCardID, employeePhoto, false);
            userlist.add(employee);
            et_name.setText("");
            showToast("添加雇员成功");
            employeeAdapter.notifyDataSetChanged();

        } else if (requestCode == EMPLOYEE_ALBUM_REQUEST_CODE
                && resultCode == RESULT_OK) {
            Uri originalUri = data.getData();// 得到图片的URI

            String[] imgs = { MediaStore.Images.Media.DATA };// 将图片URI转换成存储路径
            Cursor cursor = this.managedQuery(originalUri, imgs, null, null,
                    null);
            int index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            employeePhoto = cursor.getString(index);
            employeeNum = "" + doorSystem.getNextUserNumber();

            Employee employee = new Employee(employeeNum, employeeName,
                    employeeCardID, employeePhoto, false);
            userlist.add(employee);
            et_name.setText("");
            showToast("添加雇员成功");
            employeeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        // 重绘界面
        super.onResume();
        employeeAdapter.notifyDataSetChanged();
    }
}
