package org.icary.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class PropertiesFileConfig implements Config{

	private Properties properties = new Properties();
	
	@Inject
	public PropertiesFileConfig(File propertyFile) throws FileNotFoundException, IOException {
		properties.load(new FileReader(propertyFile));
	}
	
	@Override
	public String get(String key) {
		return properties.getProperty(key);
	}

}
