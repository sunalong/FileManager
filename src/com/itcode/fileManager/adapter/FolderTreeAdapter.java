package com.itcode.fileManager.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.github.kevinsawicki.wishlist.MultiTypeAdapter;
import com.itcode.fileManager.R.id;
import com.itcode.fileManager.domain.Folder;

/**
 * 显示文件夹及文件的Fragment的Adapter
 * 
 * @author sunalong
 * 
 */
public class FolderTreeAdapter extends MultiTypeAdapter {

	/**
	 * 文件夹类型
	 */
	private static final int TYPE_FOLDER = 0;
	/**
	 * 文件类型
	 */
	private static final int TYPE_FILE = 1;
	private static final String TAG = "FolderTreeAdapter";
	private Context context;
	private List<Folder> list;

	public FolderTreeAdapter(Context context, List<Folder> list) {
		super(context);
		this.context = context;
		this.list = list;
		setItems(list);
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	/**
	 * 根据不同的type获取相对应的布局id
	 */
	@Override
	protected int getChildLayoutId(int type) {
		switch (type) {
		case TYPE_FOLDER:
			return com.itcode.fileManager.R.layout.folder_item;
		case TYPE_FILE:
			return com.itcode.fileManager.R.layout.file_item;
		default:
			return -1;
		}
	}

	/**
	 * 根据不同的type获取相对应的布局中的id
	 */
	@Override
	protected int[] getChildViewIds(int type) {
		switch (type) {
		case TYPE_FOLDER:
			return new int[] { id.tvFolderName, id.tvFolderNumber, id.tvFileNumber };
		case TYPE_FILE:
			return new int[] { id.tvFile, id.tvSize };
		default:
			return null;
		}
	}

	/**
	 * Set root folder to display
	 * 供外界调用，将item中的值与类型设置好，如：adapter.getWrappedAdapter().setItems(folder);
	 * 在此方法中调用方法MultiTypeAdapter中的addItems，而此方法中有notifyDataSetChanged
	 * 
	 * @param root
	 */
	public void setItems(List<Folder> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).isFolder())
				addItem(TYPE_FOLDER, list.get(i));
			else
				addItem(TYPE_FILE, list.get(i));
		}
	}

	/**
	 * 在getView中，当convertView为空时调用
	 */
	@Override
	protected View initialize(int type, View view) {
		view = super.initialize(type, view);
		return view;
	}

	/**
	 * 在getView中被调用，以便更新view的数据显示<br>
	 * ①：设置padding<br>
	 * ②：设置文件、文件夹item的名称及数据的显示
	 * 
	 */
	@Override
	protected void update(final int position, final Object item, final int type) {
		Log.i(TAG, "update.position:" + position);
		Log.i(TAG, "update.list.size:" + list.size());
		Folder folder;
		folder = list.get(position);
		switch (type) {
		case TYPE_FOLDER:
			Log.i(TAG, "此为文件夹");
			// {id.tvFolderName,id.tvFolderNumber,id.tvFileNumber};
			setText(0, position + " " + folder.getName());
			setText(1, "" + folder.getFileNumber());
			setText(2, "" + folder.getFileNumber());
			break;
		case TYPE_FILE:
			Log.i(TAG, "此为文件");
			setText(0, position + " " + folder.getName());
			setText(1, "13kb");
			break;
		}
	}
}
