package com.itcode.fileManager.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.itcode.fileManager.R;

/**
 * 显示文件夹及文件的Fragment的Adapter
 * @author sunalong
 *
 */
public class FolderTreeAdapter extends BaseAdapter {

	private Context context;
	private List list;
	public FolderTreeAdapter(Context context,List list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = View.inflate(context,R.layout.item_file_list_fragment, null);
		TextView tvFileName = (TextView) view.findViewById(R.id.tvfileName);
		tvFileName.setText(list.get(position).toString());
		return view;
	}

}
