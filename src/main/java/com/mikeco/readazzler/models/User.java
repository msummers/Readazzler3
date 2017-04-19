package com.mikeco.readazzler.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class User {
	@OneToMany(mappedBy="user", cascade = { CascadeType.ALL})
	private Set<Feed> discoveredFeeds = new HashSet<Feed>();

	@OneToMany(mappedBy="user", cascade = { CascadeType.ALL})
	private Set<Folder> folders = new HashSet<Folder>();

	@OneToMany(mappedBy="user", cascade = { CascadeType.ALL})
	private Set<Feed> followedFeeds = new HashSet<Feed>();
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String name;

	public Set<Feed> getDiscoveredFeeds() {
		return discoveredFeeds;
	}

	public Set<Folder> getFolders() {
		return folders;
	}

	public Set<Feed> getFollowedFeeds() {
		return followedFeeds;
	}

	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setDiscoveredFeeds(Set<Feed> discoveredFeeds) {
		this.discoveredFeeds = discoveredFeeds;
	}
	public void setFolders(Set<Folder> folders) {
		this.folders = folders;
	}
	
	public void setFollowedFeeds(Set<Feed> followedFeeds) {
		this.followedFeeds = followedFeeds;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
