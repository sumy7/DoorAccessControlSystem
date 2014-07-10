package com.sumy.dooraccesscontrolsystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * 所有 Activity 的基类，封装最常用的 Activity 的操作
 * 
 * @author sumy
 * 
 */
public abstract class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutResID());
		initView();
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
	 * 显示一个简单的 Toast
	 * 
	 * @param text
	 *            需要显示的内容
	 */
	protected void showToask(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}
}