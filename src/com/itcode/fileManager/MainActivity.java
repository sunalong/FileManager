package com.itcode.fileManager;

import java.io.File;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.RelativeLayout;

import com.itcode.fileManager.fragment.FolderTreeFragment;
import com.itcode.fileManager.utils.FileUtils;

public class MainActivity extends FragmentActivity {

	private static final String TAG = "MainActivity";
	private ViewPager viewPager;
	private RelativeLayout rlContainer;
	private FolderTreeFragment fragment;
	/**
	 * fragment是否可以继续返回
	 */
	private boolean canContinueBack=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rlContainer = (RelativeLayout) findViewById(R.id.rlContainer);
		fragment = new FolderTreeFragment();
		FragmentManager supportFragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.rlContainer, fragment);
		fragmentTransaction.commit();
		// viewPager = (ViewPager) findViewById(R.id.vpContainer);
		// viewPager.setAdapter(new PagerAdapter());
		// //Activity.viewPager--->PagerAdapter-->Fragment?
		// viewPager.add(fragment);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		/**
		 * ①：如果Fragment为空，则调用默认的返回键操作
		 * ②：如果fragment不能继续返回了，则同样调用市府的返回键操作
		 */
		if (fragment == null||!fragment.onBackPressedFragment()) {
//			canContinueBack = fragment.onBackPressedFragment();
			Log.i(TAG,"canContinueBack:"+canContinueBack);
			super.onBackPressed();
		}
	}
}
