package com.itcode.fileManager.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itcode.fileManager.R;
import com.itcode.fileManager.adapter.FolderTreeAdapter;
import com.itcode.fileManager.domain.Folder;
import com.itcode.fileManager.utils.FileUtils;

/**
 * 显示文件夹及文件的Fragment
 * 
 * @author sunalong
 * 
 */
public class FolderTreeFragment extends Fragment implements OnItemClickListener {

	private static final String TAG = "FileListFragment";
	private ListView listView;
	private View view;
	/**
	 * 当前目录下的文件夹及文件的列表集合
	 */
	private List<Folder> list;
	/**
	 * 指定路径(根路径/)下的文件列表
	 */
	private File[] files;
	private Folder folder;
	private FolderTreeAdapter adapter;
	/**
	 * 根路径
	 */
	private String RootPath = "/";
	/**
	 * 当前父路径
	 */
	private String currentFatherPath = RootPath;
	/**
	 * 当前目录下的文件夹列表
	 */
	private List<Folder> folderList;
	/**
	 * 当前目录下手文件列表
	 */
	private List<Folder> fileList;
	private TextView tvCurrentFolder;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i(TAG, "onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		Log.i(TAG, "onAttach");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		// initDataList("/");
		super.onCreate(savedInstanceState);
	}

	/**
	 * 初始化数据
	 */
	private void initDataList(String path) {
		// 让当前父路径与此路径的父路径相同
		Log.i(TAG, "当前路径：" + path);
		tvCurrentFolder.setText(path);
		if (path.equals(RootPath)) {
			currentFatherPath = null;
		} else if (0 == path.lastIndexOf("/")) {// 类似: /mnt,/cache等的父路径为：/
			currentFatherPath = path.substring(0, path.lastIndexOf("/") + 1);
		} else {
			currentFatherPath = path.substring(0, path.lastIndexOf("/"));// 类似：
																			// /mnt/sdcard的父路径为：/mnt
		}
		Log.i(TAG, "当前路径的父路径：" + currentFatherPath);
		files = FileUtils.getFiles(path);
		if (list != null) {
			list.clear();
			folderList.clear();
			fileList.clear();
		} else {
			list = new ArrayList<Folder>(FileUtils.getFiles(path).length);
			folderList = new ArrayList<Folder>(FileUtils.getFiles(path).length);
			fileList = new ArrayList<Folder>(FileUtils.getFiles(path).length);
		}
		for (File file : files) {
			folder = new Folder();
			if (file.isDirectory()) {
				// TODO:计算此文件夹下的文件、文件夹个数
				int len = countFiles(file);
				folder.setFileNumber(len);
				if (len == -1)// 需要root
					folder.setFolderNumber(-1);
				else
					folder.setFolderNumber(file.listFiles().length - len);
				folder.setName(file.getName());
				folder.setPath(file.getPath());
				folder.setFolder(true);
				folderList.add(folder);
			} else if (file.isFile()) {
				folder.setName(file.getName());
				folder.setFileNumber(0);
				folder.setFileSize(file.length());
				folder.setPath(file.getPath());
				folder.setFolder(false);
				fileList.add(folder);
			}
		}
		list.addAll(folderList);// 先将文件夹加入到list中，以便让文件夹在最前面
		list.addAll(fileList);// 将文件夹下的文件加入到list中
	}

	/**
	 * 计算当前文件夹下的文件个数
	 * 
	 * @param file
	 * @return
	 */
	private int countFiles(File file) {
		int fileNumber = 0;
		Log.i(TAG, "countFiles:" + file.getName() + " " + file.getPath());

		File[] listFiles = file.listFiles();
		if (listFiles == null)// 需要root
			return -1;
		for (int i = 0; i < listFiles.length; i++) {
			if (listFiles[i].isFile())
				fileNumber++;
		}
		listFiles = null;
		return fileNumber;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.i(TAG, "onCreateView");
		view = inflater.inflate(R.layout.fragment_file_list, container, false);
		return view;
	}

	@Override
	public void onDestroyView() {
		Log.i(TAG, "onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		Log.i(TAG, "onDetach");
		super.onDetach();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		Log.i(TAG, "onViewCreated");
		tvCurrentFolder = (TextView) view.findViewById(R.id.tvCurrentFolder);
		// tvFatherPath.setText(currentFatherPath);
		listView = (ListView) view.findViewById(R.id.lvContainer);
		initDataList("/");
		adapter = new FolderTreeAdapter(getActivity(), list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		// getActivity().onKeyDown(KeyEvent.KEYCODE_BACK, null);

		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Folder folder = (Folder) adapter.getItem(position);
		Log.i(TAG, position + " onItemClick:" + folder);
		if (folder.isFolder()) {// 如果是文件夹
			files = FileUtils.getFiles(folder.getPath());
			// adapter.clear();
			initDataList(folder.getPath());
			for (int i = 0; i < list.size(); i++) {
				Log.i(TAG, i + " onItem:" + list.get(i));
			}
			adapter.setItems(list);
			adapter.notifyDataSetChanged();
		} else {// 是文件：高亮打开
			Toast.makeText(getActivity(), "是文件,高亮打开", 0).show();
		}
	}

	/**
	 * 定义在Fragment中的返回键操作方法，供外界调用
	 */
	public boolean onBackPressedFragment() {
		if (currentFatherPath == null) {
			return false;
		} else {
			initDataList(currentFatherPath);
			for (int i = 0; i < list.size(); i++) {
				Log.i(TAG, i + " 返回键Fragment:" + list.get(i));
			}
			adapter.setItems(list);
			adapter.notifyDataSetChanged();
			return true;
		}
	}
}
