package org.SirTobiSwobi.c3.classifiertrainer.db;

/**
 * This class is meant to be extended in each classifier trainer implementation
 * @author Tobias Eljasik-Swoboda
 *
 */
public class Configuration {
	
	private long id;
	private int folds; // of n-fold cross-validation. Default = 2;
	private boolean includeImplicits;
	private double assignmentThreshold;
	private SelectionPolicy selectionPolicy;

	
	public Configuration(long id) {
		super();
		this.id = id;
		this.folds=2;
		this.includeImplicits=true;
		this.assignmentThreshold=0.5;
		this.selectionPolicy=SelectionPolicy.MicroaverageF1;
	}
	
	public Configuration(long id, int folds){
		super();
		this.id = id;
		this.folds = folds;
	}
	
	

	public Configuration(long id, int folds, boolean includeImplicits, double assignmentThreshold,
			SelectionPolicy selectionPolicy) {
		super();
		this.id = id;
		this.folds = folds;
		this.includeImplicits = includeImplicits;
		this.assignmentThreshold = assignmentThreshold;
		this.selectionPolicy = selectionPolicy;
	}

	public Configuration() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public int getFolds() {
		return folds;
	}

	public void setFolds(int folds) {
		this.folds = folds;
	}

	public String toString(){
		return ""+id+" "+folds;
	}

	public boolean isIncludeImplicits() {
		return includeImplicits;
	}

	public void setIncludeImplicits(boolean includeImplicits) {
		this.includeImplicits = includeImplicits;
	}

	public double getAssignmentThreshold() {
		return assignmentThreshold;
	}

	public void setAssignmentThreshold(double assignmentThreshold) {
		this.assignmentThreshold = assignmentThreshold;
	}

	public SelectionPolicy getSelectionPolicy() {
		return selectionPolicy;
	}

	public void setSelectionPolicy(SelectionPolicy selectionPolicy) {
		this.selectionPolicy = selectionPolicy;
	}	

}
