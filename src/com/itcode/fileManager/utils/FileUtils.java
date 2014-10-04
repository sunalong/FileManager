package com.itcode.fileManager.utils;

import java.io.File;
import java.util.List;

import android.text.TextUtils;

public class FileUtils {

	public static void getFolderDirectory(){
		
	}
	
	/**
	 * 获取给定路径下的所有文件
	 * @param path
	 * @return
	 */
	public static File[] getFiles(String path){
		return new File(path).listFiles();
	}
	 /**
     * Get file name for path
     *
     * @param path
     * @return last segment of path
     */
    public static String getName(final String path) {
        if (TextUtils.isEmpty(path))
            return path;

        int lastSlash = path.lastIndexOf('/');
        if (lastSlash != -1 && lastSlash + 1 < path.length())
            return path.substring(lastSlash + 1);
        else
            return path;
    }
}
