package org.SirTobiSwobi.c3.classifiertrainer.db;

public class Model {
	private long id;
	private long configurationId; //Storing what this model is based on.
	private double progress; //stores the training progress.
	private int steps;
	private int completed;
	private String trainingLog; //for API development purposed. Optional for actual model. Stores progress of the training.
	
	public Model(long id, long configurationId) {
		super();
		this.id = id;
		this.configurationId = configurationId;
		this.progress = .0;
		this.trainingLog="";
		this.steps=0;
		this.completed=0;
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

	public String getTrainingLog() {
		return trainingLog;
	}

	public void setTrainingLog(String trainingLog) {
		this.trainingLog = trainingLog;
	}
	
	public synchronized void appendToTrainingLog(String appendString){
		this.trainingLog = this.trainingLog + appendString;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public synchronized void incrementCompletedSteps(){
		this.completed++;
		this.progress=(double)completed/(double)steps;
	}
	
	
	
}
