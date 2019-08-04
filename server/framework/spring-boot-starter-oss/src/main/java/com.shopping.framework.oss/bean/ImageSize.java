package com.shopping.framework.oss.bean;

import java.io.Serializable;

/**
 * @Author 没有用户名
 * @Date 2017/8/25
 * @Description
 */
public class ImageSize implements Serializable {
	private static final long serialVersionUID = -9069477480307598770L;
	private int width;
	private int height;

	public ImageSize() {
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}

