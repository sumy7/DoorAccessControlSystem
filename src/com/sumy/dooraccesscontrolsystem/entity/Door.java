package com.sumy.dooraccesscontrolsystem.entity;

import com.sumy.dooraccesscontrolsystem.R.anim;

import android.graphics.drawable.AnimationDrawable;
import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * 实体类，门
 * 
 * @author sumy
 * 
 */
public class Door {
    private boolean isOpen = false;

    /**
     * 开门操作
     * 
     * @param imageView
     *            需要打开的门
     */
    public void toOpen(ImageView imageView) {
        if (!isOpen()) {
            isOpen = true;
        }
        AnimationDrawable animationdrawable = (AnimationDrawable) imageView
                .getDrawable();
        animationdrawable.stop();
        animationdrawable.start();
    }

    /**
     * 关门操作
     */
    public void toClose() {
        if (isOpen()) {
            isOpen = false;
        }
    }

    /**
     * 返回门的状态
     * 
     * @return <b>true</b> 门已打开 <b>false</b> 门关闭
     */
    public boolean isOpen() {
        return this.isOpen;
    }
}
