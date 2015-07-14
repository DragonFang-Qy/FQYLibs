package com.fqy.fqylibs.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.view.View;

import com.fqy.fqylibs.R;

/**
 * @Title: UtilsAsyncImageLoader.java
 * @Package com.fqy.fqylibs.utils
 * @Description: TODO 欢迎页
 * @author: Fang Qingyou
 * @date 2015年7月11日上午9:24:13
 * @version V1.0
 */

public class UtilsAsyncImageLoader {
	private LruCache<String, Drawable> imageCache;
	private static String FILEPATH;

	public UtilsAsyncImageLoader(String filePath) {

		if (imageCache == null) {
			imageCache = new LruCache<String, Drawable>(4 * 1024 * 1024);

		}

		FILEPATH = Environment.getDownloadCacheDirectory() + "/imageCache";
		makeDir();
	}

	private static void makeDir() {
		File fileDir = new File(FILEPATH);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
			// try {
			// new File(FILEPATH + ".nomedia").createNewFile();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
		}
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static <T extends View> void loadImageFromUrl(T view, String urlPath) {
		InputStream stream = null;
		File file = null;
		FileOutputStream outputStream = null;

		try {
			stream = new URL(urlPath).openStream();

			file = new File(
					FILEPATH
							+ urlPath.substring(urlPath.length() - 10,
									urlPath.length()));

			if (!file.exists()) {
				file.createNewFile();
			} else {
				file.delete();
				file.createNewFile();
			}

			outputStream = new FileOutputStream(file);

			byte[] bs = new byte[1024];
			int flag;
			while ((flag = stream.read(bs)) != -1) {
				outputStream.write(bs, 0, flag);
			}

			Bitmap smallBitmap = UtilsImage.getSmallBitmap(file.getAbsolutePath());

			view.setBackgroundDrawable(new BitmapDrawable(view.getResources(),
					smallBitmap));
			// Build.VERSION_CODES.JELLY_BEAN == 16 android 4.1
			if (Build.VERSION.SDK_INT >= 16) {
				view.setBackground(new BitmapDrawable(view.getResources(),
						smallBitmap));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {

				if (outputStream != null) {
					outputStream.close();
				}

				if (stream != null) {
					stream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}


}
