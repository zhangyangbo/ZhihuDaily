package com.example.ex00_day_;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.example.ex00_day_Mole.Story;
import com.example.ex00_day_Mole.TopStory;
import com.example.ex00_day_Mole.ZhihuLatest;
import com.example.ex00_day_con.Constants;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.UILUtils;
import com.xinbo.utils.VolleyListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class WodeshoucangFragment extends Fragment {

	private View view;
	private ListView mListView;
	private View headerView;
//	private  ActionBar mActionBar;
	private List<TopStory> topStories;
	private List<Story> mData = new ArrayList<Story>();
	private LatestAdapter adapter;
	private List<Story> list;

	public WodeshoucangFragment() {
		// Required empty public constructor
	}

	
	@Override
	public void onResume() {
		super.onResume();
		//重新查询数据库，并刷新
		initData();
//		mActionBar.setTitle(list.size()+" "+"条收藏");
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		if(view == null)
		{
			initList(inflater, container);
			initData();
		}
		return view;
	}

	private void initList(final LayoutInflater inflater, ViewGroup container) {
		view = inflater.inflate(R.layout.fragment_page_one_of, container, false);
		mListView = (ListView) view.findViewById(R.id.listView1);
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
	
	
	private void initData() {
		//把查询的所有数据保存到容器
		
		list = DBUtils.queyrhasColleted();
		
		//因为要重新刷新所有要先清空容器
		mData.clear();
		mData.addAll(list);
		adapter.notifyDataSetChanged();
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
			
			//有些行数据没有图片地址
			List<String> images = story.getImages();
			if(images == null || images.size() == 0)
			{
				//没有的话设置为隐藏不占布局
			     imgIcon.setVisibility(View.GONE);	
			}else
			{
				imgIcon.setVisibility(View.VISIBLE);
				UILUtils.displayImage(images.get(0), imgIcon);
				
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
