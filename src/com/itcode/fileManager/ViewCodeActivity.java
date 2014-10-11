package com.itcode.fileManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;

import com.itcode.fileManager.Intents.Builder;
import com.itcode.fileManager.utils.SourceEditor;

/**
 * 展示代码高亮
 * 
 * @author sunalong
 * 
 */
public class ViewCodeActivity extends Activity{

	private static final String TAG = "ViewCodeActivity";
	private static Builder builder;
	private ProgressBar pbDataLoading;
	private WebView wvCodeView;
	private SourceEditor sourceEditor;
	private String content;
	private Button btSelect;
	private String path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_code);
		Intent intent = getIntent();
		path = intent.getStringExtra("path");
		initView();
		getContentFromFile(path);
		setSource();
	}

	private void getContentFromFile(String path) {
		try {
			Log.i(TAG, "path:" + path);
			path = Uri.decode(path);// 将路径中文的乱码形式转化为可阅读的中文字符
			Log.i(TAG, "decode path:" + path);
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			content = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSource() {
		// btSelect.setVisibility(View.GONE);
		sourceEditor = new SourceEditor(wvCodeView);// 设置代码编辑器//设置内容//设置文件名后缀
		// TODO:打开文件读取内容
		// TODO:注册此类打开文件的选择：如同打开视频时选择播放器一样
		sourceEditor.setSource(path, content, false);
	}

	private void initView() {
		wvCodeView = (WebView) findViewById(R.id.wvCode);
	}

}
