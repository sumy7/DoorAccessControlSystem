package com.sumy.dooraccesscontrolsystem.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.sumy.dooraccesscontrolsystem.R;
import com.sumy.dooraccesscontrolsystem.business.DoorSystem;
import com.sumy.dooraccesscontrolsystem.business.Environment;
import com.sumy.dooraccesscontrolsystem.entity.Admin;
import com.sumy.dooraccesscontrolsystem.entity.User;
import com.sumy.dooraccesscontrolsystem.utils.XMLTools;
import com.sumy.dooraccesscontrolsystem.validate.Validate;

/**
 * 管理员界面，用于显示管理员的基本操作，验证管理员身份
 * 
 * @author sumy
 * 
 */
public class AdminActivity extends BaseActivity {

    private ListView listView;

    private String path = android.os.Environment.getExternalStorageDirectory()
            + "/dooraccessusersbackup.xml";

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_admin;
    }

    @Override
    protected void initView() {
        // 显示登录对话框验证权限
        showLoginDialog();

        listView = (ListView) findViewById(R.id.admin_listview);
        listView.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, this.getResources()
                        .getTextArray(R.array.admin_function)));
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                switch (position) {
                case 0:
                    // 录入新雇员
                    startActivity(InputEmployeeActivity.class);
                    break;
                case 1:
                    // 开门
                    setResult(RESULT_OK);
                    finish();
                    break;
                case 2:
                    // 备份文件
                    // 保存到XML文件中
                    Log.i("mytag", path);
                    boolean result = XMLTools.writeXML(path,
                            doorSystem.getUserlist());
                    if (result) {
                        showToast("备份成功");
                    } else {
                        showToast("备份失败");
                    }
                    break;
                case 3:
                    // 恢复信息
                    ArrayList<User> list = XMLTools.readXML(path);
                    if (list != null) {
                        doorSystem.setUserlist(list);
                        showToast("恢复成功");
                    } else {
                        showToast("恢复失败");
                    }
                    break;

                case 4:
                    // 经理手势录入
                    startActivity(InputManagerActivity.class);
                    break;
                case 5:
                    // 系统设置
                    showToast("功能暂未实现");
                    break;
                case 6:
                    // 管理员退出
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            AdminActivity.this);
                    builder.setTitle("确定退出？")
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            Environment.adminLogout();
                                            finish();
                                        }

                                    }).setNegativeButton("取消", null).show();
                    break;
                case 7:
                    // 查看考勤信息
                    startActivity(ShowCheckInActivity.class);
                    break;
                }
            }
        });
    }

    /**
     * 显示管理员登录对话框
     */
    private void showLoginDialog() {
        // 检测是否已经登录
        if (Environment.getLoginedAdmin() != null) {
            return;
        }

        // 设置并显示登录对话框
        final View view = LayoutInflater.from(this).inflate(
                R.layout.login_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.admin).setTitle(R.string.admin_login_dialog)
                .setView(view)
                .setPositiveButton(R.string.ok, new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // 获取登录框信息
                        final String username = ((EditText) view
                                .findViewById(R.id.login_edit_account))
                                .getText().toString();
                        final String password = ((EditText) view
                                .findViewById(R.id.login_edit_pwd)).getText()
                                .toString();

                        // 遍历用户进行登录验证
                        boolean checkresult = doorSystem.Check(new Validate() {

                            @Override
                            public boolean checkIn(List<User> users) {
                                for (User user : users) {
                                    if (user != null) {
                                        if (user instanceof Admin) {
                                            Admin thisuser = (Admin) user;
                                            if (thisuser.getName().equals(
                                                    username)
                                                    && thisuser.getPassword()
                                                            .equals(password))
                                                Environment
                                                        .adminLogin(thisuser);
                                            return true;
                                        }
                                    }
                                }
                                return false;
                            }
                        });

                        // 显示登录结果
                        if (checkresult) {
                            showToast("登录成功");
                        } else {
                            showToast("登录失败");
                            finish();
                        }
                    }
                }).setNegativeButton(R.string.cancel, new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 自动保存用户列表
        XMLTools.writeXML(DoorSystem.AUTOSAVE_PATH, doorSystem.getUserlist());
    }

}
