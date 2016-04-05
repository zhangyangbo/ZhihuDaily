package com.example.ex00_day_fragment;

import java.util.List;

import com.example.ex00_day_.R;
import com.example.ex00_day_.R.id;
import com.example.ex00_day_.R.layout;
import com.example.ex00_day_Mole.TopStory;
import com.xinbo.utils.UILUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ImageResFragment extends Fragment {

	private int resId;
	private TopStory topStory;
	private int index;
	public ImageResFragment(int resId, int index)
	{
		this.resId = resId;
		this.index = index;
	}
	public ImageResFragment(TopStory topStory, int index)
	{
		this.topStory = topStory;
		this.index = index;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_imageres,null);
		ImageView mImageView = (ImageView) view.findViewById(R.id.imageView1);
		//图片的点击事件
		mImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			}
			
		});
		    String image = topStory.getImage();
		    //判断是否加载
		    if(image != null && "".equals(image))
		    {
		    	UILUtils.displayImage(image, mImageView);
		    }
		
//		mImageView.setImageResource(resId);
		return view;
	}

}
