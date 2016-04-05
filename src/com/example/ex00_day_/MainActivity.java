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
          //��ȡ�����isDark
      		boolean isDark = readMode();
      		//�����ʱ������ж�
      		if(isDark)
      		{
      			//�����иı�����
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
		//��ַ
		String url = "http://192.168.56.1:8080/Zhihu_Server/themes.txt";
		//HTTP����
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				//������ݺ�
				Themes themes = GsonUtils.parseJSON(arg0, Themes.class);
				//�������������
				mDataOther.addAll(themes.getOthers());
				//֪ͨ����
				myAdapter.notifyDataSetChanged();
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	//������
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
        
        //����ҪView
        View wodeshoucang = headerView.findViewById(R.id.Layout_wodeshoucang);
        wodeshoucang.setOnClickListener(this);
        View lixianxiazai = headerView.findViewById(R.id.Layout_lixianxiazai);
        lixianxiazai.setOnClickListener(this);
        //����headerView����ֱ�ӵ��
        mDrawer.addHeaderView(headerView,null,false);
        mDrawer.addHeaderView(headerViewShouye);
//        mDrawer.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerLayout.setDrawerListener(new DemoDrawerListener());

        mDrawerLayout.setDrawerTitle(GravityCompat.START, getString(R.string.drawer_title));
        
        //�޸Ĳ໬�˵�����
        myAdapter = new MyAdapter();
        mDrawer.setAdapter(myAdapter);
        
//        mDrawer.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
//                Shakespeare.TITLES));
        //�໬���ֵĵ���¼�
        mDrawer.setOnItemClickListener(new DrawerItemClickListener());
        //����ListView�ĵ����ɫ�¼�
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
			Toast.makeText(this, "�ҵ��ղ�", Toast.LENGTH_SHORT).show();
		    //ֻ�����½����ҵ��ղ�ҳ����и�ֵ
			List<Story> list = DBUtils.queyrhasColleted();
			mActionBar.setTitle(list.size()+" "+"���ղ�");
			changFragment(new WodeshoucangFragment());
			break;
		case R.id.Layout_lixianxiazai:
			Toast.makeText(this, "�ҵ��ղ�", Toast.LENGTH_SHORT).show();
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
    	//�˵����ѡ��
//    	menu.add( 0 , 1 , 0 , "�л�ģʽ");
//    	menu.add( 0 , 2 , 0 , "����");
    	getMenuInflater().inflate(R.menu.main, menu);
    	return super.onCreateOptionsMenu(menu);
    }
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       
    	switch (item.getItemId()) {
		case R.id.action_dark_mode:
			boolean isDark = readMode();
			//��ÿ�ε�����б���
			saveMode(!isDark);
			finish();
			//ȡ���ر�ҳ��Ķ���
			overridePendingTransition(0, 0);
			//��ת
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
    
    @Override   //�ڲˆγ��F֮ǰ
    public boolean onPrepareOptionsMenu(Menu menu) {
    	MenuItem item = menu.getItem(1);
		boolean isDark = readMode();
		if (isDark) {
			item.setTitle("�ռ�ģʽ");
		} else {
			item.setTitle("ҹ��ģʽ");
		}
		return super.onPrepareOptionsMenu(menu);
    }

    
    //��
   	private boolean readMode() {
   		
   		SharedPreferences sp = getSharedPreferences("setting", 0);
   		boolean isDark = sp.getBoolean("dark_mode",false);
   		return isDark;
   	}



   	//д
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

        	//�໬�˵�����¼��ľɷ���
        	//        	jiufangfa(position);
        	
        	//������ 1 ��ʱ�������ҳ
        	if(position == 1)
        	{
        		//ΪActionBar��ֵ
        		mActionBar.setTitle("��ҳ");
        		//��ת����ҳ
        		changFragment(new BlankFragment());
        		return;
        	}
        	//������HeaderView ��Ӱ��position ��ֵ
        	
        	//��õ�ǰposition��Ӧ��ֵ
        	position -= mDrawer.getHeaderViewsCount();
        	Other other = mDataOther.get(position);
        	//tietle ��ֵ
        	mActionBar.setTitle(other.getName());
        	//��ת
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

    //��תƬ�εķ���
    public void changFragment(Fragment fragment)
    {
    	mfragment = fragment;
    	//���û����仰����໬�˵�֮�󣬲໬�˵������Զ���ʧ
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
        	//������getSupportActionBar() ֮ǰȥ����toolbar
        	Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            mActionBar = getSupportActionBar();
            mActionBar.setTitle("֪���ձ�");
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
