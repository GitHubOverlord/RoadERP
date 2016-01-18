package com.lida.road.application;

import android.graphics.Bitmap;

import com.juhn.android_frame.application.MainBaseApplication;
import com.jun.android_frame.http.utils.HttpUtils;
import com.lida.road.R;
import com.lida.road.utils.AuthImageDownloader;
import com.loopj.android.http.PersistentCookieStore;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class MainApplication extends MainBaseApplication {
	
	public static PersistentCookieStore persistentCookieStore;
	@Override
	public void onCreate() {
		super.onCreate();
		persistentCookieStore = new PersistentCookieStore(getApplicationContext());
		HttpUtils.getClient().setCookieStore(persistentCookieStore);
		initAppForMainProcess();
	}

	/**
	 * 
	 * 初始化universal-image-loader缓存加载图片配置
	 */
	private void initAppForMainProcess() {

		// 初始化imageloader

		@SuppressWarnings("deprecation")
		DisplayImageOptions options = new DisplayImageOptions.Builder() // 显示参数配置

				.cacheInMemory(true).cacheOnDisc(true) // 设置图片缓存在内存/sd卡中

				.imageScaleType(ImageScaleType.IN_SAMPLE_INT) // 设置图片编码方式

				.bitmapConfig(Bitmap.Config.RGB_565)// 图片解码类型。防止内存溢出的，图片太多就这这个。还有其他设置

				 .showImageOnLoading(R.drawable.ic_launcher) // 默认图片

				 .showImageForEmptyUri(R.drawable.ic_launcher) //

				// url爲空會显示该图片，自己放在drawable里面的

				 .showImageOnFail(R.drawable.ic_launcher)// 加载失败显示的图片

				.displayer(new RoundedBitmapDisplayer(1)) // 圆角，不需要请删除
				.build();

		@SuppressWarnings("deprecation")
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this)
				// 图片参数配置

				.memoryCacheExtraOptions(480, 800)
				// 缓存在内存的图片的最大宽和高
				.imageDownloader(new AuthImageDownloader(getApplicationContext()))
				.memoryCache(new WeakMemoryCache())
				.memoryCacheSize(2 * 1024 * 1024)
				// 缓存到内存的最大数据

				.threadPriority(Thread.NORM_PRIORITY - 2)
				// 设置当前线程优先级

				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO) // 设置图片加载和显示队列处理的类型

				.defaultDisplayImageOptions(options) // 上面的options对象，一些属性配置
				
				.build();

		ImageLoader.getInstance().init(config); // 初始化

	}
}
