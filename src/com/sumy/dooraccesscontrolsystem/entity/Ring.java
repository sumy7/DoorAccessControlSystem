package com.sumy.dooraccesscontrolsystem.entity;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * 实体类，门铃
 * 
 * @author sumy
 * 
 */
public class Ring {
    private boolean isRing = false;

    /**
     * 激活门铃
     */
    public void toRing(Context context, int resid) {
        MediaPlayer mediaplayer = MediaPlayer.create(context, resid);
        mediaplayer.start();
    }

    public boolean isRing() {
        return this.isRing;
    }
}
