package com.example.ex00_day_;



import java.util.ArrayList;
import java.util.List;

import com.activeandroid.Model;
import com.android.volley.VolleyError;
import com.example.ex00_day_.MainActivity.MyAdapter;
import com.example.ex00_day_Mole.Other;
import com.example.ex00_day_Mole.Story;
import com.example.ex00_day_Mole.Themes;
import com.example.ex00_day_con.ActionBarTitle;
import com.example.ex00_day_fragment.BlankFragment;
import com.example.ex00_day_fragment.SeeFragment;
import com.example.ex00_day_fragment.UserRecommFragment;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.opengl.EGLExt;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements OnClickListener{
	private DrawerLayout mDrawerLayout;
    private ListView mDrawer;
    private View headerView;
    private TextView mContent;

    private ActionBarHelper mActionBar;

    private ActionBarDrawerToggle mDrawerToggle;
	private MyAdapter myAdapter;
	private List<Other> mDataOther = new ArrayList<Other>();
	private View headerViewShouye;
	public Fragment mfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          //获取保存的isDark
      		boolean isDark = readMode();
      		//登入的时候进行判断
      		if(isDark)
      		{
      			//代码中改变主题
      			setTheme(R.style.dark);
      			
      		}else{
      			setTheme(R.style.light);
      			
      		}
        setContentView(R.layout.activity_main);
        initUI();
        initFragment();
        intiData();
    }
    
   
    
    
    

	private void intiData() {
		//网址
		String url = "http://192.168.56.1:8080/Zhihu_Server/themes.txt";
		//HTTP请求
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				//获得数据后
				Themes themes = GsonUtils.parseJSON(arg0, Themes.class);
				//往容器添加内容
				mDataOther.addAll(themes.getOthers());
				//通知更新
				myAdapter.notifyDataSetChanged();
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	//适配器
	class MyAdapter extends BaseAdapter
	{

		private Other other;

		@Override
		public int getCount() {
			return mDataOther.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = getLayoutInflater().inflate(R.layout.list_item_menu, null);
			TextView chtv_title = (TextView) view.findViewById(R.id.tv_title);
			ImageView chima_logo = (ImageView) view.findViewById(R.id.ima_logo);
			other = mDataOther.get(position);
			
			chtv_title.setText(other.getName());
			return view;
		}
		
	}
	

	private void initFragment() {
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.zhen, new BlankFragment()).commit();
	}

	private void initUI() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer = (ListView) findViewById(R.id.start_drawer);
        headerView = getLayoutInflater().inflate(R.layout.menuheader, null);
        headerViewShouye = getLayoutInflater().inflate(R.layout.headerviewshouye, null);
//        headerView.setOnClickListener(null);
        
        //属性要View
        View wodeshoucang = headerView.findViewById(R.id.Layout_wodeshoucang);
        wodeshoucang.setOnClickListener(this);
        View lixianxiazai = headerView.findViewById(R.id.Layout_lixianxiazai);
        lixianxiazai.setOnClickListener(this);
        //进行headerView不可直接点击
        mDrawer.addHeaderView(headerView,null,false);
        mDrawer.addHeaderView(headerViewShouye);
//        mDrawer.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerLayout.setDrawerListener(new DemoDrawerListener());

        mDrawerLayout.setDrawerTitle(GravityCompat.START, getString(R.string.drawer_title));
        
        //修改侧滑菜单布局
        myAdapter = new MyAdapter();
        mDrawer.setAdapter(myAdapter);
        
