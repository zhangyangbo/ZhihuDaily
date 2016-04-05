package com.example.ex00_day_;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.android.volley.VolleyError;
import com.example.ex00_day_Mole.Detail;
import com.example.ex00_day_Mole.Story;
import com.example.ex00_day_Mole.ZhihuLatest;
import com.example.ex00_day_con.Constants;
import com.example.ex00_day_con.PreferenceUtils;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;

import android.R.anim;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.TextSize;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ParticularsDetailsActivity extends AppCompatActivity implements OnClickListener {

	
	private ActionBar mActionBar;
	private WebView mWebView;
	private Story story;
	private ImageView ima_collet;

	private ImageView ima_praise;

	private ImageView ima_comment;

	private ImageView ima_share;

	private TextView tv_dianzan;

	private TextView tv_pinglun;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_particulars_details);
		initUI();
		initData();
	}

	
	private void initData() {
		Intent intent = getIntent();
		story = (Story) intent.getSerializableExtra("story");
		String url = Constants.URL.HOST + "detail.txt";
		//初始化按钮图标
		initCollect();
		
//				+story.getId();
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String json) {
				Detail parseJSON = GsonUtils.parseJSON(json, Detail.class);
				//根据传递过来的的地址去获取
				mWebView.loadDataWithBaseURL(null, parseJSON.getBody(), "text/html", "utf-8", null);
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				
			}
		});
		
	}


	private void initTooBar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.xiangqingtoolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle("");
        mActionBar.setDisplayShowHomeEnabled(true);
        //这句话为显示toolbar的图标
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(R.layout.menu_detail_toobar);
        tv_dianzan = (TextView) findViewById(R.id.tv_dianzan);
        
        tv_pinglun = (TextView) findViewById(R.id.tv_pinglun);
        
        
        //收藏图标
        ima_collet = (ImageView) findViewById(R.id.ima_collet);
        ima_collet.setOnClickListener(this);
        //点赞
        ima_praise = (ImageView) findViewById(R.id.ima_praise);
        ima_praise.setOnClickListener(this);
        //评论
        ima_comment = (ImageView) findViewById(R.id.ima_comment);
        ima_comment.setOnClickListener(this);
        //分享
        ima_share = (ImageView) findViewById(R.id.ima_share);
        ima_share.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ima_collet:
			//判断当个story是否已经被收藏
			//查询数据   查询需要添加无参的构造方法
			boolean hasColleted = DBUtils.hasColleted(story);
			
			if(hasColleted)
			{
				//取消收藏
				DBUtils.delCollect(story);
//			new Delete().from(Story.class).where("story_id = ?",story.getSroryId()).execute();
				ima_collet.setImageResource(R.drawable.collect);
				break;
				
			}else
			{
				//解决多次点击出现bug   赋值给新对象
//			Story storyNew = new Story(story.getImages(),story.getType(),story.getGaPrefix(),story.getTitle(),story.getSroryId());
				//克隆 ，解决无法收藏问题
				Story storyNew = story.clone();
				//收藏
				DBUtils.collect(storyNew);
				ima_collet.setImageResource(R.drawable.collected);
			}
			break;
		case R.id.ima_praise:
			Toast.makeText(this, "点赞", Toast.LENGTH_SHORT).show();
			
			tv_dianzan.setText("" + 1);
			break;
		case R.id.ima_comment:
			Toast.makeText(this, "评论", Toast.LENGTH_SHORT).show();
			tv_pinglun.setText("" + 1);
			break;
		case R.id.ima_share: 
			Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
			break;
			
		default:
			break;
		}
	}
	
	//c初始化按钮图标
	public void initCollect()
	{
		Story storycollet = new Select().from(Story.class).where("story_id = ?",story.getSroryId()).executeSingle();
		if(storycollet != null)
		{
			ima_collet.setImageResource(R.drawable.collected);
		}
	}
	
	
	private void initUI() {
		initTooBar();
		mWebView = (WebView) findViewById(R.id.webView1);
		WebSettings settings = mWebView.getSettings();
		settings.setJavaScriptEnabled(true);
		
		//判断当前状态如果是大字体就改变字体
		if (PreferenceUtils.isBigText(this)){
			settings.setTextSize(TextSize.LARGER);
		}

		
		
//		mWebView.loadUrl("http://192.168.56.1:8080/Zhihu_Server/");
		//防止跳出
		mWebView.setWebViewClient(new WebViewClient());
		mWebView.setWebChromeClient(new WebChromeClient());
		
	}
	
	



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		//点击详情页面 关闭
		if (id == android.R.id.home) {
//			return true;
			finish();
		}
		return super.onOptionsItemSelected(item);
	}


}
