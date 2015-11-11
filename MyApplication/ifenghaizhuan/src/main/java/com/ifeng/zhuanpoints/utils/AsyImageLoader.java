package com.ifeng.zhuanpoints.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.ifeng.zhuanpoints.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class AsyImageLoader {
	private Activity mActivity;
	private ImageLoader imageLoader;
	private DisplayImageOptions options, options1;

	public AsyImageLoader(Activity activity) {
		mActivity = activity;
		initPic();
	}

	private void initPic() {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				mActivity)
				.memoryCacheExtraOptions(1080, 1920)
				// 最大图片缓存大小
				// max width, max height，即保存的每个缓存文件的最大长宽
				// .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75,
				// null) // Can slow ImageLoader, use it carefully (Better don't
				// use it)/设置缓存的详细信息，最好不要设置这个
				.threadPoolSize(3)
				// 设置线程池个数
				// 线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
				// You can pass your own memory cache
				// implementation/你可以通过自己的内存缓存实现
				.memoryCacheSize(2 * 1024 * 1024)
				.discCacheSize(50 * 1024 * 1024)
				// 图片缓冲空间
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// 将保存的时候的URI名称用MD5 加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCacheFileCount(100) // 缓存的文件数量100
				// .discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				// .imageDownloader(new BaseImageDownloader(context, 5 * 1000,
				// 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
				// .writeDebugLogs() // Remove for release app
				.build();// 开始构建
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);// 初始化
		imageLoader = ImageLoader.getInstance();
		initOptions();
	}

	public void loadImage(ImageView view, String url, boolean option) {
		if (TextUtils.isEmpty(url))
			return;
		if (url.startsWith("file://")) {
			imageLoader.displayImage(url, view, options);
		} else if (url.startsWith("http")) {
			imageLoader.displayImage(url, view, options);
		} else {
			imageLoader.displayImage(ContentUtil.HOST_PIC + url, view, options);

		}
	}

	public void loadImageProgress(ImageView view, String url, boolean option,
			ImageLoadingListener mImageload,
			ImageLoadingProgressListener mImagePro) {// 带进度的图片加载类

		if (url.startsWith("file://")) {
			imageLoader
					.displayImage(url, view, options1, mImageload, mImagePro);
		} else if (null != url && url.startsWith("http")) {
			imageLoader
					.displayImage(url, view, options1, mImageload, mImagePro);
		} else {
			imageLoader.displayImage(ContentUtil.HOST_PIC + url, view,
					options1, mImageload, mImagePro);

		}
	}

	private void initOptions() {

		// options = new DisplayImageOptions.Builder().
		// // .showImageOnLoading(R.drawable.ic_launcher) //设置图片在下载期间显示的图片
		// .showImageForEmpty(R.drawable.ic_launcher)//设置图片Uri为空或是错误的时候显示的图片
		// .showImageOnFail(R.drawable.ic_launcher) //设置图片加载/解码过程中错误时候显示的图片
		// .cacheInMemory(true)//设置下载的图片是否缓存在内存中
		// .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
		// .considerExifParams(true) //是否考虑JPEG图像EXIF参数（旋转，翻转）
		// .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
		// .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
		// .decodingOptions(android.graphics.BitmapFactory.Options
		// decodingOptions)//设置图片的解码配置
		// //.delayBeforeLoading(int delayInMillis)//int
		// delayInMillis为你设置的下载前的延迟时间
		// //设置图片加入缓存前，对bitmap进行设置default_image_large_light.9
		// //.preProcessor(BitmapProcessor preProcessor)
		// .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
		// .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
		// .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
		// .build();//构建完成
		options = new DisplayImageOptions.Builder()
				// .showStubImage(R.drawable.default_image_little)
				.showImageOnFail(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher)
				// .cacheInMemory().cacheOnDisc()
				.cacheInMemory(true).cacheOnDisc(true)
				.bitmapConfig(Bitmap.Config.RGB_565) // 设置图片的解码类型
				.build();
		options1 = new DisplayImageOptions.Builder()
				// .showStubImage(R.drawable.default_image_large_dark)
				.showImageOnFail(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565) // 设置图片的解码类型
				.build();
	}

	/**
	 * 获取屏幕宽度，px
	 * 
	 * @param context
	 * @return
	 */
	public static float getScreenWidth(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.widthPixels;
	}

	/**
	 * 获取屏幕高度，px
	 * 
	 * @param context
	 * @return
	 */
	public static float getScreenHeight(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.heightPixels;
	}
}
