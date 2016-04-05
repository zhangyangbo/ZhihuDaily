package com.example.ex00_day_fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.example.ex00_day_.CircleImageView;
import com.example.ex00_day_.ParticularsDetailsActivity;
import com.example.ex00_day_.R;
import com.example.ex00_day_.R.layout;
import com.example.ex00_day_Mole.Editor;
import com.example.ex00_day_Mole.ImageUtils;
import com.example.ex00_day_Mole.Other;
import com.example.ex00_day_Mole.Story;
import com.example.ex00_day_Mole.ThemesContent;
import com.example.ex00_day_Mole.ZhihuLatest;
import com.example.ex00_day_con.Constants;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.UILUtils;
import com.xinbo.utils.VolleyListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class UserRecommFragment extends Fragment {

	private View view;
	private List<Story> mData = new ArrayList<Story>();
	private ListView mListView;
	private LatestAdapter adapter;
	private View headerView;
	private Other other;
	private LayoutInflater inflater;
	
	
	public UserRecommFragment(Other other) {
		this.other = other;
		
	}

	




	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		this.inflater = inflater;
		
		//第一次创建的时候需要执行，否则就不执行
		if(view == null)
		{
			initListView(inflater, container);
			JsonUrl();
			
		}
		return  view;
	}

	private void JsonUrl() {
//		String url = "http://192.168.56.1:8080/Zhihu_Server/themes_content.txt";
		HTTPUtils.get(getContext(), Constants.URL.THEMES_CONTENT , new VolleyListener() {
			@Override
			public void onResponse(String json) {
				ThemesContent parseJSON = GsonUtils.parseJSON(json, ThemesContent.class);
				List<Story> stories = parseJSON.getStories();
				List<Editor> editors = parseJSON.getEditors();
				
				//找到线性布局
				LinearLayout mLinear = (LinearLayout) headerView.findViewById(R.id.Lin);
			   for (int i = 0; i < editors.size(); i++) {
				   View chid =inflater.inflate(R.layout.img_time, null);
				   CircleImageView img_zhubian = (CircleImageView) chid.findViewById(R.id.circleImageView1);
//				   ImageView img_zhubian = (ImageView) chid.findViewById(R.id.ima_zhubian);
				   mLinear.addView(chid);
				   Editor editor = editors.get(i);
//				   //缓存图片
				   UILUtils.displayImageNoAnim(editor.getAvatar(), img_zhubian);
				   
			}
				 //在容器添加内容
				mData.addAll(stories);
				
				
				
				//通知适配器进行更新
				adapter.notifyDataSetChanged();
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				
			}
		});
		
	    }
		
	

	private void initListView(final LayoutInflater inflater, ViewGroup container) {
		view = inflater.inflate(R.layout.fragment_page_one_of, container, false);
		mListView = (ListView) view.findViewById(R.id.listView1);

		headerView = inflater.inflate(R.layout.ueser_header, null);
	    initHeaderView();
	    mListView.addHeaderView(headerView,null,false);
		adapter = new LatestAdapter(inflater);
		mListView.setAdapter(adapter);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getContext(),ParticularsDetailsActivity.class);
				position -= mListView.getHeaderViewsCount();
				Story story = mData.get(position);
				//要记得在Story实现序列化借口 不然值不能传递
				intent.putExtra("story", story);
				startActivity(intent);
				
			}
		});
		
	}
	
	
	
	private void initHeaderView() {
		ImageView ima_logo = (ImageView) headerView.findViewById(R.id.ima_logo);
		TextView ima_tv = (TextView) headerView.findViewById(R.id.tv_ima_text);
		ima_tv.setText(other.getDescription());
		UILUtils.displayImage(other.getThumbnail(),ima_logo);
	}

	private View layout;
	private WebView mWebView;


	private final class LatestAdapter extends BaseAdapter {
		private final LayoutInflater inflater;

		private LatestAdapter(LayoutInflater inflater) {
			this.inflater = inflater;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			layout = inflater.inflate(R.layout.listview_text, null);
			TextView tvTitle = (TextView) layout.findViewById(R.id.tv_title);
			ImageView imgIcon = (ImageView) layout.findViewById(R.id.img_icon);
			Story story = mData.get(position);
			tvTitle.setText(story.getTitle());
			List<String> images = story.getImages();
			
			
			//有些地址没有图像  进行判断
			if(images == null || images.size() == 0)
			{  
				imgIcon.setVisibility(View.GONE);
			}else{
//				                      //没有动画的方法
//				UILUtils.displayImageNoAnim(images.get(0), imgIcon);
				 //在设置下判断无图模式的时候使用
				ImageUtils.displayImage(getContext(), story.getImages().get(0), imgIcon);
				
			}
				
			return layout;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getCount() {
			return mData.size();
		}
	}

}
