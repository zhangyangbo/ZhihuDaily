package com.example.ex00_day_Mole;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//为了保存到数据库
@Table(name="story")
public class Story extends Model implements Serializable,Cloneable{
	
	@Override
	public Story clone(){
		// TODO Auto-generated method stub
		try {
			return (Story) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Expose
	@Column
    private List<String> images = new ArrayList<String>();
    @Expose
    @Column
    private Integer type;
    @Expose
    @Column(name = "story_id")
    private Integer id;
   
    
    public Story() {
		super();
	}

	@SerializedName("ga_prefix")
    @Expose
    @Column
    private String gaPrefix;
    @Expose
    @Column
    private String title;
    @Expose
    @Column
    private Boolean multipic;
    
   
	@Override
	public String toString() {
		return "Story [images=" + images + ", type=" + type + ", id=" + id + ", gaPrefix=" + gaPrefix + ", title="
				+ title + ", multipic=" + multipic + "]";
	}

	
	/**
     * 
     * @return
     *     The images
     */
    public List<String> getImages() {
        return images;
    }

    /**
     * 
     * @param images
     *     The images
     */
    public void setImages(List<String> images) {
        this.images = images;
    }

    /**
     * 
     * @return
     *     The type
     */
    public Integer getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 
     * 继承Model后编译出错，因为Model 有getId的方法，
     * 解决  修改方法名字
     */
    public Integer getSroryId() {
        return id;
    }

    /**
     * 继承Model后编译出错，因为
     */
    public void getSroryId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The gaPrefix
     */
    public String getGaPrefix() {
        return gaPrefix;
    }

    /**
     * 
     * @param gaPrefix
     *     The ga_prefix
     */
    public void setGaPrefix(String gaPrefix) {
        this.gaPrefix = gaPrefix;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The multipic
     */
    public Boolean getMultipic() {
        return multipic;
    }

    /**
     * 
     * @param multipic
     *     The multipic
     */
    public void setMultipic(Boolean multipic) {
        this.multipic = multipic;
    }

	public Story(List<String> images, Integer type, Integer id, String gaPrefix, String title, Boolean multipic) {
		super();
		this.images = images;
		this.type = type;
		this.id = id;
		this.gaPrefix = gaPrefix;
		this.title = title;
		this.multipic = multipic;
	}

}
