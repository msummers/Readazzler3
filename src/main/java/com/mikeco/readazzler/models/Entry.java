package com.mikeco.readazzler.models;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mikeco.readazzler.siteprocessors.Processor;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;

@Entity
public class Entry {
	private static final Logger log = LoggerFactory.getLogger(Entry.class);
	// This is the RSS Description
	@Transient
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

	private URL link;

	@ManyToMany(cascade = { CascadeType.ALL })
	private Set<Media> media = new HashSet<>();

	// The actual page
	@Transient
	private Document page;

	public Document getPage() {
		if (page == null) {
			try {
				page = Jsoup.parse(getLink(), 60);
			} catch (IOException e) {
				log.error("getPage: error getting page: {}", getLink(), e);
			}
		}
		return page;
	}

	private Date pubDate; // Mon, 22 Aug 2016 11:20:33 -0700

	// Where is this reblogged from? (immediate parent)
	@ManyToOne(cascade = { CascadeType.ALL })
	private Entry reblog;

	@OneToMany(mappedBy = "reblog", cascade = { CascadeType.ALL })
	private Set<Entry> reblogs = new HashSet<Entry>();

	private String title;
	@Transient
	private Processor processor;

	public Entry() {
	}

	public Entry(SyndEntry syndEntry, Set<Media> media, Feed feed) {
		this.setDescription(syndEntry.getDescription());
		this.setFeed(feed);
		this.setGuid(syndEntry.getUri());
		try {
			this.setLink(new URL(syndEntry.getLink()));
		} catch (MalformedURLException e) {
			log.error("Entry: syndEntry.getLink: invalid URL: %s", syndEntry.getLink(), e);
		}
		this.setMedia(media);
		for (Media m : media) {
			m.getEntries()
				.add(this);
			m.setCategories(syndEntry);
			this.isRead = m.getIsRead();
		}
		this.setPubDate(syndEntry.getPublishedDate());
		this.setTitle(syndEntry.getTitle());
	}

	public void enrich() {
		// TODO if I propagate enrich this is the trigger
		this.getLikes()
			.addAll(this.findLikes());
		this.getReblogs()
			.addAll(this.findReblogs());
		// TODO find source
		// TODO process path

	}

	public Collection<? extends Feed> findLikes() {
		// TODO find Likes associated with this Entry
		// Get the page source
		// Parse the likes, this is source specific
		return null;
	}

	public Collection<? extends Entry> findReblogs() {
		// TODO find Reblogs associated with this Entry
		// Get the page source
		// Parse the reblogs, this is source specific

		return null;
	}

	public List<Entry> findSource() {
		// TODO find this Entries' source(s)
		List<Entry> parents = new ArrayList<>();
		return parents;
	}

	public List<Entry> findTrail() {
		// TODO the path this entry took to get here if any.
		// This comes from parsing the <a> tags in the description
		List<Entry> parents = new ArrayList<>();
		return parents;
	}

	public String getDescription() {
		if (description == null) {
			description = getProcessor().getDescription(getPage());
		}
		return description;
	}

	private Processor getProcessor() {
		if (processor == null) {
			processor = Processor.fromURL(getLink());
		}
		return processor;
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

	public URL getLink() {
		return link;
	}

	public Set<Media> getMedia() {
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

	private boolean mediaIsTagged() {
		for (Media m : media)
			if (!m.getTags()
				.isEmpty())
				return true;
		return false;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDescription(SyndContent syndContent) {
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
		if (isRead && mediaIsTagged())
			enrich();
	}

	public void setLikes(Set<Feed> likes) {
		this.likes = likes;
	}

	public void setLink(URL link) {
		this.link = link;
	}

	public void setMedia(Set<Media> media) {
		if (media.size() >= 1)
			this.isRead = (media.toArray(new Media[0]))[0].getIsRead();
		this.media.addAll(media);
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
		// Tags are kept on the media, but they'll be 'set' in the ui on an Entry
		// (and perhaps a media)
		for (Media m : media)
			m.setTags(tags);
		if (!tags.isEmpty())
			enrich();
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
