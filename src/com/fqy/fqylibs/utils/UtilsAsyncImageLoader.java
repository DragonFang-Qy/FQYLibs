package com.fqy.fqylibs.utils;

import java.io.File;
import java.io.IOException;

import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.util.LruCache;

/**
 * @Title: UtilsAsyncImageLoader.java
 * @Package com.so.buyer.utils
 * @Description: TODO ��ӭҳ
 * @author: Fang Qingyou
 * @date 2015��7��10������2:59:56
 * @version V1.0
 */
public class UtilsAsyncImageLoader {

	private LruCache<String, Drawable> imageCache;
	private static String FILEPATH;

	public UtilsAsyncImageLoader(String filePath) {

		if (imageCache == null) {
			imageCache = new LruCache<String, Drawable>(4 * 1024 * 1024);
		}

		this.FILEPATH = Environment.getDownloadCacheDirectory() + "/imageCache";
		makeDir();
	}

	public static void makeDir() {

	}
	//
	// public static void makeDir() {
	// FILEPATH = Environment.getExternalStorageDirectory().getAbsolutePath()
	// + "/" + "soso" + "/" + "cache" + "/";
	// File fileDir = new File(FILEPATH);
	// if (!fileDir.exists()) {
	// fileDir.mkdirs();
	// try {
	// new File(FILEPATH + ".nomedia").createNewFile();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }
}
