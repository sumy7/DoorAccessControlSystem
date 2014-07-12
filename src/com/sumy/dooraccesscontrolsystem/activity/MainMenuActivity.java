package com.sumy.dooraccesscontrolsystem.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
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
import com.sumy.dooraccesscontrolsystem.business.DoorSystem;
import com.sumy.dooraccesscontrolsystem.entity.GridViewItem;

/**
 * 主菜单界面
 * 
 * @author sumy
 * 
 */
public class MainMenuActivity extends BaseActivity {

    private static int ADMIN_REQUEST_CODE = 233;
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
                    break;
                case 2:
                    // 访客
                    showToast("访客门铃");
                    DoorSystem.getInstance().getRing()
                            .toRing(MainMenuActivity.this, R.raw.ring);
                    break;
                case 3:
                    // 经理
                    showToast("经理指纹");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADMIN_REQUEST_CODE && resultCode == RESULT_OK) {
            doorSystem.getDoor().toOpen(imageView);
        }
    }
}
