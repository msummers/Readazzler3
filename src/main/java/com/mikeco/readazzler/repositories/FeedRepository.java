package com.mikeco.readazzler.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.mikeco.readazzler.models.Feed;

public interface FeedRepository extends PagingAndSortingRepository<Feed, Long> {
	public List<Feed> findByRssUrl(String rssUrl);
}
