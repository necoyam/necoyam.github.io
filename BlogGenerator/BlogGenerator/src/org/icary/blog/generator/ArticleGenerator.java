package org.icary.blog.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.icary.blog.util.Util;
import org.icary.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ArticleGenerator implements Generator {

	private Logger logger = LoggerFactory.getLogger(ArticleGenerator.class);

	private final Config config;
	
	private final FromMarkDownGenerator fromMarkDownGenerator;

	@Inject
	public ArticleGenerator(Config config, FromMarkDownGenerator fromMarkDownGenerator) {
		this.config = config;
		this.fromMarkDownGenerator = fromMarkDownGenerator;
	}
	/*
	 * (non-Javadoc) TODO: change it to copy all files with same folder structure then generate file
	 * 
	 * @see org.icary.blog.generator.Generator#generate(java.util.Properties)
	 */
	@Override
	public void generate() {
		String inputPath = config.get("inputPath");
		Path postPath = Util.checkNCreateDirectory(inputPath);

		logger.info("Start processing blog posts from " + inputPath);
		try {
			Files.walkFileTree(postPath, fromMarkDownGenerator);
		} catch (IOException e) {
			new RuntimeException("Failed to process blog posts postPath, htmlPathfrom " + inputPath, e);
		}
	}

}
