package com.mikeco.readazzler.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;

@Entity
public class Entry {

	@Lob
	private String description;

	@ManyToOne(cascade = { CascadeType.ALL })
	private Feed feed;

	private String guid;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Boolean isRead = false;

	@ManyToMany(cascade = { CascadeType.ALL })
	private Set<Feed> likes = new HashSet<Feed>();

	private String link;

	// TODO An Entry can have more than one media, so this needs to be ManyToMany
	@ManyToOne(cascade = { CascadeType.ALL })
	private Media media;

	private Date pubDate; // Mon, 22 Aug 2016 11:20:33 -0700

	// Where is this reblogged from? (immediate parent)
	@ManyToOne(cascade = { CascadeType.ALL })
	private Entry reblog;

	@OneToMany(mappedBy = "reblog", cascade = { CascadeType.ALL })
	private Set<Entry> reblogs = new HashSet<Entry>();

	private String title;

	public Entry() {
	}

	public Entry(SyndEntry syndEntry, Media media, Feed feed) {
		//this.setDescription(syndEntry.getDescription());
		this.setFeed(feed);
		this.setGuid(syndEntry.getUri());
		this.setLink(syndEntry.getLink());
		this.setMedia(media);
		media.getEntries()
			.add(this);
		media.setCategories(syndEntry);
		this.setPubDate(syndEntry.getPublishedDate());
		this.setTitle(syndEntry.getTitle());
		this.isRead = media.getIsRead();
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

	public Boolean getIsRead() {
		return isRead;
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

	public Entry getReblog() {
		return reblog;
	}

	public Set<Entry> getReblogs() {
		return reblogs;
	}

	public String getTitle() {
		return title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private void setDescription(SyndContent syndContent) {
		this.description = syndContent.getValue();
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

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public void setLikes(Set<Feed> likes) {
		this.likes = likes;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setMedia(Media media) {
		this.isRead = media.getIsRead();
		this.media = media;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public void setReblog(Entry reblog) {
		this.reblog = reblog;
	}

	public void setReblogs(Set<Entry> reblogs) {
		this.reblogs = reblogs;
	}

	public void setTags(Set<Tag> tags) {
		// TODO setting tags should trigger finding likes & reblogs
		this.media.setTags(tags);
		if(!tags.isEmpty()){
			this.getLikes().addAll(this.findLikes());
			this.getReblogs().addAll(this.findReblogs());
		}
	}

	public Collection<? extends Entry> findReblogs() {
		// TODO find Reblogs associated with this Entry
		// Get the page source
		// Parse the reblogs, this is source specific
		
		return null;
	}
	
	public List<Entry> findSource(){
		// TODO find this Entries' source(s)
		List<Entry> parents = new ArrayList<>();
		return parents;
	}

	public Collection<? extends Feed> findLikes() {
		// TODO find Likes associated with this Entry
		// Get the page source
		// Parse the likes, this is source specific
		return null;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
