package com.sumy.dooraccesscontrolsystem.adapter;

import java.util.ArrayList;

import com.sumy.dooraccesscontrolsystem.entity.GridViewItem;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GridViewAdapter extends AdapterBase<GridViewItem> {

    private Typeface tf;

    public GridViewAdapter(Context context, int resourceID,
            ArrayList<GridViewItem> data) {
        super(context, resourceID, data);

        tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/wryh.ttf");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(mContext);
        textView.setText(mData.get(position).getName());
        Drawable top = mContext.getResources().getDrawable(
                mData.get(position).getPic());
        top.setBounds(0, 0, top.getIntrinsicWidth(), top.getIntrinsicHeight());
        textView.setCompoundDrawables(null, top, null, null);
        textView.setTextSize(20);
        // 设置字体
        textView.setTypeface(tf);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

}
