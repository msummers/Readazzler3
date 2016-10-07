package com.mikeco.readazzler.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;

@Entity
public class Entry {
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Category> categories = new HashSet<>();
	private String description;
	@ManyToOne
	private Feed feed;
	private String guid;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Boolean isRead = false;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Feed> likes = new HashSet<Feed>();

	private String link;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Media media;

	private Date pubDate; // Mon, 22 Aug 2016 11:20:33 -0700

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Entry> reblogs = new HashSet<Entry>();

	private String title;

	public Entry() {
	}

	public Entry(SyndEntry syndEntry, Media media, Feed feed) {
		this.setCategories(Category.fromList(syndEntry.getCategories()));
		this.setDescription(syndEntry.getDescription());
		this.setFeed(feed);
		this.setGuid(syndEntry.getUri());
		this.setLikes(syndEntry);
		this.setLink(syndEntry.getLink());
		this.setMedia(media);
		this.setPubDate(syndEntry.getPublishedDate());
		this.setReblogs(syndEntry);
		this.setTitle(syndEntry.getTitle());

	}

	private void setDescription(SyndContent content) {
		// TODO Auto-generated method stub

	}

	private void setReblogs(SyndEntry syndEntry) {
		// TODO Auto-generated method stub

	}

	private void setLikes(SyndEntry syndEntry) {
		// TODO Auto-generated method stub

	}

	public Set<Category> getCategories() {
		return categories = new HashSet<>();
	}

	public String getDescription() {
		return description;
	}

	public Feed getFeed() {
		return feed;
	}

	public String getGuid() {
		return guid;
	}

	public Long getId() {
		return id;
	}

	public Set<Feed> getLikes() {
		return likes;
	}

	public String getLink() {
		return link;
	}

	public Media getMedia() {
		return media;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public Set<Entry> getReblogs() {
		return reblogs;
	}

	public String getTitle() {
		return title;
	}

	public void setCategories(Set<Category> category) {
		this.categories = category;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFeed(Feed feed) {
		this.feed = feed;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLikes(Set<Feed> likes) {
		this.likes = likes;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public void setReblogs(Set<Entry> reblogs) {
		this.reblogs = reblogs;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
}
