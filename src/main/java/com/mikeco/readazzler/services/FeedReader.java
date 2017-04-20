package com.mikeco.readazzler.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.mikeco.readazzler.models.Feed;
import com.mikeco.readazzler.repositories.EntryRepository;
import com.mikeco.readazzler.repositories.FeedRepository;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

@Service
public class FeedReader {
	Logger log = LoggerFactory.getLogger(FeedReader.class);
	@Autowired
	FeedRepository feedRepo;
	@Autowired
	EntryService entryService;
	@Autowired 
	EntryRepository entryRepo;

	// Every 10 minutes
	@Scheduled(fixedDelay = 1000 * 60 * 10)
	@Transactional
	public void readFeeds() {
		log.info("readFeeds: enter");

		for (Feed feed : feedRepo.findAll()) {
			log.debug(String.format("readFeeds: %s folder: %s", feed.getRssUrl(), feed.getFolders()
				.iterator().next()
				.getLabel()));
			try {
				//URL feedUrl = new URL(feed.getRssUrl());

				CloseableHttpClient httpClient = HttpClientBuilder.create()
					.build();
				HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

				// This is due to Spring not following https redirects
				RestTemplate restTemplate = new RestTemplate();
				//SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
				restTemplate.setRequestFactory(factory);
				
				ResponseEntity<String> response = restTemplate.getForEntity(feed.getRssUrl(), String.class);
				
				// FIXME Handle response codes responsibly!
				// A method on Feed is a good idea
				log.debug(feed.getRssUrl() + " : " + response.getStatusCode());
				if (response.getStatusCode() != HttpStatus.OK) {
					log.error(feed.getRssUrl() + " : " + response.getStatusCode());
					log.error(feed.getRssUrl() + " : " + response.getHeaders()
						.getLocation());
					continue;
				}
				InputStream stream = new ByteArrayInputStream(response.getBody()
					.getBytes(StandardCharsets.UTF_8));
				SyndFeedInput input = new SyndFeedInput();
				SyndFeed syndFeed = input.build(new XmlReader(stream));
				for (SyndEntry syndEntry : syndFeed.getEntries()) {
					feed.getEntries()
						.add(entryService.findOrNew(syndEntry, feed));
				}
				log.debug("readFeeds: saving feed");
				feedRepo.save(feed);
			} catch (Exception e) {
				log.error(feed.getTitle() + ": " + e.getLocalizedMessage(), e);
			}
		}
	}
}