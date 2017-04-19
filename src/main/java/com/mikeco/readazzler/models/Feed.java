package com.mikeco.readazzler.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Feed {
	@ManyToOne
	private User user;

	@OneToMany(mappedBy="feed", cascade = { CascadeType.ALL})
	private Set<Entry> entries = new HashSet<>();
	
	@ManyToMany(cascade = { CascadeType.ALL})
	private Set<Folder> folders = new HashSet<>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToMany(mappedBy="likes", cascade = { CascadeType.ALL})
	private Set<Entry> likedEntries = new HashSet<>();
	
	private String rssUrl;
	private String siteUrl;
	private String title;
	private String type;

	public Feed() {

	}

	public Feed(String type, String title, String xmlUrl, String htmlUrl) {
		this.type = type;
		this.title = title;
		this.rssUrl = xmlUrl;
		this.siteUrl = htmlUrl;
	}

	public Set<Entry> getEntries() {
		return entries;
	}

	public Set<Folder> getFolders() {
		return folders;
	}

	public Long getId() {
		return id;
	}

	public Set<Entry> getLikedEntries() {
		return likedEntries;
	}

	public String getRssUrl() {
		return rssUrl;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	public void setEntries(Set<Entry> entries) {
		this.entries = entries;
	}

	public void setFolders(Set<Folder> folders) {
		this.folders = folders;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLikedEntries(Set<Entry> likedEntries) {
		this.likedEntries = likedEntries;
	}

	public void setRssUrl(String rssUrl) {
		this.rssUrl = rssUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

}
