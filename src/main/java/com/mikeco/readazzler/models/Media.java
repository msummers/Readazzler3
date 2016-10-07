package com.mikeco.readazzler.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;



@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames="link"))
public class Media {
	@OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE })
	private Set<Entry> entries = new HashSet<Entry>();
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Boolean isRead = false;
	
	private String link;
	
	private String type;

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
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setType(String type) {
		this.type = type;
	}

}
