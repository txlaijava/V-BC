package com.shopping.framework.oss.bean;

public class OssConfig {

	private String uploadFilePath;
	private int smallWidth;
	private int smallHeight;
	private int middleWidth;
	private int middleHeight;
	private int bigWidth;
	private int bigHeight;
	private String imageSuffix;

	public OssConfig(String uploadFilePath,int smallWidth,int smallHeight,int middleWidth,int middleHeight, int bigWidth,int bigHeight,String imageSuffix){
		this.uploadFilePath = uploadFilePath;
		this.smallWidth = smallWidth;
		this.smallHeight = smallHeight;
		this.middleWidth = middleWidth;
		this.middleHeight = middleHeight;
		this.bigWidth = bigWidth;
		this.bigHeight = bigHeight;
		this.imageSuffix = imageSuffix;

	}

	public String getUploadFilePath() {
		return uploadFilePath;
	}

	public void setUploadFilePath(String uploadFilePath) {
		this.uploadFilePath = uploadFilePath;
	}

	public int getSmallWidth() {
		return smallWidth;
	}

	public void setSmallWidth(int smallWidth) {
		this.smallWidth = smallWidth;
	}

	public int getSmallHeight() {
		return smallHeight;
	}

	public void setSmallHeight(int smallHeight) {
		this.smallHeight = smallHeight;
	}

	public int getMiddleWidth() {
		return middleWidth;
	}

	public void setMiddleWidth(int middleWidth) {
		this.middleWidth = middleWidth;
	}

	public int getMiddleHeight() {
		return middleHeight;
	}

	public void setMiddleHeight(int middleHeight) {
		this.middleHeight = middleHeight;
	}

	public int getBigWidth() {
		return bigWidth;
	}

	public void setBigWidth(int bigWidth) {
		this.bigWidth = bigWidth;
	}

	public int getBigHeight() {
		return bigHeight;
	}

	public void setBigHeight(int bigHeight) {
		this.bigHeight = bigHeight;
	}

	public String getImageSuffix() {
		return imageSuffix;
	}

	public void setImageSuffix(String imageSuffix) {
		this.imageSuffix = imageSuffix;
	}
}
