package org.icary.blog.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.log4j.Logger;
import org.icary.blog.parser.BlogParser;
import org.icary.blog.parser.Parser;

public class FromMarkDownGenerator extends SimpleFileVisitor<Path> {

  private final Logger logger = Logger.getLogger(FromMarkDownGenerator.class);
  private Path htmlPath;
  private Path sourcePath;

  private Parser parser = new BlogParser();

  private final String headerFile = "D:\\git\\necoyam.github.io\\template\\head.html";
  private final String tailFile = "D:\\git\\necoyam.github.io\\template\\tail.html";

  private String headerHtml = "";
  private String tailHtml = "";

  FromMarkDownGenerator(Path from, Path to) {
    this.sourcePath = from;
    this.htmlPath = to;

    try {
      headerHtml = new String(Files.readAllBytes(new File(headerFile).toPath()), "utf-8");
      tailHtml = new String(Files.readAllBytes(new File(tailFile).toPath()), "utf-8");
      logger.debug("============header===========");
      logger.debug(headerHtml);
      logger.debug("============tail===========");
      logger.debug(tailHtml);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      logger.error(e);
      throw new RuntimeException("Failed to read header / tail file");
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
        logger.error(e);
        throw new RuntimeException("Failed to copy " + from.getFileName());
      }
    }
  }

  @Override
  public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
    logger.error(exc);
    return FileVisitResult.CONTINUE;
  }

  private Path generateOutputPathFromInputPath(Path sourceDirectory, Path outputPath) {
    return outputPath.resolve(this.sourcePath.relativize(sourceDirectory));
  }

  private void markDownToHtml(Path from, Path to) {
    File htmlFile = to.getParent().resolve(to.toFile().getName() + ".html").toFile();
    logger.info("processing " + from.getFileName());
    try {
      FileWriter fw = new FileWriter(htmlFile);
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
      logger.error(e);
      throw new RuntimeException("Failed to process article");
    }
    logger.info("processed files are stored to " + htmlFile.getAbsolutePath());
  }
}
