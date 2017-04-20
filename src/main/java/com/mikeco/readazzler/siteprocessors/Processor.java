package com.mikeco.readazzler.siteprocessors;

import java.net.URL;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Processor {
	private static final Logger log = LoggerFactory.getLogger(Processor.class);

	public static Processor fromURL(URL url) {
		Processor processor = null;
		if (url.getHost()
			.toLowerCase()
			.contains("tumblr.com"))
			return new Tumblr();
		log.error("fromURL: no Processor for host: {}", url.getHost());
		return processor;
	}

	public abstract String getDescription(Document page);
}