package org.icary.blog.generator;

import org.apache.log4j.Logger;

public class BlogGenerator implements Generator {

  private final Logger logger = Logger.getLogger(BlogGenerator.class);

  public void generate() {
    new ArticleGenerator().generate();
  }

  public static void main(String[] args) {
    new BlogGenerator().generate();
  }
}
