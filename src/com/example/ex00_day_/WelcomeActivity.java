package com.example.ex00_day_;

import android.app.Activity;

import com.example.ex00_day_con.PreferenceUtils;
import com.example.ex00_day_xinshouyindao.SplashActivity;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.os.Build;

public class WelcomeActivity extends Activity {
     
    
	private ImageView mImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		mImageView = (ImageView) findViewById(R.id.imageView1);
		Animation loadAnimation = AnimationUtils.loadAnimation(this, R.anim.huanyingjiemian);
		mImageView.startAnimation(loadAnimation);
		loadAnimation.setAnimationListener(new AnimationListener() {
			
			private Intent intent;

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				boolean play = isPlay(WelcomeActivity.this);
				if(play)
				{
					setisPlay(WelcomeActivity.this, false);
					 intent=new Intent(WelcomeActivity.this,SplashActivity.class);
					
				}else
				{
					
					intent=new Intent(WelcomeActivity.this,MainActivity.class);
				}
				startActivity(intent);
				finish();
			}
		});
	
//		
//		mImageView.postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				
//				boolean play = isPlay(WelcomeActivity.this);
//				Intent intent = new Intent();
//				if(play)
//				{
//					 intent = new Intent(WelcomeActivity.this,MainActivity.class);
//					
//				}else
//				{
//					 intent = new Intent(WelcomeActivity.this,SplashActivity.class);
//				}
//				
//				startActivity(intent);
//				finish();
//			}
//		}, 2000);
//		
//		boolean play = isPlay(this);
//		if(play)
//		{
//			
//			Intent intent=new Intent(this,SplashActivity.class);
//			startActivity(intent);
//			
//		}else
//		{
//			
//			Intent intent=new Intent(this,MainActivity.class);
//			startActivity(intent);
//		}
		
		
	}

	
	
	public static void setisPlay(Context context, boolean isPlay) {
		SharedPreferences sp = context.getSharedPreferences("huanying", 0);
		sp.edit().putBoolean("isPlay", isPlay).commit();
	}
	public static boolean isPlay(Context context) {
		SharedPreferences sp = context.getSharedPreferences("huanying", 0);
		boolean isNoPic = sp.getBoolean("isPlay", false);
		return isNoPic;
	}
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
