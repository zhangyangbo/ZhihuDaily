package com.example.ex00_day_;

import java.util.List;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.ex00_day_Mole.Story;
import com.example.ex00_day_Mole.StoryImages;

public class DBUtils {
	
	//��ѯ
	public static boolean hasColleted(Story story)
	{
		Story storycollet = new Select().from(Story.class).where("story_id = ?",story.getSroryId()).executeSingle();
	    return storycollet != null;
	}
	
	//��ѯ����   ���浽����
	public static List<Story> queyrhasColleted()
	{
		List<Story> data = new Select().from(Story.class).execute();
		
		//ͼƬ��ѯ
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
	
	
	//��ѯ
	public static void MinhasColleted(Story story)
	{
		Story storycollet = new Select().from(Story.class).where("story_id = ?",story.getSroryId()).executeSingle();
	
	}
	
	
	
	//�ղ�
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
	//ɾ��
	public static void delCollect(Story story)
	{
		new Delete().from(Story.class).where("story_id = ?",story.getSroryId()).execute();
	}
	
	
	
	

}
