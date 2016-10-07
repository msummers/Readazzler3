package com.mikeco.readazzler.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.rometools.rome.feed.synd.SyndCategory;

@Entity
public class Category {
	public static Set<Category> fromList(List<SyndCategory> categories) {
		Set<Category> result = new HashSet<>(categories.size());
		for(SyndCategory sc : categories){
			result.add(new Category(sc));
		}
		return result;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String name = "";

	private String taxonomyUri = "";
	public Category() {
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
