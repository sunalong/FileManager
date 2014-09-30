package com.itcode.fileManager.utils;

import java.io.File;
import java.util.List;

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
	
}
