/**
 * 
 */
package com.epam.news.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "NEWS")
public class News implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ")
	@SequenceGenerator(name = "SEQ", sequenceName = "NEWS_SEQ")
	@Column(name = "ID")
	private int id;
	
	@Column(name = "TITLE", length = 100)
	private String title;
	
	@Column(name = "POST_DATE")
	private Date postDate;
	
	@Column(name = "BRIEF", length = 500)
	private String brief;
	
	@Column(name = "CONTENT", length = 1000)
	private String content;
	
	private String displayPostDate;
	
	
	public News() {
		
	}
	
	public int getId() {
		return id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brief == null) ? 0 : brief.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result
				+ ((displayPostDate == null) ? 0 : displayPostDate.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((postDate == null) ? 0 : postDate.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		News other = (News) obj;
		if (brief == null) {
			if (other.brief != null)
				return false;
		} else if (!brief.equals(other.brief))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (displayPostDate == null) {
			if (other.displayPostDate != null)
				return false;
		} else if (!displayPostDate.equals(other.displayPostDate))
			return false;
		if (id != other.id)
			return false;
		if (postDate == null) {
			if (other.postDate != null)
				return false;
		} else if (!postDate.equals(other.postDate))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	
	
	
	
}
