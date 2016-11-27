package org.icary.blog.parser;

public enum ParagraphType {
	HTML, MARKDOWN;

	public String getTag() {
		return "@" + this.name();
	}

}
