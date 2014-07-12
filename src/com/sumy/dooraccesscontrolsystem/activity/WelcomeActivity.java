package com.sumy.dooraccesscontrolsystem.activity;

import android.os.Handler;

import com.sumy.dooraccesscontrolsystem.R;

/**
 * 欢迎界面
 * 
 * @author sumy
 * 
 */
public class WelcomeActivity extends BaseActivity {

    private Handler handler;

    private Class toGotoActivity;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        // 判断是否第一次启动
        if (isFirst()) {
            // 第一次启动，转到引导界面
            toGotoActivity = ActivitySplash.class;
        } else {
            // 不是第一次启动，转到主界面
            toGotoActivity = MainMenuActivity.class;
        }

        handler = new Handler();

        // 创建handler，3秒后跳过欢迎界面
        handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivity(toGotoActivity);
                finish();
            }
        }, 2000);
    }

}
