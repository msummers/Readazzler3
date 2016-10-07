package com.mikeco.readazzler.services;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.mikeco.readazzler.models.Feed;
import com.mikeco.readazzler.models.Folder;
import com.mikeco.readazzler.models.User;
import com.mikeco.readazzler.repositories.FeedRepository;
import com.mikeco.readazzler.repositories.FolderRespository;
import com.mikeco.readazzler.repositories.UserRepository;

@Service
public class OpmlImport {
	private static final Logger LOG = LoggerFactory.getLogger(OpmlImport.class);
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private FolderRespository folderRepo;
	@Autowired
	private FeedRepository feedRepo;

	private User user;
	private Folder currentFolder;


	private Folder findOrCreateFolder(String label) {
		List<Folder> result = folderRepo.findByLabel(label);
		Folder folder = null;
		if (result.isEmpty()) {
			folder = new Folder();
			folder.setLabel(label);
			//folderRepo.save(folder);
		} else
			folder = result.get(0);
		return folder;
	}

	private User findOrCreateUser(String name) {
		List<User> result = userRepo.findByName(name);
		User user = null;
		if (result.isEmpty()) {
			user = new User();
			user.setName(name);
			//userRepo.save(user);
		} else
			user = result.get(0);
		return user;
	}

	public void fromFileName(String fileName) {
		// First ensure we have one and only one User
		user = findOrCreateUser("Jim");
		currentFolder = findOrCreateFolder("Unfiled");
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(this::parse);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		userRepo.save(user);
	}

	private void parse(String line) {
		if (line == null)
			return;
		line = line.trim();
		if (line.isEmpty())
			return;
		if (!line.startsWith("<outline"))
			return;
		if (line.contains(" type="))
			parseFeed(line);
		else
			parseFolder(line);
	}
	private static Logger log = LoggerFactory.getLogger(OpmlImport.class);
	// <outline type="rss" text="Baeldung" title="Baeldung" xmlUrl="http://feeds.feedburner.com/Baeldung" htmlUrl="http://www.baeldung.com"/>
	private void parseFeed(String line) {
		try {
			InputSource is = new InputSource(new StringReader(line));
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(is);
			NodeList nl = document.getElementsByTagName("outline");
			Node n = nl.item(0);
			NamedNodeMap nnm = n.getAttributes();
			String type = nnm.getNamedItem("type")
				.getFirstChild()
				.getTextContent();
			String text = nnm.getNamedItem("text")
				.getFirstChild()
				.getTextContent();
			String title = nnm.getNamedItem("title")
				.getFirstChild()
				.getTextContent();
			String rssUrl = nnm.getNamedItem("xmlUrl")
				.getFirstChild()
				.getTextContent();
			String siteUrl = nnm.getNamedItem("htmlUrl")
				.getFirstChild()
				.getTextContent();
			List<Feed> result = feedRepo.findByRssUrl(rssUrl);
			if (result.isEmpty()) {
				Feed feed = new Feed(type, title, rssUrl, siteUrl);
				currentFolder.getFeeds()
					.add(feed);
				feed.getFolders()
					.add(currentFolder);
				log.debug("parseFeed: save feed: " + feed.getRssUrl());
				//feedRepo.save(feed);
				log.debug("parseFeed: save folder: " + currentFolder.getLabel());
				//folderRepo.save(currentFolder);
			}

		} catch (Exception e) {
			LOG.error(line);
			LOG.error(e.getMessage());
		}

	}

	// <outline text="Java" title="Java">
	private void parseFolder(String line) {
		try {
			line = line.replaceAll(">", "/>");
			InputSource is = new InputSource(new StringReader(line));
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(is);
			NodeList nl = document.getElementsByTagName("outline");
			Node n = nl.item(0);
			NamedNodeMap nnm = n.getAttributes();
			String text = nnm.getNamedItem("text")
				.getFirstChild()
				.getTextContent();
			String label = nnm.getNamedItem("title")
				.getFirstChild()
				.getTextContent();
			List<Folder> result = folderRepo.findByLabel(label);
			if (result.isEmpty()) {
				Folder folder = new Folder();
				folder.setLabel(label);
				user.getFolders()
					.add(folder);
				log.debug("parseFolder: save folder: " + folder.getLabel());
				//folderRepo.save(folder);
				log.debug("parseFolder: save user: " + user.getName());
				//userRepo.save(user);
				currentFolder = folder;
			}
		} catch (Exception e) {
			LOG.error(line);
			LOG.error(e.getMessage());
		}
	}
}
