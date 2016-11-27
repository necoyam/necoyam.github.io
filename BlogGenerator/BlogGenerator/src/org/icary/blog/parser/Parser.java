package org.icary.blog.parser;

import java.io.IOException;

public interface Parser {

	String parse(String original) throws IOException;
}
