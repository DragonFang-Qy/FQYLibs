package com.fqy.fqylibs.utils;

import java.io.FileOutputStream;

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

	private static int myWidth;
	private static int myHeight;
	
	
	
	private static void setWidth(int width) {
		myWidth = width;
	}

	private static void setHeight(int height) {
		myHeight = height;
	}

	/** 计算压缩比例
	 * @author: Fang Qingyou
	 * @date 2015年7月16日下午4:16:23    
	 * @param options
	 * @param imageHeight  目标高
	 * @param imageWidth   目标宽
	 * @return
	 */
	private static int calculateInSampleSize(Options options,
			int imageWidth, int imageHeight) {
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

	/** 压缩图片，不处理反转信息
	 * @author: Fang Qingyou
	 * @date 2015年7月16日下午4:15:38    
	 * @param fileName
	 * @return
	 */
	public static Bitmap getSmallBitmap(String fileName) {
		Bitmap bitmap = getSmallBitmap(fileName, 400, 800);
		return bitmap;
	}


	/** 压缩图片，不处理反转信息
	 * @author: Fang Qingyou
	 * @date 2015年7月16日下午4:20:59    
	 * @param fileName
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap getSmallBitmap(String fileName,int width, int height) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true; // 设置为true，只读边框，不读内容
		BitmapFactory.decodeFile(fileName, options);
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		
		setWidth(width);
		setHeight(height);
		
		options.inSampleSize = calculateInSampleSize(options, myWidth, myHeight);

		options.inJustDecodeBounds = false;
		
		return BitmapFactory.decodeFile(fileName, options);
		
	}

	public static Bitmap getCompressBitmap(Context context, String filePath, int width, int height) {
		Bitmap bitmap = getSmallBitmap();
	}
	
	{
		/**
		 * 得到小于要求宽高的图片 不处理翻转信息
		 * 
		 * @param filePath
		 * @param width
		 * @param height
		 * @return
		 */
		public static Bitmap getComPressBitmap(String filePath, int width, int height) {
			BitmapFactory.Options ops = new BitmapFactory.Options();
			ops.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(filePath, ops);
			ops.inSampleSize = calculateInSampleSize(ops, width, height);
			ops.inPreferredConfig = Bitmap.Config.RGB_565;
			ops.inJustDecodeBounds = false;

			FileInputStream is = null;
			Bitmap bitmap = null;
			try {
				is = new FileInputStream(filePath);
				bitmap = BitmapFactory.decodeFileDescriptor(is.getFD(), null, ops);
				return bitmap;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (null != is) {
						is.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}

		/**
		 * 某些图片带反转信息 需要在读取时恢复过来
		 * 
		 * @param context
		 * @param filePath
		 * @param width
		 *            最大的宽高
		 * @param height
		 * @return
		 */
		public static Bitmap getCompressBitmap(Context context, String filePath, int width, int height) {
			Bitmap bitmap = getComPressBitmap(filePath, width, height);
			if (bitmap != null) {
				try {
					ExifInterface exifInterface = new ExifInterface(filePath);
					int result = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
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
					Bitmap rotated = ImageUtil.rotate(bitmap, rotate);
					return rotated;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			return null;

		}

	}
}
