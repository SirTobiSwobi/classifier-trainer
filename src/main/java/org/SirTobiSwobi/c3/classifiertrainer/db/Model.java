package org.SirTobiSwobi.c3.classifiertrainer.db;

public class Model {
	private long id;
	private long configurationId; //Storing what this model is based on.
	private double progress; //stores the training progress.
	
	public Model(long id, long configurationId) {
		super();
		this.id = id;
		this.configurationId = configurationId;
		this.progress = .0;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getConfigurationId() {
		return configurationId;
	}

	public void setConfigurationId(long configurationId) {
		this.configurationId = configurationId;
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}
	
	public String toString(){
		return ""+id+" "+configurationId+" "+progress;
	}
	
}
