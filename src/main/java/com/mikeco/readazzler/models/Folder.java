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
public class Folder {
	@ManyToMany(mappedBy="folders", cascade = { CascadeType.ALL})
	private Set<Feed> feeds = new HashSet<Feed>();
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String label;
	
	@ManyToOne(cascade = { CascadeType.ALL})
	private Folder parentFolder;
	
	@OneToMany(mappedBy="parentFolder", cascade = { CascadeType.ALL})
	private Set<Folder> subFolders = new HashSet<Folder>();
	
	@ManyToOne
	private User user;

	public Set<Feed> getFeeds() {
		return feeds;
	}

	public Long getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public Folder getParentFolder() {
		return parentFolder;
	}

	public Set<Folder> getSubFolders() {
		return subFolders;
	}

	public void setFeeds(Set<Feed> feeds) {
		this.feeds = feeds;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setParentFolder(Folder parentFolder) {
		this.parentFolder = parentFolder;
	}

	public void setSubFolders(Set<Folder> subFolders) {
		this.subFolders = subFolders;
	}
}
