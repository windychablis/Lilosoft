package com.chablis.lilosoft.utils;

import android.widget.ImageView;

import com.chablis.lilosoft.base.AppContext;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * 图片加载处理类
 * @author ray
 * @date 2015-03-15
 */
public class ImageLoaderUtils {
	private static ImageLoader imageloader;
	public static void loaderImage(ImageView imageView,String url,DisplayImageOptions ops){
		imageloader=ImageLoader.getInstance();
		imageloader.displayImage(url, imageView, ops);
	}
	
	public static void loaderImage(ImageView imageView,String url){
		imageloader=ImageLoader.getInstance();
		imageloader.displayImage(url, imageView);
	}
	
	public static void loaderRoundedImage(ImageView imageView,String url){
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
		.displayer(new RoundedBitmapDisplayer(10))
		.cacheInMemory(true)
		.cacheOnDisc(true).build();
		
		ImageLoaderConfiguration config2 = new ImageLoaderConfiguration.Builder(AppContext.get())
		.defaultDisplayImageOptions(defaultOptions)
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.denyCacheImageMultipleSizesInMemory()
		.discCacheFileNameGenerator(new Md5FileNameGenerator())
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		.build();
		ImageLoader.getInstance().init(config2);
		imageloader=ImageLoader.getInstance();
		imageloader.displayImage(url, imageView);
	}
	
	/**
	 * 清除双缓存
	 * 一般修改图片后清除
	 */
	public static void clearCache(){
		imageloader=ImageLoader.getInstance();
		imageloader.clearDiscCache();
		imageloader.clearMemoryCache();
	}
}
