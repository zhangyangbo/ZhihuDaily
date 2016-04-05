package com.example.ex00_day_con;

public class Constants {
//	//模拟网址
	public static final class URL{
		//hfs链接网址
		public static final String HOST= "http://192.168.56.1:8080/Zhihu_Server/";
		//最新资讯地址
		public static final String LATEST= HOST + "latest.txt";
		//昨天资讯地址
		public static final String BEFORE1= HOST + "before0926.txt";
		public static final String BEFORE2= HOST + "before0927.txt";
		public static final String BEFORE3= HOST + "before0928.txt";
		public static final String BEFORE4= HOST + "before0929.txt";
		public static final String[] BEFORE = new String[]{
				LATEST,
				BEFORE1,
				BEFORE2,
				BEFORE3,
				BEFORE4,
		};
		
		public static final String THEMES_CONTENT= HOST + "themes_content.txt";
		
	}
	
	
//	//真实网址
//	public static final class URL{
//		//hfs链接网址
//		public static final String HOST= "http://new-at.zhihu.com/api/4/";
//		//最新资讯地址
//		public static final String LATEST= HOST + "latest.txt";
//		//昨天资讯地址
//		public static final String BEFORE1= HOST + "news/before/20161111";
//		public static final String BEFORE2= HOST + "news/before/20161112";
//		public static final String BEFORE3= HOST + "news/before/20161113";
//		public static final String BEFORE4= HOST + "news/before/20161114";
//		public static final String[] BEFORE = new String[]{
//				LATEST,
//				BEFORE1,
//				BEFORE2,
//				BEFORE3,
//				BEFORE4,
//		};
//		
//		
//	}
	
	


}
