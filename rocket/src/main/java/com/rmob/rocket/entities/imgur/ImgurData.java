package com.rmob.rocket.entities.imgur;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ImgurData {

	private String id;
	private String title;
	private String description;
	private long datetime;
	private String type;
	private boolean animated;
	private int width;
	private int height;
	private int size;
	private int views;
	private int bandwidth;
	private Object vote;
	private boolean favorite;
	private Object nsfw;
	private Object section;
	private Object accountUrl;
	private int accountId;
	private boolean isAd;
	private boolean inMostViral;
	private String[] tags;
	private int adType;
	private String adUrl;
	private boolean inGallery;
	private String deletehash;
	private String name;
	private String link;
}