//        mDrawer.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
//                Shakespeare.TITLES));
        //侧滑布局的点击事件
        mDrawer.setOnItemClickListener(new DrawerItemClickListener());
        //设置ListView的点击变色事件
        mDrawer.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        mActionBar = createActionBarHelper();
        mActionBar.init();

        // ActionBarDrawerToggle provides convenient helpers for tying together the
        // prescribed interactions between a top-level sliding drawer and the action bar.
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close);
       
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Layout_wodeshoucang:
			Toast.makeText(this, "我的收藏", Toast.LENGTH_SHORT).show();
		    //只有重新进入我的收藏页面才有赋值
			List<Story> list = DBUtils.queyrhasColleted();
			mActionBar.setTitle(list.size()+" "+"条收藏");
			changFragment(new WodeshoucangFragment());
			break;
		case R.id.Layout_lixianxiazai:
			Toast.makeText(this, "我的收藏", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
		
	}
	
	
	
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mDrawerToggle.syncState();
    }
  
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	//菜单添加选项
//    	menu.add( 0 , 1 , 0 , "切换模式");
//    	menu.add( 0 , 2 , 0 , "设置");
    	getMenuInflater().inflate(R.menu.main, menu);
    	return super.onCreateOptionsMenu(menu);
    }
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       
    	switch (item.getItemId()) {
		case R.id.action_dark_mode:
			boolean isDark = readMode();
			//把每次点击进行保存
			saveMode(!isDark);
			finish();
			//取消关闭页面的动画
			overridePendingTransition(0, 0);
			//跳转
			Intent intent = new Intent(this,MainActivity.class);
			startActivity(intent);
			break;
		case R.id.action_settings:
			Intent seeintent = new Intent(this,SettingOptionActivity.class);
			startActivity(seeintent);
			
			break;
		

		default:
			break;
		}
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override   //在菜單出現之前
    public boolean onPrepareOptionsMenu(Menu menu) {
    	MenuItem item = menu.getItem(1);
		boolean isDark = readMode();
		if (isDark) {
			item.setTitle("日间模式");
		} else {
			item.setTitle("夜间模式");
		}
		return super.onPrepareOptionsMenu(menu);
    }

    
    //读
   	private boolean readMode() {
   		
   		SharedPreferences sp = getSharedPreferences("setting", 0);
   		boolean isDark = sp.getBoolean("dark_mode",false);
   		return isDark;
   	}



   	//写
   	private void saveMode(boolean isDark) {
   		SharedPreferences sp = getSharedPreferences("setting", 0);
   		sp.edit().putBoolean("dark_mode", isDark).commit();
   	}
    
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * This list item click listener implements very simple view switching by changing
     * the primary content text. The drawer is closed when a selection is made.
     */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        	//侧滑菜单点击事件的旧方法
        	//        	jiufangfa(position);
        	
        	//当等于 1 的时候进入首页
        	if(position == 1)
        	{
        		//为ActionBar赋值
        		mActionBar.setTitle("首页");
        		//跳转到首页
        		changFragment(new BlankFragment());
        		return;
        	}
        	//增加了HeaderView 会影响position 的值
        	
        	//获得当前position对应的值
        	position -= mDrawer.getHeaderViewsCount();
        	Other other = mDataOther.get(position);
        	//tietle 赋值
        	mActionBar.setTitle(other.getName());
        	//跳转
        	changFragment(new UserRecommFragment(other));
        	
        	
        	
        }
        

//		private void jiufangfa(int position) {
//			mDrawerLayout.closeDrawer(mDrawer);
//        	position -= 1;
//            mActionBar.setTitle(ActionBarTitle.TITLES[position]);
//            switch (position) {
//			case 0:
//				getSupportFragmentManager().beginTransaction()
//				.replace(R.id.zhen, new BlankFragment()).commit();
//				break;
//			case 1:
//				getSupportFragmentManager().beginTransaction()
//				.replace(R.id.zhen, new UserRecommFragment() ).commit();
//				
//				break;
//			case 2:
//				getSupportFragmentManager().beginTransaction()
//				.replace(R.id.zhen, new SeeFragment()).commit();
//				break;
//
//			default:
//				break;
//			}
//		}
    }

    //跳转片段的方法
    public void changFragment(Fragment fragment)
    {
    	mfragment = fragment;
    	//如果没有这句话点击侧滑菜单之后，侧滑菜单不会自动消失
    	mDrawerLayout.closeDrawer(mDrawer);
    	getSupportFragmentManager().beginTransaction()
    	.replace(R.id.zhen, mfragment ).commit();
    }
    
    /**
     * A drawer listener can be used to respond to drawer events such as becoming
     * fully opened or closed. You should always prefer to perform expensive operations
     * such as drastic relayout when no animation is currently in progress, either before
     * or after the drawer animates.
     *
     * When using ActionBarDrawerToggle, all DrawerLayout listener methods should be forwarded
     * if the ActionBarDrawerToggle is not used as the DrawerLayout listener directly.
     */
    private class DemoDrawerListener implements DrawerLayout.DrawerListener {
        @Override
        public void onDrawerOpened(View drawerView) {
            mDrawerToggle.onDrawerOpened(drawerView);
            mActionBar.onDrawerOpened();
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            mDrawerToggle.onDrawerClosed(drawerView);
            mActionBar.onDrawerClosed();
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            mDrawerToggle.onDrawerStateChanged(newState);
        }
    }

    /**
     * Create a compatible helper that will manipulate the action bar if available.
     */
    private ActionBarHelper createActionBarHelper() {
        return new ActionBarHelper();
    }

    /**
     * Action bar helper for use on ICS and newer devices.
     */
    private class ActionBarHelper {
        private final ActionBar mActionBar;
        private CharSequence mDrawerTitle;
        private CharSequence mTitle;

        ActionBarHelper() {
        	//必须在getSupportActionBar() 之前去设置toolbar
        	Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            mActionBar = getSupportActionBar();
            mActionBar.setTitle("知乎日报");
        }

        public void init() {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowHomeEnabled(false);
            mTitle = mDrawerTitle = getTitle();
        }

        /**
         * When the drawer is closed we restore the action bar state reflecting
         * the specific contents in view.
         */
        public void onDrawerClosed() {
            mActionBar.setTitle(mTitle);
        }

        /**
         * When the drawer is open we set the action bar to a generic title.
         * The action bar should only contain data relevant at the top level of
         * the nav hierarchy represented by the drawer, as the rest of your content
         * will be dimmed down and non-interactive.
         */
        public void onDrawerOpened() {
            mActionBar.setTitle(mDrawerTitle);
        }

        public void setTitle(CharSequence title) {
            mTitle = title;
        }
    }
 

	
}
