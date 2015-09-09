package com.fqy.fqylibs.model;

import java.io.Serializable;

/**
 * 广告信息类
 * 
 * @Title: ADModel.java
 * @Package com.fqy.fqylibs.activity.model
 * @Description: TODO 欢迎页
 * @author: Fang Qingyou
 * @date 2015年7月22日下午2:45:57
 * @version V1.0
 */
public class ADModel implements Serializable {

	private String adImage;
	private String adTitle;

	public String getAdImage() {
		return adImage;
	}

	public void setAdImage(String adImage) {
		this.adImage = adImage;
	}

	public String getAdTitle() {
		return adTitle;
	}

	public void setAdTitle(String adTitle) {
		this.adTitle = adTitle;
	}

}
