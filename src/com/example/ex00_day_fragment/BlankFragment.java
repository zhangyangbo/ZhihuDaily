package com.example.ex00_day_fragment;

import com.astuetz.PagerSlidingTabStrip;
import com.example.ex00_day_.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class BlankFragment extends Fragment {


	private PagerSlidingTabStrip tabs;
	private final String[] TITLES = { "������Ѷ", "������Ѷ", "ǰ����Ѷ", "������Ѷ", "��ʱ�ձ�" };
	private View view;
	
	public BlankFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_blank, null);
		initpager();
		return view;
	}
	
	private void initpager() {
		ViewPager  mPager = (ViewPager) view.findViewById(R.id.PageOne);
		//���Ƭ�ι�����
		FragmentManager fm = getChildFragmentManager();
		
		mPager.setAdapter(new FragmentPagerAdapter(fm) {
			       //��ñ���
			public CharSequence getPageTitle(int position) {
				return TITLES[position];
			}
			
			
			@Override  //����ҳ��
			public int getCount() {
				return TITLES.length;
			}
			
			@Override
			public Fragment getItem(int position) {
				
					return new PageOneOfFragment(position);
				
					
				
			}
		});
		//����tabs ������ mPager ֮�£���Ȼ���ָ���쳣
		tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
		tabs.setViewPager(mPager);
	}
	
	
	

}
