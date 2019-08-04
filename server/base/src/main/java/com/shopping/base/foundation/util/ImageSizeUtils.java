/*
 * Copyright 2016 - 2017 suoke & Co., Ltd.
 */
package com.shopping.base.foundation.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 *
 * @version 1.0 created at 2017年4月21日 下午3:57:40
 *
 */
public class ImageSizeUtils {

	public static BufferedImage cut(BufferedImage bi, int w, int h) throws IOException {
		// 进行图片拉缩
		int oldWidth = bi.getWidth();
		int oldHeight = bi.getHeight();
		double wr = 1.0;
		double hr = 1.0;
		// 横图(宽 > 高)
		if (oldWidth > oldHeight) {
			hr = h * 1.0 / oldHeight;
			wr = hr;
			// 拉伸后宽度不足
			if (wr * oldWidth < w) {
				wr = w * 1.0 / oldWidth;
				hr = wr;
			}
		}
		// 竖图
		else {
			wr = w * 1.0 / oldWidth;
			hr = wr;
			// 拉伸后高度不足
			if (hr * oldHeight < h) {
				hr = h * 1.0 / oldHeight;
				wr = hr;
			}
		}
		AffineTransform transform = AffineTransform.getScaleInstance(wr, hr);
		AffineTransformOp ato = new AffineTransformOp(transform, null);
		BufferedImage image = (BufferedImage) ato.filter(bi, null);
		int newWidth = (int) (wr * oldWidth);
		int newHeight = (int) (hr * oldHeight);
		int x = 0, y = 0;
		// 宽度大于预期宽度，裁剪左右
		if (newWidth > w) {
			x = -(newWidth - w) / 2;
		} else if (newHeight > h) {// 裁剪上下
			y = -(newHeight - h) / 2;
		}
		BufferedImage newImage = new BufferedImage(w, h, image.getType());
		Graphics g = newImage.getGraphics();
		g.drawImage(image, x, y, null);
		return newImage;
	}

	public static BufferedImage cut(String format, InputStream src, int x, int y, int w, int h) throws IOException {
		Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName(format);
		ImageReader reader = (ImageReader) iterator.next();
		ImageInputStream iis = ImageIO.createImageInputStream(src);
		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();
		int imgWidth = reader.getWidth(0);
		int imgHeight = reader.getHeight(0);
		if (x + w > imgWidth) {
			w = imgWidth - x;
		}
		if (y + h > imgHeight) {
			h = imgHeight - y;
		}
		Rectangle rect = new Rectangle(x, y, w, h);
		param.setSourceRegion(rect);
		BufferedImage bi = reader.read(0, param);
		return bi;
	}
}