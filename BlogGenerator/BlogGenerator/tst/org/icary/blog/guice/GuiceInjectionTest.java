package org.icary.blog.guice;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.hamcrest.core.IsNull;
import org.icary.blog.generator.ArticleGenerator;
import org.icary.blog.generator.BlogGenerator;
import org.icary.blog.generator.FromMarkDownGenerator;
import org.icary.blog.generator.Generator;
import org.icary.util.Config;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

public class GuiceInjectionTest {

	private Injector injector;

	@Before
	public void init() {
		injector = Guice.createInjector(new BlogGeneratorModule());
	}
	
	@Test
	public void Given_BlogGeneratorModule_When_InjectorGetCreated_Then_ShouldInjectAllRequiredClasses() {
		Generator blogGenerator = injector.getInstance(Key.get(Generator.class, Names.named("Blog")));
		assertThat(blogGenerator.getClass(), is(BlogGenerator.class));
		Generator articleGenerator = injector.getInstance(Key.get(Generator.class, Names.named("Article")));
		assertThat(articleGenerator.getClass(), is(ArticleGenerator.class));
		Config config = injector.getInstance(Config.class);
		assertThat(config.get("test"), is("True"));
		FromMarkDownGenerator fromMarkDownGenerator = injector.getInstance(FromMarkDownGenerator.class);
		assertThat(fromMarkDownGenerator, IsNull.notNullValue());
	}
}
