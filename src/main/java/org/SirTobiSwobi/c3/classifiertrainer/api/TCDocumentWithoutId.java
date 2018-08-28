package org.SirTobiSwobi.c3.classifiertrainer.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TCDocumentWithoutId {
	
	
	private String label;
	private String content;
	
	public TCDocumentWithoutId(){
		//Jackson deserialization
	}
	
	
	public TCDocumentWithoutId(String label, String content) {
		this.content = content;
		this.label = label;
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
