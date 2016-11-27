package org.icary.blog.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.pegdown.PegDownProcessor;

public class BlogParser implements Parser {

	private PegDownProcessor pegDownProcessor = new PegDownProcessor();

	private Map<ParagraphType, Function<String, String>> parsingFunctionMap;

	public BlogParser() {
		parsingFunctionMap = new HashMap<>();
		parsingFunctionMap.put(ParagraphType.MARKDOWN, input -> pegDownProcessor.markdownToHtml(input));
		parsingFunctionMap.put(ParagraphType.HTML, input -> {
			return input;
		});

	}

	@Override
	public String parse(String original) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(original));
		StringBuilder buffer = new StringBuilder();
		String line = null;
		Paragraph paragraph = null;
		while ((line = br.readLine()) != null) {
			ParagraphType paragraphType = getParagraphType(line);
			if (paragraphType != null) { // found new type, create paragraph and
											// set type
				paragraph = processParagraph(buffer, paragraph);
				paragraph = new Paragraph();
				paragraph.setParagraphType(paragraphType);
			} else { // append
				paragraph.setParagraph(paragraph.getParagraph() + line + "\n");
			}
		}
		processParagraph(buffer, paragraph);
		return buffer.toString();
	}

	private Paragraph processParagraph(StringBuilder processed, Paragraph paragraph) {
		if (paragraph != null) {
			processed.append(parsingFunctionMap.get(paragraph.getParagraphType()).apply(paragraph.getParagraph()));
		}
		return paragraph;
	}

	private void processBuffer(StringBuilder buffer, StringBuilder processed) {
		processed.append(parsingFunctionMap.get(getParagraphType(buffer.toString())).apply(buffer.toString()))
				.append("\n");
		buffer.delete(0, buffer.length()); // clear buffer;
	}

	private ParagraphType getParagraphType(String line) {
		Optional<ParagraphType> paragraphType = Arrays.stream(ParagraphType.values())
				.filter(type -> line.startsWith(type.getTag())).findAny();
		return paragraphType.orElse(null);
	}

}
