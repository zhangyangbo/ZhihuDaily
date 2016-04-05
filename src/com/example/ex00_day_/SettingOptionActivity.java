package com.example.ex00_day_;

import java.io.File;

import com.example.ex00_day_con.PreferenceUtils;
import com.tencent.a.b.m;
import com.xinbo.utils.FileUtils;
import com.xinbo.utils.StorageUtils;

import android.R.string;
import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class SettingOptionActivity extends AppCompatActivity {
	
	

	private  ActionBar mActionBar;
	private CheckBox mCheckBigText;
	private CheckBox cb_lixianxiazai;
	private CheckBox cb_nopic;
	private CheckBox cb_tuisongxiaoxi;
	private CheckBox cb_fenxiangweibo;
	private View mLayoutBigText;
	private View mLayoutLixianxiazai;
	private View mLayoutNopic;
	private View mLayoutTuisongxiaoxi;
	private View mLayoutFenxiangweibo;
	private View mLayoutQingchuhuancun;
	private File externalCacheDir;
	private File cacheDir;
	private TextView tv_kb;
	private View mLayoutJianchagengengxin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settingoption);
		initUI();
		
	}

	private void initUI() {
		initToolbar();
		//�ҿؼ�
		initData();
		
		// �ȳ�ʼ��checkbox
		boolean bigText = PreferenceUtils.isBigText(this);
		boolean isNoPic = PreferenceUtils.isNoPic(this);
		
		mCheckBigText.setChecked(bigText);
		cb_nopic.setChecked(isNoPic);
		
		mCheckBigText.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				PreferenceUtils.setBigText(SettingOptionActivity.this,isChecked);
			}
		});
		
		cb_nopic.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				PreferenceUtils.setNoPic(SettingOptionActivity.this, isChecked);
			}
		});
		
		intilayout();
		//�л������С
		mLayoutBigText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean checked = mCheckBigText.isChecked();
				mCheckBigText.setChecked(!checked);
			}
		});
		
		//�л���ͼģʽ    
		mLayoutNopic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean checked = cb_nopic.isChecked();
				cb_nopic.setChecked(!checked);
				
			}
		});
		
//	    FileUtils.size(StorageUtils.FILE_ROOT);
		//�������
		mLayoutQingchuhuancun.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			    FileUtils.delFilesFromPath(externalCacheDir);
			    FileUtils.delFilesFromPath(cacheDir);
			    String size = queryCache();
			    tv_kb.setText(size);
			}
		});
		
		//������
		mLayoutJianchagengengxin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					//��ð�������
					PackageManager manager = getPackageManager();
					//��ð�����Ϣ
					PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
					//��ð汾��
					int versionCode = info.versionCode;
					//��ð汾��Ϣ
					String name = info.versionName;
					//ģʽ�ӷ������л�õİ汾�� 2.5
					double  newVesion = 2.5;
					if(versionCode < newVesion)
					{
						//���� Dialog����
						Toast.makeText(SettingOptionActivity.this, "������°汾", Toast.LENGTH_SHORT).show();
					}
					
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		
	}

	private void intilayout() {
		mLayoutBigText = findViewById(R.id.layout_bigtext);
		mLayoutLixianxiazai = findViewById(R.id.layout_lixianxiazai);
		mLayoutNopic = findViewById(R.id.layout_nopic);
		mLayoutTuisongxiaoxi = findViewById(R.id.layout_tuisongxiaoxi);
		mLayoutFenxiangweibo = findViewById(R.id.layout_fenxiangweibo);
		mLayoutQingchuhuancun = findViewById(R.id.layout_qingchuhuancun);
		mLayoutJianchagengengxin = findViewById(R.id.layout_jianchagengengxin);
	}

	private void initData() {
		mCheckBigText = (CheckBox) findViewById(R.id.check_bigtext);
		cb_lixianxiazai = (CheckBox) findViewById(R.id.cb_lixianxiazai);
		cb_nopic = (CheckBox) findViewById(R.id.cb_nopic);
		cb_tuisongxiaoxi = (CheckBox) findViewById(R.id.cb_tuisongxiaoxi);
		cb_fenxiangweibo = (CheckBox) findViewById(R.id.cb_fenxiangweibo);
		tv_kb = (TextView) findViewById(R.id.tv_kb);
		
	}

	private void initToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle("����");
	
		
	}
	//��ջ���
	private String queryCache()
	{
		externalCacheDir = getExternalCacheDir();
		cacheDir = getCacheDir();
		long fileLen1 = FileUtils.getFileLen(externalCacheDir);
		long fileLen2 = FileUtils.getFileLen(cacheDir);
		long fileLen = fileLen1+fileLen2;
		String size = FileUtils.size(fileLen);
		return size;
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting_option, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
}
