package com.itcode.fileManager.domain;

/**
 * 文件夹实体
 * @author sunalong
 *
 */
public class Folder {
	/**
	 * 文件夹名称
	 */
	private String name;
	/**
	 * 里面包含的文件个数
	 */
	private int fileNumber;
	
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

	@Override
	public String toString() {
		return "Folder [name=" + name + ", fileNumber=" + fileNumber + ", path=" + path + "]";
	}
}