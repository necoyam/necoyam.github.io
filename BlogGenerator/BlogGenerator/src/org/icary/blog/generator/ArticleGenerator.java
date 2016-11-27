package org.icary.blog.generator;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.icary.blog.util.Util;

public class ArticleGenerator implements Generator {

  private final Logger logger = Logger.getLogger(ArticleGenerator.class);

  /*
   * (non-Javadoc) TODO: change it to copy all files with same folder structure then generate file
   * 
   * @see org.icary.blog.generator.Generator#generate(java.util.Properties)
   */
  @Override
  public void generate(Properties configuration) {
    String inputPath = configuration.getProperty("inputPath");
    String outputPath = configuration.getProperty("outputPath");
    Path postPath = this.checkNCreateDirectory(inputPath);
    Path htmlPath = this.checkNCreateDirectory(outputPath);

    logger.info("Start processing blog posts from " + inputPath);
    try {
      Files.walkFileTree(postPath, new FromMarkDownGenerator(postPath, htmlPath));
    } catch (IOException e) {
      logger.error(e);
      new RuntimeException("Failed to process blog posts from " + inputPath);
    }
  }

  private Path checkNCreateDirectory(String inputPath) {
    Path postPath = FileSystems.getDefault().getPath(inputPath);
    try {
      Util.checkNCreateDirectory(postPath);
    } catch (IOException e) {
      logger.error(e);
      throw new RuntimeException("Failed to find/create inputPath: " + inputPath);
    }
    return postPath;
  }

}
