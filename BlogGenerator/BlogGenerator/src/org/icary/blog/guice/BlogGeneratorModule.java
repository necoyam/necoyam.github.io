package org.icary.blog.guice;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;

import org.icary.blog.generator.ArticleGenerator;
import org.icary.blog.generator.BlogGenerator;
import org.icary.blog.generator.FromMarkDownGenerator;
import org.icary.blog.generator.Generator;
import org.icary.util.Config;
import org.icary.util.PropertiesFileConfig;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

public class BlogGeneratorModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Generator.class).annotatedWith(Names.named("Article")).to(ArticleGenerator.class);
		bind(Generator.class).annotatedWith(Names.named("Blog")).to(BlogGenerator.class);
	}

	@Provides
	FromMarkDownGenerator getFromMarkDownGenerator(Config config) {
		return new FromMarkDownGenerator(config);
	}
	
	@Provides
	BlogGenerator getBlogGenerator(@Named("Article")Generator articleGenerator) {
		return new BlogGenerator(articleGenerator);
	}
	
	@Provides
	ArticleGenerator getArticleGenerator(Config config, FromMarkDownGenerator fromMarkDownGenerator) {
		return new ArticleGenerator(config, fromMarkDownGenerator);
	}
	
	@Provides
	Config getBlogGeneratorConfig() throws FileNotFoundException, IOException {
		return new PropertiesFileConfig(FileSystems.getDefault().getPath("resources", "BlogGenerator.properties").toFile());
	}
	
}
