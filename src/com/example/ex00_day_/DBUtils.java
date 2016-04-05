package com.example.ex00_day_;

import java.util.List;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.ex00_day_Mole.Story;
import com.example.ex00_day_Mole.StoryImages;

public class DBUtils {
	
	//查询
	public static boolean hasColleted(Story story)
	{
		Story storycollet = new Select().from(Story.class).where("story_id = ?",story.getSroryId()).executeSingle();
	    return storycollet != null;
	}
	
	//查询所有   保存到容器
	public static List<Story> queyrhasColleted()
	{
		List<Story> data = new Select().from(Story.class).execute();
		
		//图片查询
		for (int i = 0; i < data.size(); i++) {
			Story story = data.get(i);
			List<String> images = story.getImages();
			StoryImages storycollet = new Select().from(StoryImages.class).where("story_id = ?",story.getSroryId()).executeSingle();
			String imgurl = storycollet.getImgurl();
			if(imgurl != null && !"".equals(imgurl))
			{
				images.add(imgurl);
			}
			
		}
		return data;
	}
	
	
	//查询
	public static void MinhasColleted(Story story)
	{
		Story storycollet = new Select().from(Story.class).where("story_id = ?",story.getSroryId()).executeSingle();
	
	}
	
	
	
	//收藏
	public static void collect(Story story)
	{
		story = story.clone();
		story.save();
		List<String> listimages = story.getImages();
		if(listimages.size() > 0 )
		{
			StoryImages storyImages = new StoryImages();
			storyImages.setImgurl(listimages.get(0));
			storyImages.setId(story.getSroryId());
			storyImages.save();
		}
	}
	//删除
	public static void delCollect(Story story)
	{
		new Delete().from(Story.class).where("story_id = ?",story.getSroryId()).execute();
	}
	
	
	
	

}
