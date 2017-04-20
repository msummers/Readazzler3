package com.mikeco.readazzler.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.rometools.rome.feed.synd.SyndEntry;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "link"))
public class Media {
	@ManyToMany(mappedBy = "media", cascade = { CascadeType.ALL })
	private Set<Entry> entries = new HashSet<Entry>();
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Boolean isRead = false;

	private String link;

	private String type;
	@ManyToMany(mappedBy = "media", cascade = { CascadeType.ALL })
	private Set<Category> categories = new HashSet<>();

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public void setCategories(SyndEntry syndEntry) {
		this.setCategories(Category.fromList(syndEntry.getCategories()));
		for (Category category : this.categories) {
			category.getMedia()
				.add(this);
		}

	}

	@ManyToMany(mappedBy = "media", cascade = { CascadeType.ALL })
	private Set<Tag> tags = new HashSet<Tag>();

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags.addAll(tags);
	}

	public Set<Entry> getEntries() {
		return entries;
	}

	public Long getId() {
		return id;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public String getLink() {
		return link;
	}

	public String getType() {
		return type;
	}

	public void setEntries(Set<Entry> entry) {
		this.entries = entry;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
		for (Entry entry : entries)
			entry.setIsRead(isRead);
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setType(String type) {
		this.type = type;
	}

}
