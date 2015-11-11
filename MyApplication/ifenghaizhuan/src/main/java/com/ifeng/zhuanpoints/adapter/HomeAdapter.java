package com.ifeng.zhuanpoints.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ifeng.zhuanpoints.R;
import com.ifeng.zhuanpoints.beans.PointsItem;
import com.ifeng.zhuanpoints.utils.AsyImageLoader;

public class HomeAdapter extends BaseAdapter {
	
	private ArrayList<PointsItem> mList;
	private Context mContext;
	private AsyImageLoader imageLoader;
	public HomeAdapter(Context context,ArrayList<PointsItem> list,AsyImageLoader imageLoader)
	{
		mContext = context;
		mList = list;
		this.imageLoader = imageLoader;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		
		if(null == convertView)
		{
			convertView = LayoutInflater.from(mContext).inflate(R.layout.home_item, null, false);
			holder = new ViewHolder();
			holder.imageview = (ImageView)convertView.findViewById(R.id.item_icon);
			holder.name = (TextView)convertView.findViewById(R.id.item_name);
			holder.detail = (TextView)convertView.findViewById(R.id.item_detail);
			convertView.setTag(holder);
		}else
		{
			holder = (ViewHolder)convertView.getTag();
		}
		PointsItem item = mList.get(position);
		
		holder.name.setText(""+item.name);
		holder.detail.setText(""+item.detail);
		imageLoader.loadImage(holder.imageview, ""+item.icon, true);
		return convertView;
	}
	
	public static class ViewHolder
	{
		public ImageView imageview;
		public TextView name;
		public TextView detail;
	}
	

}
