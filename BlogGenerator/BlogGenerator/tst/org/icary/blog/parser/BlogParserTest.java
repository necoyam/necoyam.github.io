package org.icary.blog.parser;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.junit.Test;

public class BlogParserTest {

  private Parser parser = new BlogParser();

  @Test
  public void markDownOnlyTest() throws IOException {
    String testInput = "@MARKDOWN\n" + "# The largest heading\n" + "## The second largest heading\n"
        + "###### The smallest heading\n";
    String testOutput = "<h1>The largest heading</h1>\n" + "<h2>The second largest heading</h2>\n"
        + "<h6>The smallest heading</h6>"; // mark down ignors new line
                                           // at the end of file
    assertEquals(testOutput, parser.parse(testInput));
  }

  @Test
  public void htmlOnlyTest() throws IOException {
    String testInput = "@HTML\n" + "<h1>testing</h1>";
    String testOutput = "<h1>testing</h1>\n";
    assertEquals(testOutput, parser.parse(testInput));
  }

  @Test
  public void markdownNhtmlMixtureTest() throws IOException {
    String testInput = "@HTML\n" + "<h1>HTML</h1>\n" + "@MARKDOWN\n" + "# Markdown";
    String testOutput = "<h1>HTML</h1>\n<h1>Markdown</h1>";
    assertEquals(testOutput, parser.parse(testInput));
  }

  @Test
  public void test() {
    Path path = FileSystems.getDefault().getPath("C:\\temp\\20150804");
    for (int i = 0; i < path.getNameCount(); i++) {
      System.out.println(path.getName(i));
    }
    System.out.println(path.getFileName().toString());
    Path path2 = FileSystems.getDefault().getPath("C:\\temp");
    System.out.println(path2.resolve(path.getFileName()));
  }

}
