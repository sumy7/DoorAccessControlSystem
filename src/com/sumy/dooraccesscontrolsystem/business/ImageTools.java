package com.sumy.dooraccesscontrolsystem.business;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageTools {
    public static Bitmap scaleImage(String photoPath, int width, int height) {
        // 解析图片大小
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, opts);

        // 计算图片缩放倍率
        int mWidth = opts.outWidth;
        int mHeight = opts.outHeight;
        int simple = 1;
        while (mWidth > width * simple || mHeight > height * simple) {
            simple = simple * 2;
        }

        // 根据缩放比例读取图片
        opts.inJustDecodeBounds = false;
        opts.inSampleSize = simple;
        Bitmap mbitmap = BitmapFactory.decodeFile(photoPath, opts);

        return mbitmap;
    }
}
