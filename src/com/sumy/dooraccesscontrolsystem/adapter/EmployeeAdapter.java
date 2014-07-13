package com.sumy.dooraccesscontrolsystem.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sumy.dooraccesscontrolsystem.R;
import com.sumy.dooraccesscontrolsystem.entity.Admin;
import com.sumy.dooraccesscontrolsystem.entity.Employee;
import com.sumy.dooraccesscontrolsystem.entity.Manager;
import com.sumy.dooraccesscontrolsystem.entity.User;
import com.sumy.dooraccesscontrolsystem.utils.ImageTools;

/**
 * 适配录入雇员的雇员列表
 * 
 * @author sumy
 * 
 */
public class EmployeeAdapter extends AdapterBase<User> {

    public EmployeeAdapter(Context context, int resourceID, ArrayList<User> data) {
        super(context, resourceID, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.activity_input_empolyee_listitem, null);
            holder = new ViewHolder();
            holder.userImageView = (ImageView) convertView
                    .findViewById(R.id.imageViewListItemPhoto);
            holder.userNameTextView = (TextView) convertView
                    .findViewById(R.id.textViewListItemName);
            holder.userTitleTextView = (TextView) convertView
                    .findViewById(R.id.textViewListItemID);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        User user = mData.get(position);
        holder.userNameTextView.setText(user.getName());
        if (user instanceof Admin) {
            holder.userTitleTextView.setText("管理员 ID:" + user.getUserid());
            holder.userImageView.setImageResource(R.drawable.admin);
            holder.userNameTextView.setTextColor(Color.BLACK);
        } else if (user instanceof Employee) {
            Employee tmp = (Employee) user;
            holder.userNameTextView.setText(tmp.getName() + " "
                    + tmp.getCardid());
            if (!tmp.isHasCard()) {
                holder.userNameTextView.setTextColor(Color.RED);
            } else {
                holder.userNameTextView.setTextColor(Color.BLACK);
            }
            holder.userTitleTextView.setText("雇  员 ID:" + user.getUserid());
            Bitmap small = ImageTools.scaleImage(tmp.getPhoto(), 256, 256);
            if (small != null) {
                holder.userImageView.setImageBitmap(small);
            } else {
                holder.userImageView.setImageResource(R.drawable.admin);
            }
        } else if (user instanceof Manager) {
            Manager tmp = (Manager) user;
            holder.userTitleTextView.setText("经  理 ID:" + tmp.getUserid());
            holder.userImageView.setImageResource(R.drawable.admin);
            holder.userNameTextView.setTextColor(Color.BLACK);
            holder.userNameTextView.setText(tmp.getName() + "手势："
                    + tmp.getFigure());
        }
        return convertView;
    }

    private class ViewHolder {
        ImageView userImageView;
        TextView userNameTextView;
        TextView userTitleTextView;
    }

}
