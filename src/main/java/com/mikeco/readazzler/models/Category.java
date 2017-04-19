package com.mikeco.readazzler.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import com.mikeco.readazzler.repositories.Repositories;
import com.rometools.rome.feed.synd.SyndCategory;

/*
 * A category is a (hash)tag that may or may not be attached to an RSS entry
 */
@Entity
public class Category {
	public static Set<Category> fromList(List<SyndCategory> categories) {
		Set<Category> result = new HashSet<>(categories.size());
		for (SyndCategory sc : categories) {
			result.add(Repositories.getInstance()
				.getCategoryRespository()
				.findOrCreate(sc.getName(), sc.getTaxonomyUri()));
		}
		return result;
	}

	@ManyToMany(cascade = { CascadeType.ALL})
	private Set<Media> media = new HashSet<>();

	public Set<Media> getMedia() {
		return media;
	}

	public void setMedia(Set<Media> media) {
		this.media = media;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name = "";

	private String taxonomyUri = "";

	public Category() {
	}

	public Category(String name, String taxonomyUri) {
		this.name = name;
		this.taxonomyUri = taxonomyUri;
	}

	public Category(SyndCategory sc) {
		name = sc.getName();
		taxonomyUri = sc.getTaxonomyUri();
	}


	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getTaxonomyUri() {
		return taxonomyUri;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTaxonomyUri(String taxonomyUri) {
		this.taxonomyUri = taxonomyUri;
	}

}
