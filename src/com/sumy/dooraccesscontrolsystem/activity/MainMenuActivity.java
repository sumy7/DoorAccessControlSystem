package com.sumy.dooraccesscontrolsystem.activity;

import java.util.ArrayList;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.sumy.dooraccesscontrolsystem.R;
import com.sumy.dooraccesscontrolsystem.adapter.GridViewAdapter;
import com.sumy.dooraccesscontrolsystem.entity.GridViewItem;

public class MainMenuActivity extends BaseActivity {

    private GridView gridView;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_mainmenu;
    }

    @Override
    protected void initView() {
        gridView = (GridView) findViewById(R.id.mainmenu_gridView);
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
                    break;
                case 1:
                    // 雇员
                    showToast("雇员刷卡");
                    break;
                case 2:
                    // 访客
                    showToast("访客门铃");
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

}
