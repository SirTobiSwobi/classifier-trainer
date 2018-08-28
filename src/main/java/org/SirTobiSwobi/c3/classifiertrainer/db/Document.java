package org.SirTobiSwobi.c3.classifiertrainer.db;

public class Document {
	
	private long id;
	private String label;
	private String content;
	public Document(long id, String label, String content) {
		super();
		this.id = id;
		this.label = label;
		this.content = content;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	

}
