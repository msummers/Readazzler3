package com.mikeco.readazzler.repositories;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.mikeco.readazzler.models.Entry;

public interface EntryRepository extends PagingAndSortingRepository<Entry, Long> {
	Logger log = LoggerFactory.getLogger(EntryRepository.class);

	public default Entry findSingletonByGuid(String guid) {
		List<Entry> entries = this.findByGuid(guid);
		if (entries.isEmpty())
			return null;
		if (entries.size() == 1)
			return entries.get(0);
		log.error("findSingletonByGuid: duplicates: " + guid);
		return entries.get(0);
	}

	public List<Entry> findByGuid(String link);
}
