package org.SirTobiSwobi.c3.classifiertrainer.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TCModel {
	private long id;
	private long configurationId;
	
	public TCModel(){
		//Jackson deserialization
	}

	public TCModel(long id, long configurationId) {
		super();
		this.id = id;
		this.configurationId = configurationId;
	}

	@JsonProperty
	public long getConfigurationId() {
		return configurationId;
	}

	@JsonProperty
	public void setConfigurationId(long configurationId) {
		this.configurationId = configurationId;
	}
	
	

}
