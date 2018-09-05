package org.SirTobiSwobi.c3.classifiertrainer.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TCHash {
	
	private long id, fromId, toId;
	private String type;
	
	public TCHash(){
		//Jackson deserialization
	}
	
	public TCHash(long id, long fromId, long toId, String type) {
		super();
		this.id = id;
		this.fromId = fromId;
		this.toId = toId;
		this.type = type;
	}

	@JsonProperty
	public long getId() {
		return id;
	}

	@JsonProperty
	public long getFromId() {
		return fromId;
	}

	@JsonProperty
	public long getToId() {
		return toId;
	}

	@JsonProperty
	public String getType() {
		return type;
	}	

}
