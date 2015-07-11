package com.fqy.fqylibs.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

/**
 * 图片工具类
 * 
 * @Title: UtilsImage.java
 * @Package com.fqy.fqylibs.utils
 * @Description: TODO 欢迎页
 * @author: Fang Qingyou
 * @date 2015年7月11日下午4:34:52
 * @version V1.0
 */
public class UtilsImage {

	public static Bitmap getSmallBitmap(String fileName) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true; // 设置为true，只读边框，不读内容
		BitmapFactory.decodeFile(fileName, options);

		options.inSampleSize = calculateInSampleSize(options, 480, 800);

		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(fileName, options);

	}

	private static int calculateInSampleSize(Options options, int imageHeight,
			int imageWidth) {

		int outHeight = options.outHeight;
		int outWidth = options.outWidth;

		int inSampleSize = 1;// 缩放比例，默认为1
		if (outHeight > imageHeight || outWidth > imageWidth) {
			// 当outHeight >imageHeight|| outWidth > imageWidth 时进行缩放
			int heightFlag = outHeight / imageHeight;
			int widthFlag = outWidth / imageWidth;
			inSampleSize = heightFlag < widthFlag ? heightFlag : widthFlag;
		}

		return 0;
	}

}
