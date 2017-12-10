package org.icary.blog.util;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class Util {

	public static void checkNCreateDirectory(Path postPath) throws IOException {
		if (!Files.isDirectory(postPath)) {
			Files.createDirectory(postPath);
		}
	}

	public static Path checkNCreateDirectory(String inputPath) {
		Path postPath = FileSystems.getDefault().getPath(inputPath);
		try {
			Util.checkNCreateDirectory(postPath);
		} catch (IOException e) {
			throw new RuntimeException("Failed to find/create inputPath: " + inputPath, e);
		}
		return postPath;
	}
}
