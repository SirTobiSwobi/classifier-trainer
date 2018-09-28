package org.SirTobiSwobi.c3.classifiertrainer.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TCProgress {
	private String modelAddress;
	private double progress;
	
	public TCProgress(){
		//Jackson deserialization
	}

	public TCProgress(String modelAddress, double progress) {
		super();
		this.modelAddress = modelAddress;
		this.progress = progress;
	}

	@JsonProperty
	public String getModelAddress() {
		return modelAddress;
	}

	@JsonProperty
	public double getProgress() {
		return progress;
	}
	
	
}
