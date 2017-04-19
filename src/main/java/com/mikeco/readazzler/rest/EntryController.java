package com.mikeco.readazzler.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mikeco.readazzler.models.Entry;
import com.mikeco.readazzler.repositories.EntryRepository;

@RestController
@RequestMapping(path="/entry", produces={MediaType.APPLICATION_JSON_VALUE})
public class EntryController {
	@Autowired
	EntryRepository entryRepo;
	@RequestMapping(path="/{id}/parent", method={RequestMethod.GET})
	public List<Entry> getParent(@PathVariable("id")Long id){
		Entry entry = entryRepo.findOne(id);
		return entry.findSource();
	}
}