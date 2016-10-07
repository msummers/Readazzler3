package com.mikeco.readazzler.repositories;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.mikeco.readazzler.models.Media;

public interface MediaRepository extends PagingAndSortingRepository<Media, Long> {
	Logger log = LoggerFactory.getLogger(MediaRepository.class);

	public default Media findSingletonByGuid(String guid) {
		List<Media> entries = this.findByLink(guid);
		if (entries.isEmpty())
			return null;
		if (entries.size() == 1)
			return entries.get(0);
		log.error("findSingletonByGuid: duplicates: " + guid);
		return entries.get(0);
	}

	public List<Media> findByLink(String link);
}
