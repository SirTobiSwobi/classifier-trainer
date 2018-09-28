package org.SirTobiSwobi.c3.classifiertrainer.db;

/**
 * This class is meant to be extended in each classifier trainer implementation
 * @author Tobias Eljasik-Swoboda
 *
 */
public class Configuration {
	
	private long id;
	private int folds; // of n-fold cross-validation. Default = 1;

	public Configuration(long id) {
		super();
		this.id = id;
		this.folds=1;
	}
	
	public Configuration(long id, int folds){
		super();
		this.id = id;
		this.folds = folds;
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

}
