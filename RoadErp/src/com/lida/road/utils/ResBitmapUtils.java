package com.lida.road.utils;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

public class ResBitmapUtils {
	public static BitmapDrawable getBitmap(int id,Context context){
		BitmapFactory.Options opt = new BitmapFactory.Options();

		opt.inPreferredConfig = Bitmap.Config.RGB_565;

		opt.inPurgeable = true;

		opt.inInputShareable = true;

		//获取资源图片

		InputStream is = context.getResources().openRawResource(id);

		Bitmap bitmap = BitmapFactory.decodeStream(is,null, opt);

		try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new BitmapDrawable(context.getResources(),bitmap);
	}
}
