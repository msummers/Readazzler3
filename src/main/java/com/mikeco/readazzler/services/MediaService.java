package com.mikeco.readazzler.services;

import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mikeco.readazzler.models.Entry;
import com.mikeco.readazzler.models.Media;
import com.mikeco.readazzler.repositories.MediaRepository;
import com.rometools.rome.feed.synd.SyndContent;

@Service
public class MediaService {
	@Autowired
	MediaRepository mediaRepo;
	Logger log = LoggerFactory.getLogger(MediaService.class);

	/*
	 * <description>
	 * <img src="http://67.media.tumblr.com/eded6323a996f9691fceded7213fa42c/tumblr_ob9xn4uZsm1upvpx5o1_500.jpg"/>
	 * <br/><br/><p>
	 * <a href="http://sexy-couple-904.tumblr.com/post/151411131093/tiner1961-he-will-fill-her-up-she-will-love" class="tumblr_blog">sexy-couple-904</a>
	 * :</p> <blockquote><p><a class="tumblr_blog" href="http://tiner1961.tumblr.com/post/150825868562">tiner1961</a>:</p> <blockquote> <p>He will fill her up she will love it</p> </blockquote> <a class="tumblelog"
	 * href="https://tmblr.co/m343yMgl2WVGSf744xZfg3w">@sexy-couple-01</a></blockquote>
	 * </description>
	 * 
	 * <description>
	 * <video id='embed-57f6b3b0d92c1962050676' class='crt-video crt-skin-default' width='400' height='300' poster='http://media.tumblr.com/tumblr_o2733dWGJA1th4h7i_frame1.jpg' preload='none' muted data-crt-video
	 * data-crt-options='{"autoheight":null,"duration":34,"hdUrl":false,"filmstrip":{"url":"http:\/\/31.media.tumblr.com\/previews\/tumblr_o2733dWGJA1th4h7i_filmstrip.jpg","width":"200","height":"150"}}' > <source
	 * src="http://ffloridabifreak.tumblr.com/video_file/t:ACRWJNBQoSyhEqcxHp446A/151417043477/tumblr_o2733dWGJA1th4h7i" type="video/mp4"> </video> <br/><br/>
	 * </description>
	 */
	public Set<Media> findOrNew(SyndContent description, Entry entry) {
		Set<Media> result = new HashSet<>();
		Document doc = Jsoup.parseBodyFragment(description.getValue());
		result.addAll(processMedia(doc, entry, "img"));
		result.addAll(processMedia(doc, entry, "source"));
		if (result.size() <= 0)
			log.warn("findOrNew: Unknown media type: " + description.getValue());
		return result;
	}

	private Set<Media> processMedia(Document doc, Entry entry, String type) {
		Set<Media> result = new HashSet<>();
		for (Element element : doc.select(type)) {
			String link = element.attr("src");
			Media media = mediaRepo.findSingletonByGuid(link);
			// Make a new media
			if (media == null) {
				media = new Media();
				media.setLink(link);
				media.setType(type);
			}
			// Add the entry to the media
			media.getEntries()
				.add(entry);
			result.add(media);
		}
		return result;
	}
}
