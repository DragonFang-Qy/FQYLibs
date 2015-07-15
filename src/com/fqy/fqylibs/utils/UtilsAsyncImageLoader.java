package com.fqy.fqylibs.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.view.View;
import android.widget.ImageView;

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

	private ExecutorService service = Executors.newSingleThreadExecutor();

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

	/**
	 * 从URL 加载图片
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年7月14日下午1:23:47
	 * @param view
	 * @param urlPath
	 */
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static <T extends View> void loadImageFromUrl(T view, String urlPath) {
		InputStream stream = null;
		File file = null;
		FileOutputStream outputStream = null;

		try {
			stream = new URL(urlPath).openStream();

			file = new File(FILEPATH + UtilsMD5.GetMD5Code16(urlPath));

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

			Bitmap smallBitmap = UtilsImage.getSmallBitmap(file
					.getAbsolutePath());

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

	public void loadDrawable(final String imageUrl, final ImageView imageView) {

		Drawable drawable = null;
		final String fileName = FILEPATH + UtilsMD5.GetMD5Code16(imageUrl);
		File file = new File(fileName);
		if (file.exists()) {
			drawable = new BitmapDrawable(imageView.getResources(),
					UtilsImage.getSmallBitmap(fileName));
			if (drawable != null) {
				imageCache.put(fileName, drawable);
				imageView.setVisibility(View.VISIBLE);
				imageView.setImageDrawable(drawable);
				return;
			}
		}

		drawable = imageCache.get(fileName);

		if (drawable != null) {
			imageView.setVisibility(View.VISIBLE);
			imageView.setImageDrawable(drawable);
			return;
		}

		imageView.setTag(imageUrl);
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {

				loadImageFromUrl(imageView, imageUrl);
				Drawable drawable = new BitmapDrawable(
						imageView.getResources(),
						UtilsImage.getSmallBitmap(fileName));
				imageCache.put(fileName, drawable);

			}
		};
		service.submit(new Runnable() {

			@Override
			public void run() {
				Message message = new Message();
				handler.sendMessage(message);
			}

		});

	}
}
