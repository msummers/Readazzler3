package com.mikeco.readazzler.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.mikeco.readazzler.services.OpmlImport;

@Component
public class StartupEventListener {
	private static final Logger log = LoggerFactory.getLogger(StartupEventListener.class);
	@Autowired
	OpmlImport opmlImport;

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.info("Increment counter");
		opmlImport.fromFileName("jim.opml");
	}
}