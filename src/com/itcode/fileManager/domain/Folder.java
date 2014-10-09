package com.itcode.fileManager.domain;


/**
 * 文件夹实体
 * @author sunalong
 *
 */
public class Folder{
	
	/**
	 * 是否是文件夹
	 */
	private boolean isFolder;
	

	public boolean isFolder() {
		return isFolder;
	}

	public void setFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}

	/**
	 * 文件夹名称
	 */
	private String name;
	/**
	 * 里面包含的文件个数
	 */
	private int fileNumber;
	/**
	 * 里面包含的文件夹个数
	 */
	private int folderNumber;
	
	/**
	 * 此文件夹的路径
	 */
	private String path;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(int fileNumber) {
		this.fileNumber = fileNumber;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	
	public int getFolderNumber() {
		return folderNumber;
	}

	public void setFolderNumber(int folderNumber) {
		this.folderNumber = folderNumber;
	}

	@Override
	public String toString() {
		return "Folder [isFolder=" + isFolder + ", name=" + name + ", fileNumber=" + fileNumber + ", folderNumber=" + folderNumber + ", path=" + path + "]";
	}

}
