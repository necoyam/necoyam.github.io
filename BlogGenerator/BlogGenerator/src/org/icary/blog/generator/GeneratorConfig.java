package org.icary.blog.generator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class GeneratorConfig {

  private static final String CONFIG_FILE_PATH = "resource\\configuration.properties";

  private static Properties properties;

  private static final Logger logger = Logger.getLogger(GeneratorConfig.class);

  static {
    properties = new Properties();
    try {
      properties.load(new FileInputStream(CONFIG_FILE_PATH));
    } catch (IOException e) {
      logger.error("Failed to read configuration from " + CONFIG_FILE_PATH, e);
    }
  }

  public static String get(String key) {
    return GeneratorConfig.properties.getProperty(key);
  }


}
