package com.mikeco.readazzler.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mikeco.readazzler.models.Category;
import com.mikeco.readazzler.models.Entry;
import com.mikeco.readazzler.models.Feed;
import com.mikeco.readazzler.models.Media;
import com.mikeco.readazzler.repositories.CategoryRepository;
import com.mikeco.readazzler.repositories.EntryRepository;
import com.rometools.rome.feed.synd.SyndEntry;

@Service
public class EntryService {
	Logger log = LoggerFactory.getLogger(EntryService.class);
	@Autowired
	EntryRepository entryRepo;
	@Autowired
	MediaService mediaService;
	
	public Entry findOrNew(SyndEntry syndEntry, Feed feed) {
		Entry entry = entryRepo.findSingletonByGuid(syndEntry.getLink());
		// Already processed this feed entry?
		// This works because I use a Set rather than a List so no duplicates
		if(entry != null)
			return entry;
		
		//TODO media is going to have to be a list
		Media media = mediaService.findOrNew(syndEntry.getDescription(), entry);
		entry = new Entry(syndEntry, media, feed);
		return entry;
	}
}