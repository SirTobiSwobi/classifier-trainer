package org.SirTobiSwobi.c3.classifiertrainer.db;

public class Model {
	private long id;
	private double progress; //stores the training progress.
	private int steps;
	private int completed;
	private String trainingLog; //for API development purposed. Optional for actual model. Stores progress of the training.
	private Configuration configuration;
	
	public Model(long id, Configuration configuration) {
		super();
		this.id = id;
		this.configuration = configuration;
		this.progress = .0;
		this.trainingLog="";
		this.steps=0;
		this.completed=0;
		
	}
	
	public Model(long id, Configuration configuration, String trainingLog) {
		super();
		this.id = id;
		this.configuration = configuration;
		this.progress = 1.0; //only used for active Model when put there. Training progress is always completed.
		this.trainingLog=trainingLog;
		this.steps=10; //only used for active Model when put there. Training progress is always completed.
		this.completed=10; //only used for active Model when put there. Training progress is always completed.
		
	}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}
	
	public String toString(){
		return ""+id+" "+configuration.toString()+" "+progress;
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

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	
	
	
	
}
