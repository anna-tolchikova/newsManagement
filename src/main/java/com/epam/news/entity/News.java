/**
 * 
 */
package com.epam.news.entity;

import java.sql.Date;


/**
 * @author Hanna_Tolchykava
 *
 */
public class News{
	
	private int id;
	private String title;
	private Date postDate;
	private String brief;
	private String content;
	
	private String displayPostDate;
	
	public int getId() {
		return id;
	}
	
	public News() {
		super();
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDisplayPostDate() {
		return displayPostDate;
	}

	public void setDisplayPostDate(String displayPostDate) {
		this.displayPostDate = displayPostDate;
	}
	
	
	
	
	
}
