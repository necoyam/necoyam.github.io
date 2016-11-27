package org.icary.blog.parser;

public class Paragraph {

	private ParagraphType paragraphType = null;
	private String paragraph = "";

	public ParagraphType getParagraphType() {
		return paragraphType;
	}

	public void setParagraphType(ParagraphType paragraphType) {
		this.paragraphType = paragraphType;
	}

	public String getParagraph() {
		return paragraph;
	}

	public void setParagraph(String paragraph) {
		this.paragraph = paragraph;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((paragraph == null) ? 0 : paragraph.hashCode());
		result = prime * result + ((paragraphType == null) ? 0 : paragraphType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paragraph other = (Paragraph) obj;
		if (paragraph == null) {
			if (other.paragraph != null)
				return false;
		} else if (!paragraph.equals(other.paragraph))
			return false;
		if (paragraphType != other.paragraphType)
			return false;
		return true;
	}

}
