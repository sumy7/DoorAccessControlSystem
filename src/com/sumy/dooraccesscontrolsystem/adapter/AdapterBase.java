package com.sumy.dooraccesscontrolsystem.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.widget.BaseAdapter;

public abstract class AdapterBase<E> extends BaseAdapter {

	protected Context mContext;
	protected int mResourceID;
	protected ArrayList<E> mData;

	public AdapterBase(Context context, int resourceID, ArrayList<E> data) {
		this.mContext = context;
		this.mResourceID = resourceID;
		this.mData = data;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
