package org.icary.blog.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Util {

	public static void checkNCreateDirectory(Path postPath) throws IOException {
		if (!Files.isDirectory(postPath)) {
			Files.createDirectory(postPath);
		}
	}
}
