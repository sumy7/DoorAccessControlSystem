package com.sumy.dooraccesscontrolsystem.activity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.nfc.NfcAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.sumy.dooraccesscontrolsystem.R;
import com.sumy.dooraccesscontrolsystem.adapter.GridViewAdapter;
import com.sumy.dooraccesscontrolsystem.business.Environment;
import com.sumy.dooraccesscontrolsystem.entity.GridViewItem;

/**
 * 主菜单界面
 * 
 * @author sumy
 * 
 */
public class MainMenuActivity extends BaseActivity {

    private static int ADMIN_REQUEST_CODE = 233;
    private static int EMPLOYEE_REQUEST_CODE = 233333;
    private static int MANAGER_REQUEST_CODE = 2333;

    private GridView gridView;
    private ImageView imageView;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_mainmenu;
    }

    @Override
    protected void initView() {
        gridView = (GridView) findViewById(R.id.mainmenu_gridView);
        imageView = (ImageView) findViewById(R.id.mainmenu_imageview);
        // 停止动画
        ((AnimationDrawable) imageView.getDrawable()).stop();

        ArrayList<GridViewItem> list = new ArrayList<GridViewItem>();
        list.add(new GridViewItem("管理员进入", R.drawable.admin));
        list.add(new GridViewItem("雇员刷卡", R.drawable.card));
        list.add(new GridViewItem("访客门铃", R.drawable.bell));
        list.add(new GridViewItem("经理指纹", R.drawable.finger));
        GridViewAdapter adapter = new GridViewAdapter(this, 0, list);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new OnItemClickListener() {

            /*
             * position 视图在适配器中的位置
             * 
             * id 被点击项目的行id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                switch (position) {
                case 0:
                    // 管理员
                    showToast("管理员访问");
                    startActivityForResult(AdminActivity.class,
                            ADMIN_REQUEST_CODE);
                    break;
                case 1:
                    // 雇员
                    showToast("雇员刷卡");
                    NfcAdapter nfcadapter = NfcAdapter
                            .getDefaultAdapter(MainMenuActivity.this);
                    if (nfcadapter == null
                            || (nfcadapter != null && !nfcadapter.isEnabled())) {
                        showToast("设备不支持NFC或NFC功能未启用，使用手动签到");
                        startActivityForResult(EmployeeActivity.class,
                                EMPLOYEE_REQUEST_CODE);
                    } else {
                        startActivityForResult(EmployeeNFCEnterActivity.class,
                                EMPLOYEE_REQUEST_CODE);
                    }
                    break;
                case 2:
                    // 访客
                    showToast("访客门铃");
                    doorSystem.getRing().toRing(MainMenuActivity.this,
                            R.raw.ring);
                    break;
                case 3:
                    // 经理
                    showToast("经理手势");
                    startActivityForResult(ManagerCheckInActivity.class,
                            MANAGER_REQUEST_CODE);
                    break;
                }
            }
        });
        // 为 GridView 的每一项设置动画
        Animation animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f);
        animation.setDuration(1500);
        LayoutAnimationController controller = new LayoutAnimationController(
                animation);
        gridView.setLayoutAnimation(controller);
        animation.start();
    }

    private static Boolean isGoingExit = false; // 当前是否正在退出

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 双击 back键 退出
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Timer tExit = null;
            if (isGoingExit == false) {
                isGoingExit = true; // 准备退出
                showToast("再按一次退出");
                tExit = new Timer();
                tExit.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isGoingExit = false; // 取消退出
                    }
                }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
            } else {
                finish();
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADMIN_REQUEST_CODE && resultCode == RESULT_OK) {
            doorSystem.getDoor().toOpen(imageView);
        }
        if (requestCode == EMPLOYEE_REQUEST_CODE && resultCode == RESULT_OK) {
            doorSystem.getDoor().toOpen(imageView);
        }
        if (requestCode == MANAGER_REQUEST_CODE && resultCode == RESULT_OK) {
            doorSystem.getDoor().toOpen(imageView);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出管理员登录
        Environment.adminLogout();
    }
}
