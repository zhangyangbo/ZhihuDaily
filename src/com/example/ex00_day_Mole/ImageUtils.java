package com.example.ex00_day_Mole;

import java.io.File;

import com.example.ex00_day_con.PreferenceUtils;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinbo.utils.ConnectionUtils;
import com.xinbo.utils.UILUtils;

import android.content.Context;
import android.widget.ImageView;

public class ImageUtils {
	@SuppressWarnings("deprecation")
	public static void displayImage(Context context, String imageUrls, ImageView mImageView){
		boolean isConnected = ConnectionUtils.isConnected(context);
		// 有网络   前提条件
		DiskCache diskCache = ImageLoader.getInstance().getDiskCache();
		File cacheFile = diskCache.get(imageUrls);
		boolean hasCache = cacheFile != null;
		if (hasCache){
			// 从缓存中获取图片
			UILUtils.displayImage(imageUrls, mImageView);
		} else if (isConnected){
			boolean isNoPic = PreferenceUtils.isNoPic(context);
//			boolean isWIFI = ConnectionUtils.isWIFI(getContext());
			boolean isWIFI = false;
			// 没选中无图模式 || (选中无图模式 && 是WIFI )
			boolean condition = (!isNoPic || (isNoPic && isWIFI));
			if (condition){
				UILUtils.displayImage(imageUrls, mImageView);
			}
		} 
	}
}
