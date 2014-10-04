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
import android.widget.ListView;

import com.itcode.fileManager.R;
import com.itcode.fileManager.adapter.FolderTreeAdapter;
import com.itcode.fileManager.domain.Folder;
import com.itcode.fileManager.utils.FileUtils;

/**
 * 显示文件夹及文件的Fragment
 * @author sunalong
 *
 */
public class FolderTreeFragment extends Fragment {

	private static final String TAG = "FileListFragment";
	private ListView listView;
	private View view;
	private List<Folder> list;
	/**
	 * 指定路径(根路径/)下的文件列表
	 */
	private File[] files;
	private Folder folder;
	private FolderTreeAdapter adapter;

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
		initDataList();
		super.onCreate(savedInstanceState);
	}

	/**
	 * 初始化数据
	 */
	private void initDataList() {
		files = FileUtils.getFiles("/");
		if (list != null)
			list.clear();
		else
			list = new ArrayList<Folder>(FileUtils.getFiles("/").length);
		
		List<Folder> folderList = new ArrayList<Folder>();
		List<Folder> fileList = new ArrayList<Folder>();
		
		for(File file:files){
			folder = new Folder();
			if(file.isDirectory() && file.listFiles() !=null){
				folder.setName(file.getName());
				folder.setFileNumber(file.listFiles().length);
				folder.setPath(file.getPath());
				folder.setFolder(true);
				folderList.add(folder);
			}else if(file.isFile()){
				folder.setName(file.getName());
				folder.setFileNumber(0);
				folder.setPath(file.getPath());
				folder.setFolder(false);
				fileList.add(folder);
			}
		}
		list.addAll(folderList);//先将文件夹加入到list中，以便让文件夹在最前面
		list.addAll(fileList);//将文件夹下的文件加入到list中
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
		listView = (ListView) view.findViewById(R.id.lvContainer);
		adapter = new FolderTreeAdapter(getActivity(), list);
		listView.setAdapter(adapter);
		super.onViewCreated(view, savedInstanceState);
	}

}
