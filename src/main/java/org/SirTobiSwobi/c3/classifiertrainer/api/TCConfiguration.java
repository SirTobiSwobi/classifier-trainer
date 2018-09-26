package org.SirTobiSwobi.c3.classifiertrainer.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TCConfiguration {

	private long id;

	public TCConfiguration(long id) {
		this.id = id;
	}

	public TCConfiguration() {
		//Jackson deserialization
	}

	@JsonProperty
	public long getId() {
		return id;
	}

	
	
}
