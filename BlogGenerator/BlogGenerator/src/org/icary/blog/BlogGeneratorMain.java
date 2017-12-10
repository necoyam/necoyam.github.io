package org.icary.blog;

import org.icary.blog.generator.BlogGenerator;
import org.icary.blog.guice.BlogGeneratorModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class BlogGeneratorMain {

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new BlogGeneratorModule());
		injector.getInstance(BlogGenerator.class).generate();
	}

}
