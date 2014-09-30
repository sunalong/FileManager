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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rlContainer = (RelativeLayout) findViewById(R.id.rlContainer);
		FolderTreeFragment fragment = new FolderTreeFragment();
		FragmentManager supportFragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.rlContainer, fragment);
		fragmentTransaction.commit();
		// viewPager = (ViewPager) findViewById(R.id.vpContainer);
		// viewPager.setAdapter(new PagerAdapter());
		// //Activity.viewPager--->PagerAdapter-->Fragment?
		// viewPager.add(fragment);
	}

}
