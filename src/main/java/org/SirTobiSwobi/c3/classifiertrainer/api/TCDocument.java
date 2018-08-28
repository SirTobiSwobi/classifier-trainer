package org.SirTobiSwobi.c3.classifiertrainer.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TCDocument {
	
	
	private long id;
	private String label;
	private String content;
	
	public TCDocument(){
		//Jackson deserialization
	}
	
	
	public TCDocument(long id, String label, String content) {
		this.content = content;
		this.label = label;
		this.id = id;
	}
	
	@JsonProperty
	public long getId() {
		return id;
	}
	
	@JsonProperty
	public String getLabel() {
		return label;
	}
	
	@JsonProperty
	public String getContent() {
		return content;
	}

}
