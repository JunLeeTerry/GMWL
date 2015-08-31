/*
 *******************************************************************************
 * All rights Reserved, Copyright (C) www.gmly.com 2015
 * FileName: Base64Util.java
 * Modify record:
 * NO. |     Date       |    Version      |    Name         |      Content
 * 1   | 2015年6月2日        |   1.0           |  GMSZ)LiangYan  | original version
 *******************************************************************************
 */
package com.gmsz.utils;

/**
 * Class name:Base64Util
 * Description: please write your description
 * @author LiangYan
 */
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Base64Util {
	public static void main(String[] args) {

	}

	public static Bitmap stringtoBitmap(String string) {
		// 将字符串转换成Bitmap类型
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;
		try {
			byte[] bitmapArray = Base64.decode(string, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length, options);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	public static Bitmap stringtoBitmap1(String string) {
		// 将字符串转换成Bitmap类型
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;
		try {
			byte[] bitmapArray = Base64.decode(string, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length, options);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;
	}
}
