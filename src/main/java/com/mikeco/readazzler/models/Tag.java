package com.mikeco.readazzler.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


@Entity
public class Tag {
	@ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE })
	private Set<Entry> entries = new HashSet<Entry>();;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String label;

	public Set<Entry> getEntries() {
		return entries;
	}

	public Long getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public void setEntries(Set<Entry> entries) {
		this.entries = entries;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
