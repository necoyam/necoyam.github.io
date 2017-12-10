package org.icary.blog.generator;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class BlogGenerator implements Generator {

	private Logger logger = LoggerFactory.getLogger(BlogGenerator.class);

	private final Generator articleGenerator;

	@Inject
	public BlogGenerator(Generator articleGenerator) {
		this.articleGenerator = articleGenerator;
	}
	/**
	 * Main method which hold recipe to generator blog
	 */
	public void generate() {
		articleGenerator.generate();
	}

}
