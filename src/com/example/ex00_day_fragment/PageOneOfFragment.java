package com.example.ex00_day_fragment;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.example.ex00_day_.ParticularsDetailsActivity;
import com.example.ex00_day_.R;
import com.example.ex00_day_Mole.ImageUtils;
import com.example.ex00_day_Mole.Story;
import com.example.ex00_day_Mole.TopStory;
import com.example.ex00_day_Mole.ZhihuLatest;
import com.example.ex00_day_con.Constants;
import com.hp.hpl.sparta.xpath.PositionEqualsExpr;
import com.tencent.a.a.a.a;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.UILUtils;
import com.xinbo.utils.VolleyListener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class PageOneOfFragment extends Fragment {

	private List<Story> mData = new ArrayList<Story>();
	private View view;
	private ListView mListView;
	private View headerView;
	private static final int TIME = 2000;
	private boolean isManualSlide ;
	private boolean PageOneOfisVisible = true ; 
	private ViewPager mPager;
	private LayoutInflater inflater;
	private List<TopStory> topStories;
	private LatestAdapter adapter;
	private int position;
	
	public PageOneOfFragment(int position) {
		this.position = position;
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//第一次创建需要被执行，否则不执行
		PageOneOfisVisible = true;
		if(view == null)
		{
			initList(inflater, container);
			JSONurl();
		}
		return view;
	}

	private void initList(final LayoutInflater inflater, ViewGroup container) {
		view = inflater.inflate(R.layout.fragment_page_one_of, container, false);
		mListView = (ListView) view.findViewById(R.id.listView1);
		headerView = inflater.inflate(R.layout.header, null);
		//得到数据在初始化
//		initBanner();
		mListView.addHeaderView(headerView);
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
	
	
	private void initBanner() {
		mPager = (ViewPager) headerView.findViewById(R.id.Viewpager);
		//获得片段管理器
		FragmentManager fm = getChildFragmentManager();
		
		mPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			
			@Override
			public int getCount() {
				return  topStories.size() * 10000;
			}
			
			@Override
			public Fragment getItem(int position) {
				//解决数组越界超出
				int index = position % topStories.size();
				TopStory topStory = topStories.get(index);
				return new ImageResFragment(topStory,index);
			}
		});
		//自动滚动
		autoScroll();
		//Pager监听事件
		PagerMonitor();
	}

	private void PagerMonitor() {
		//监听是否手动滑动
		mPager.addOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				
			}
			
			@Override                                //arg1：滑动比例     arg2：滑动像素
			public void onPageScrolled(int position, float arg1, int arg2) {
				
			}
			
			@Override   //state ： 状态
			public void onPageScrollStateChanged(int state) {
				switch (state) {
				     //手动拖拽
				case ViewPager.SCROLL_STATE_DRAGGING:
					isManualSlide = true;
					break;
					//复位
				case ViewPager.SCROLL_STATE_SETTLING:
					isManualSlide = false;
					break;
					//空闲
				case ViewPager.SCROLL_STATE_IDLE:
					isManualSlide = false;
					break;

				default:
					break;
				}
			}
		});
	}

	private void autoScroll() 
	{
		mPager.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if(!isManualSlide && PageOneOfisVisible)
				{
					//让ViewPager 自动下一页
					int item = mPager.getCurrentItem() + 1;
					mPager.setCurrentItem(item);
				}
				mPager.postDelayed(this, TIME);
			}
		}, TIME);
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		PageOneOfisVisible = false;
	}
	
	private void JSONurl() {
//	String url = "http://192.168.56.1:8080/zhihu_latest.txt";
	HTTPUtils.get(getContext(), Constants.URL.BEFORE[position], new VolleyListener() {
		@Override
		public void onResponse(String json) {
			ZhihuLatest parseJSON = GsonUtils.parseJSON(json, ZhihuLatest.class);
			List<Story> stories = parseJSON.getStories();
			
			
//			//一旦获得banner数据  立即初始化
			topStories = parseJSON.getTopStories();
			if(topStories == null || topStories.size() == 0)
			{
			   mListView.removeHeaderView(headerView);
			}else
			{
//			//轮播图数据 //得到数据在初始化
				initBanner();
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
	
	private final class LatestAdapter extends BaseAdapter {
		private final LayoutInflater inflater;

		private LatestAdapter(LayoutInflater inflater) {
			this.inflater = inflater;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View layout = inflater.inflate(R.layout.listview_text, null);
			TextView tvTitle = (TextView) layout.findViewById(R.id.tv_title);
			ImageView imgIcon = (ImageView) layout.findViewById(R.id.img_icon);
			Story story = mData.get(position);
			tvTitle.setText(story.getTitle());
//			UILUtils.displayImage(story.getImages().get(0), imgIcon);
			//在设置下判断无图模式的时候使用
			ImageUtils.displayImage(getContext(), story.getImages().get(0), imgIcon);
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