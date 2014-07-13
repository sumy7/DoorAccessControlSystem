package com.sumy.dooraccesscontrolsystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.sumy.dooraccesscontrolsystem.business.DoorSystem;
import com.sumy.dooraccesscontrolsystem.dao.CheckInDao;

/**
 * 所有 Activity 的基类，封装最常用的 Activity 的操作
 * 
 * @author sumy
 * 
 */
public abstract class BaseActivity extends Activity {

    private static String KEY_ISFIRST = "isfirst";

    private SharedPreferences sharedPreferences;
    private Editor editor;

    protected DoorSystem doorSystem;
    protected CheckInDao checkInDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        doorSystem = DoorSystem.getInstance();
        checkInDao = new CheckInDao(this);

        setContentView(getLayoutResID());
        // 取得偏好设置管理器
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // 调用子类初始化视图
        initView();

        // 取得编辑器
        editor = sharedPreferences.edit();
        // 使用编辑器存储信息
        editor.putBoolean(KEY_ISFIRST, false);
        // 提交修改信息
        editor.commit();
    }

    /**
     * 需要显示的布局资源
     * 
     * @return 布局资源 ID
     */
    protected abstract int getLayoutResID();

    /**
     * 执行初始化 View 的各项操作，会被基类自动调用
     */
    protected abstract void initView();

    /**
     * 启动一个 Activity
     * 
     * @param cls
     *            需要启动的 Activity 的类名
     */
    protected void startActivity(Class cls) {
        startActivity(new Intent(this, cls));
    }

    /**
     * 以回调结果的方式启动一个 Activity
     * 
     * @param cls
     *            需要启动的 Activity 的类名
     * @param requestCode
     *            启动时候的请求码
     */
    protected void startActivityForResult(Class cls, int requestCode) {
        startActivityForResult(new Intent(this, cls), requestCode);
    }

    /**
     * 显示一个简单的 Toast
     * 
     * @param text
     *            需要显示的内容
     */
    protected void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    /**
     * 检测是否是第一次运行
     * 
     * @return 是否是第一次运行
     */
    protected boolean isFirst() {
        return sharedPreferences.getBoolean(KEY_ISFIRST, true);
    }
}