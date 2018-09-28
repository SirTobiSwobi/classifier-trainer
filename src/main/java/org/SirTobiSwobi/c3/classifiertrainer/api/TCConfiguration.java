package org.SirTobiSwobi.c3.classifiertrainer.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TCConfiguration {

	private long id;
	private int folds;

	public TCConfiguration(long id, int folds) {
		this.id = id;
		this.folds = folds;
	}

	public TCConfiguration() {
		//Jackson deserialization
	}

	@JsonProperty
	public long getId() {
		return id;
	}

	@JsonProperty
	public int getFolds() {
		return folds;
	}
	
	

	
	
}
