package org.icary.blog.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

import org.icary.blog.parser.BlogParser;
import org.icary.blog.parser.Parser;
import org.icary.blog.util.Util;
import org.icary.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Singleton;

@Singleton
public class FromMarkDownGenerator extends SimpleFileVisitor<Path> {

	private Logger logger = LoggerFactory.getLogger(FromMarkDownGenerator.class);
	private final Config config;	
	
	private Path htmlPath;
	private Path sourcePath;

	private Parser parser = new BlogParser();

	private final String headerFile;
	private final String tailFile;

	private String headerHtml = "";
	private String tailHtml = "";

	public FromMarkDownGenerator(Config config) {
		this.config = config;
		this.headerFile = config.get("headerFile");
		this.tailFile = config.get("tailFile");
		this.sourcePath = Util.checkNCreateDirectory(config.get("inputPath"));
		this.htmlPath = Util.checkNCreateDirectory(config.get("outputPath"));

		try {
			headerHtml = new String(Files.readAllBytes(new File(headerFile).toPath()), "utf-8");
			tailHtml = new String(Files.readAllBytes(new File(tailFile).toPath()), "utf-8");
		} catch (IOException e) {
			throw new RuntimeException("Failed to read header / tail file", e);
		}
	}

	@Override
	public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
		this.process(path, generateOutputPathFromInputPath(path, htmlPath));
		return FileVisitResult.CONTINUE;
	}

	private void process(Path from, Path to) {
		if (from.getFileName().toString().endsWith(".post")) {
			this.markDownToHtml(from, to);
		} else {
			try {
				Files.createDirectories(to.getParent());
				Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING,
						StandardCopyOption.COPY_ATTRIBUTES);
			} catch (IOException e) {
				throw new RuntimeException("Failed to copy " + from.getFileName(), e);
			}
		}
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		logger.error("Error in visitFileFailed", exc);
		return FileVisitResult.CONTINUE;
	}

	private Path generateOutputPathFromInputPath(Path sourceDirectory, Path outputPath) {
		return outputPath.resolve(this.sourcePath.relativize(sourceDirectory));
	}

	private void markDownToHtml(Path from, Path to) {
		File htmlFile = to.getParent().resolve(to.toFile().getName() + ".html").toFile();
		logger.info("processing " + from.getFileName());
		try {
			BufferedWriter fw =
					new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "utf-8"));
			String original = new String(Files.readAllBytes(from), "utf-8");
			logger.debug("============original===========");
			logger.debug(original);
			String parsed = parser.parse(original);
			logger.debug("============parsed===========");
			logger.debug(parsed);
			fw.write(headerHtml);
			fw.write(parsed);
			// TODO: generate pager
			// TODO: create index page
			// TODO: create category
			fw.write(tailHtml);
			fw.flush();
			fw.close();
			logger.info("processed " + from.getFileName() + " to " + htmlFile.getName());
		} catch (IOException e) {
			throw new RuntimeException("Failed to process article", e);
		}
		logger.info("processed files are stored to " + htmlFile.getAbsolutePath());
	}
}
