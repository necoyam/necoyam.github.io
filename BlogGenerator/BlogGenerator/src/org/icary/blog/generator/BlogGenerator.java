package org.icary.blog.generator;

import java.util.Properties;

import org.apache.log4j.Logger;

public class BlogGenerator implements Generator {

  private final Logger logger = Logger.getLogger(BlogGenerator.class);

  public void generate(Properties configuration) {
    new ArticleGenerator().generate(configuration);
  }

  public static void main(String[] args) {
    Properties configuration = new Properties();
    configuration.setProperty("inputPath", "D:\\git\\necoyam.github.io\\raw");
    configuration.setProperty("outputPath", "D:\\git\\necoyam.github.io\\article");
    new BlogGenerator().generate(configuration);
  }
}
