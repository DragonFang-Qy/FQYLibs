package com.fqy.fqylibs.utils;

import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;

/**
 * 图片工具类
 * <p>
 * 压缩图片，不处理反转信息 {@link #getSmallBitmap(String)}&nbsp;&nbsp;
 * {@link#getSmallBitmap(String, int, int)}</br> 压缩图片处理反转信息
 * {@link #getCompressBitmap(String)}&nbsp;&nbsp;
 * {@link#getCompressBitmap(String, int, int)}
 * </p>
 * 
 * @Title: UtilsImage.java
 * @Package com.fqy.fqylibs.utils
 * @author: Fang Qingyou
 * @date 2015年7月11日下午4:34:52
 * @version V1.0
 */
public class UtilsImage {

	private static int myWidth;
	private static int myHeight;

	private static void setWidth(int width) {
		myWidth = width;
	}

	private static void setHeight(int height) {
		myHeight = height;
	}

	/**
	 * 计算压缩比例
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年7月16日下午4:16:23
	 * @param options
	 * @param imageHeight
	 *            目标高
	 * @param imageWidth
	 *            目标宽
	 * @return
	 */
	private static int calculateInSampleSize(Options options, int imageWidth,
			int imageHeight) {
		// 实际宽高
		int outHeight = options.outHeight;
		int outWidth = options.outWidth;

		int inSampleSize = 1;// 缩放比例，默认为1
		if (outHeight > imageHeight || outWidth > imageWidth) {
			// 当outHeight >imageHeight|| outWidth > imageWidth 时进行缩放
			int heightFlag = outHeight / imageHeight;
			int widthFlag = outWidth / imageWidth;

			inSampleSize = heightFlag < widthFlag ? heightFlag : widthFlag;

		}

		return inSampleSize % 2 == 0 ? inSampleSize : inSampleSize - 1;
		// 官方文档中说，inSampleSize这个属性最好是2的倍数，这样处理更快，效率更高。
	}

	/**
	 * 压缩图片，不处理反转信息
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年7月16日下午4:15:38
	 * @param fileName
	 * @return
	 */
	public static Bitmap getSmallBitmap(String fileName,
			FileOutputStream outputStream) {
		Bitmap bitmap = getSmallBitmap(fileName, 400, 800, outputStream);
		return bitmap;
	}

	/**
	 * 压缩图片，不处理反转信息
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年7月16日下午4:15:38
	 * @param fileName
	 * @return
	 */
	public static Bitmap getSmallBitmap(String fileName) {
		Bitmap bitmap = getSmallBitmap(fileName, 400, 800, null);
		return bitmap;
	}

	/**
	 * 压缩图片，不处理反转信息
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年7月16日下午4:20:59
	 * @param fileName
	 * @param width
	 * @param height
	 * @param outputStream
	 * @return
	 * 
	 */
	public static Bitmap getSmallBitmap(String fileName, int width, int height,
			FileOutputStream outputStream) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true; // 设置为true，只读边框，不读内容
		BitmapFactory.decodeFile(fileName, options);
		options.inPreferredConfig = Bitmap.Config.RGB_565;

		setWidth(width);
		setHeight(height);

		options.inSampleSize = calculateInSampleSize(options, myWidth, myHeight);

		options.inJustDecodeBounds = false;
		if (outputStream == null) {
			return BitmapFactory.decodeFile(fileName, options);
		} else {
			Bitmap bitmap = BitmapFactory.decodeFile(fileName, options);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream);// 压缩质量
			return bitmap;
		}

	}

	/**
	 * 压缩图片处理反转信息
	 * 
	 * @Title: getCompressBitmap
	 * @param filePath
	 * @return
	 * 
	 */
	public static Bitmap getCompressBitmap(String filePath,
			FileOutputStream outputStream) {
		Bitmap bitmap = getCompressBitmap(filePath, 400, 800, outputStream);
		return bitmap;
	}

	/**
	 * 压缩图片处理反转信息
	 * 
	 * @Title: getCompressBitmap
	 * @param filePath
	 * @return
	 * 
	 */
	public static Bitmap getCompressBitmap(String filePath) {
		Bitmap bitmap = getCompressBitmap(filePath, 400, 800, null);
		return bitmap;
	}

	/**
	 * 压缩图片处理反转信息
	 * 
	 * @Title: getCompressBitmap
	 * @param filePath
	 * @param width
	 *            目标宽
	 * @param height
	 *            目标高
	 * @return
	 * 
	 */
	public static Bitmap getCompressBitmap(String filePath, int width,
			int height, FileOutputStream outputStream) {
		Bitmap bitmap = getSmallBitmap(filePath, width, height, outputStream);
		if (bitmap != null) {
			try {
				ExifInterface exifInterface = new ExifInterface(filePath);
				int result = exifInterface.getAttributeInt(
						ExifInterface.TAG_ORIENTATION,
						ExifInterface.ORIENTATION_UNDEFINED);
				int rotate = 0;
				switch (result) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					rotate = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					rotate = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					rotate = 270;
					break;
				default:
					break;
				}
				Bitmap rotated = rotateBitmap(bitmap, rotate);
				return rotated;
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return null;

	}

	/**
	 * 旋转bitmap
	 * 
	 * @Title: rotateBitmap
	 * @param bitmap
	 * @param rotate
	 * @return
	 * 
	 */
	private static Bitmap rotateBitmap(Bitmap bitmap, int rotate) {
		if (bitmap == null)
			return null;

		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		// Setting post rotate to 90
		Matrix mtx = new Matrix();
		mtx.postRotate(rotate);
		return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
	}
}
