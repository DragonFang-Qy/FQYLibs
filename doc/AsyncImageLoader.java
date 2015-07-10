package com.so.buyer.utils;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.so.buyer.R;
import com.so.buyer.SoSoApplication;

/**
 * ʵ��ͼƬ���첽������ʾ
 */

@SuppressLint("HandlerLeak")
public class AsyncImageLoader {

	private static LruCache<String, Drawable> imageCache;
	private static String FILEPATH;
	private ExecutorService service = Executors.newSingleThreadExecutor();

	public AsyncImageLoader() {
		if (imageCache == null)
			imageCache = new LruCache<String, Drawable>(4 * 1024 * 1024);
		makeDir();
	}

	public static void makeDir() {
		FILEPATH = Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/" + "soso" + "/" + "cache" + "/";
		File fileDir = new File(FILEPATH);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
			try {
				new File(FILEPATH + ".nomedia").createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressLint("NewApi")
	public static Drawable loadImageFromUrl(String urlPath, String imageName) {
		InputStream stream = null;
		try {
			stream = new URL(urlPath).openStream();
			File file = new File(FILEPATH + imageName);
			if (!file.exists()) {
				file.createNewFile();
			}
			if (file.exists()) {
				file.delete();
				file.createNewFile();
			}
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int ins;
			while ((ins = stream.read(buf)) != -1) {
				fileOutputStream.write(buf, 0, ins);
			}
			fileOutputStream.close();

			return Drawable.createFromPath(FILEPATH + imageName);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("exception:", urlPath);
			Drawable d = SoSoApplication.context
					.getDrawable(R.drawable.icon_image);
			Log.e("drawable", d.toString());
			return d;
			// throw new RuntimeException(e);
		} finally {
			try {
				if (stream != null) {
					stream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public Drawable loadDrawable(final String imageUrl,
			final ImageCallback imagecallback) {
		Drawable drawable = null;

		String imageBaseName[] = imageUrl.split("\\/");
		final String iName = imageBaseName[imageBaseName.length - 1];
		String iname;
		if (iName.contains("?")) {
			String imName[] = iName.split("\\?");
			iname = imName[1];
		} else {
			String imName[] = iName.split("\\.");
			iname = imName[0];
		}
		final String imageName = iname;
		drawable = imageCache.get(imageName);
		if (drawable != null) {
			return drawable;

		}

		File file = new File(FILEPATH + imageName);
		if (file.exists()) {
			drawable = Drawable.createFromPath(FILEPATH + imageName);

			if (drawable != null) {

				imageCache.put(imageName, drawable);

				return drawable;
			}
		}

		imagecallback.setTag(imageUrl);

		final Handler handler = new Handler() {

			public void handleMessage(Message msg) {

				imagecallback.imageLoaded(msg);

			}

		};
		service.submit(new Runnable() {

			@Override
			public void run() {
				Drawable drawable = loadImageFromUrl(imageUrl, imageName);

				if (drawable != null) {
					imageCache.put(imageName, drawable);

					Message message = handler.obtainMessage(0, drawable);

					Bundle bundle = new Bundle();

					bundle.putString("name", imageUrl);

					message.setData(bundle);

					handler.sendMessage(message);
				}

			}
		});

		return null;
	}

	public void loadDrawable(final String imageUrl, final ImageView imageView) {
		Drawable drawable = null;

		String imageBaseName[] = imageUrl.split("\\/");
		final String iName = imageBaseName[imageBaseName.length - 1];
		String imName[] = iName.split("\\.");
		final String imageName = imName[0];

		drawable = imageCache.get(imageName);
		if (drawable != null) {
			imageView.setVisibility(View.VISIBLE);
			imageView.setImageDrawable(drawable);
			return;
		}

		{
			File file = new File(FILEPATH + iName);
			if (file.exists()) {
				drawable = Drawable.createFromPath(FILEPATH + iName);
				if (drawable != null) {
					imageCache.put(imageName, drawable);
					imageView.setVisibility(View.VISIBLE);
					imageView.setImageDrawable(drawable);
					return;
				}
			}
		}

		final CallbackImpl imagecallback = new CallbackImpl(imageView);
		imagecallback.setTag(imageUrl);

		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				imagecallback.imageLoaded(msg);
			}
		};
		service.submit(new Runnable() {

			@Override
			public void run() {
				Drawable drawable = loadImageFromUrl(imageUrl, iName);

				if (drawable != null) {
					imageCache.put(imageName, drawable);

					Message message = handler.obtainMessage(0, drawable);

					Bundle bundle = new Bundle();

					bundle.putString("name", imageUrl);

					message.setData(bundle);

					handler.sendMessage(message);
				}
			}
		});

	}

	public void loadHeadDrawable(final String imageUrl,
			final ImageView imageView) {
		Drawable drawable = null;

		String imageBaseName[] = imageUrl.split("\\/");
		final String iName = imageBaseName[imageBaseName.length - 1];
		String imName[] = iName.split("\\.");
		final String imageName = imName[0];

		drawable = imageCache.get(imageName);
		if (drawable != null) {
			imageView.setVisibility(View.VISIBLE);
			imageView.setImageDrawable(drawable);
			return;
		}

		// {
		// File file = new File(FILEPATH + iName);
		// if (file.exists()) {
		// drawable = Drawable.createFromPath(FILEPATH + iName);
		// if (drawable != null) {
		// imageCache.put(imageName, drawable);
		// imageView.setVisibility(View.VISIBLE);
		// imageView.setImageDrawable(drawable);
		// return;
		// }
		// }
		// }

		final CallbackImpl imagecallback = new CallbackImpl(imageView);
		imagecallback.setTag(imageUrl);

		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				imagecallback.imageLoaded(msg);
			}
		};
		service.submit(new Runnable() {

			@Override
			public void run() {
				Drawable drawable = loadImageFromUrl(imageUrl, iName);

				if (drawable != null) {
					imageCache.put(imageName, drawable);

					Message message = handler.obtainMessage(0, drawable);

					Bundle bundle = new Bundle();

					bundle.putString("name", imageUrl);

					message.setData(bundle);

					handler.sendMessage(message);
				}
			}
		});

	}

	// public Drawable loadDrawable(final String imageName, final String
	// imageUrl,final ImageCallback imagecallback) {
	// Drawable drawable = null;
	// final Handler handler = new Handler() {
	//
	// public void handleMessage(Message msg) {
	//
	// imagecallback.imageLoaded((Drawable) msg.obj);
	//
	// }
	//
	// };
	// if (imageCache.containsKey(imageName)) {
	//
	// // �ӻ����ж�ȡ
	// SoftReference<Drawable> softReference = imageCache.get(imageName);
	//
	// drawable = softReference.get();
	//
	// if (drawable != null) {
	//
	// return drawable;
	//
	// }
	//
	// }
	// service.submit(new Runnable() {
	//
	// @Override
	// public void run() {
	// Drawable drawable = loadImageFromUrl(imageUrl, imageName);
	//
	// imageCache
	// .put(imageName, new SoftReference<Drawable>(drawable));
	//
	// Message message = handler.obtainMessage(0, drawable);
	//
	// handler.sendMessage(message);
	// }
	// });
	//
	// return null;
	//
	// }

	// public static Drawable loadImageFromUrl(String urlPath,String imageName){
	// try {
	// return Drawable.createFromStream(new URL(urlPath).openStream(), "src");
	// } catch (Exception e) {
	// throw new RuntimeException(e);
	// }
	// }

	public void deleteImage(String imageUrl) {
		String imageBaseName[] = imageUrl.split("\\/");
		final String iName = imageBaseName[imageBaseName.length - 1];
		String iname;
		if (iName.contains("?")) {
			String imName[] = iName.split("\\?");
			iname = imName[1];
		} else {
			String imName[] = iName.split("\\.");
			iname = imName[0];
		}
		final String imageName = iname;
		imageCache.remove(imageName);
	}

	public interface ImageCallback {
		public void imageLoaded(Message message);

		public void setTag(String tag);
	}

}
