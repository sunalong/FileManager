package com.itcode.fileManager.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
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
import com.itcode.fileManager.ViewCodeActivity;
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
		// Log.i(TAG, "onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		// Log.i(TAG, "onAttach");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Log.i(TAG, "onCreate");
		// initDataList("/");
		super.onCreate(savedInstanceState);
	}

	/**
	 * 初始化数据
	 */
	private void initDataList(String path) {
		// 让当前父路径与此路径的父路径相同
		files = FileUtils.getFiles(path);
		if (files == null) {// 当前路径是需要root的，不能遍历其下的文件，所以会返回null
			Toast.makeText(getActivity(), FileUtils.getName(path) + "需要root权限!", 0).show();
			return;
		}
		Log.i(TAG, "当前路径：" + path);
		SpannableStringBuilder ssb = addListenerToPath(path);
		tvCurrentFolder.setText(ssb);
		tvCurrentFolder.setMovementMethod(LinkMovementMethod.getInstance());
		if (path.equals(RootPath)) {
			currentFatherPath = null;
		} else if (0 == path.lastIndexOf("/")) {// 类似: /mnt,/cache等的父路径为：/
			currentFatherPath = path.substring(0, path.lastIndexOf("/") + 1);
		} else {
			currentFatherPath = path.substring(0, path.lastIndexOf("/"));// 类似：
																			// /mnt/sdcard的父路径为：/mnt
		}
		Log.i(TAG, "当前路径的父路径：" + currentFatherPath);

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
				folder.setFileNumber(countFiles(file, false));
				folder.setFolderNumber(countFiles(file, true));
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
	 * 为路径各片段添加点击事件
	 * 
	 * @param path
	 * @return
	 */
	private SpannableStringBuilder addListenerToPath(String path) {
		final String[] splitStr = path.split("/");
		String strRoot = "/ ";
		SpannableStringBuilder ssb = new SpannableStringBuilder(strRoot);
		int len = 0;
		for (int i = 0; i < splitStr.length; i++) {
			final String segment = splitStr[i];// 每次segment的hashCode的值不一样
			ssb.append(segment);
			if (i == splitStr.length - 1)
				return ssb;
			final int index = i;
			ssb.setSpan(new URLSpan(segment) {
				@Override
				public void onClick(View widget) {
					StringBuilder sb = new StringBuilder();
					for (int j = 0; j < index; j++) {
						if (!TextUtils.isEmpty(splitStr[j].trim()))
							sb.append("/").append(splitStr[j]);
					}
					sb.append("/").append(segment);
					initDataList(sb.toString());
					adapter.setItems(list);
					adapter.notifyDataSetChanged();
				}
			}, ssb.length() - segment.length(), ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			if (ssb.length() != strRoot.length())
				ssb.append(" / ");
		}
		return ssb;
	}

	/**
	 * 计算当前文件夹下的文件个数
	 * 
	 * @param file
	 * @param isFolder
	 *            true 表示计算文件夹个数;false表示计算文件个数
	 * @return 如果计算文件夹个数，则返回的是file中文件夹个数;如果计算文件个数，则返回的是file中的文件个数
	 */
	private int countFiles(File file, boolean isFolder) {
		int fileNumber = 0;
		// Log.i(TAG, "countFiles:" + file.getName() + " " + file.getPath());

		File[] listFiles = file.listFiles();
		if (listFiles == null)// 需要root
			return -1;
		if (isFolder) {// 如果是文件夹
			for (int i = 0; i < listFiles.length; i++)
				if (listFiles[i].isDirectory())
					fileNumber++;
		} else {// 如果是文件
			for (int i = 0; i < listFiles.length; i++)
				if (listFiles[i].isFile())
					fileNumber++;
		}
		listFiles = null;
		return fileNumber;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Log.i(TAG, "onCreateView");
		view = inflater.inflate(R.layout.fragment_file_list, container, false);
		return view;
	}

	@Override
	public void onDestroyView() {
		// Log.i(TAG, "onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		// Log.i(TAG, "onDetach");
		super.onDetach();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// Log.i(TAG, "onViewCreated");
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
		// Log.i(TAG, position + " onItemClick:" + folder);
		if (folder.isFolder()) {// 如果是文件夹
			files = FileUtils.getFiles(folder.getPath());
			// adapter.clear();
			initDataList(folder.getPath());
			// for (int i = 0; i < list.size(); i++) {
			// Log.i(TAG, i + " onItem:" + list.get(i));
			// }
			adapter.setItems(list);
			adapter.notifyDataSetChanged();
		} else {// 是文件：高亮打开
			Toast.makeText(getActivity(), "是文件,高亮打开", 0).show();
			Intent intent = new Intent(getActivity(),ViewCodeActivity.class);
			intent.putExtra("path", folder.getPath());
			startActivity(intent);
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
			// for (int i = 0; i < list.size(); i++) {
			// Log.i(TAG, i + " 返回键Fragment:" + list.get(i));
			// }
			adapter.setItems(list);
			adapter.notifyDataSetChanged();
			return true;
		}
	}
}
