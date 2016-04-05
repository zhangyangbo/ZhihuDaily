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
		// ������   ǰ������
		DiskCache diskCache = ImageLoader.getInstance().getDiskCache();
		File cacheFile = diskCache.get(imageUrls);
		boolean hasCache = cacheFile != null;
		if (hasCache){
			// �ӻ����л�ȡͼƬ
			UILUtils.displayImage(imageUrls, mImageView);
		} else if (isConnected){
			boolean isNoPic = PreferenceUtils.isNoPic(context);
//			boolean isWIFI = ConnectionUtils.isWIFI(getContext());
			boolean isWIFI = false;
			// ûѡ����ͼģʽ || (ѡ����ͼģʽ && ��WIFI )
			boolean condition = (!isNoPic || (isNoPic && isWIFI));
			if (condition){
				UILUtils.displayImage(imageUrls, mImageView);
			}
		} 
	}
}
