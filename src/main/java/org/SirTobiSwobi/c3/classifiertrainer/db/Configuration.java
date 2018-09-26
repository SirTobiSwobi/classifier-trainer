package org.SirTobiSwobi.c3.classifiertrainer.db;

/**
 * This class is meant to be extended in each classifier trainer implementation
 * @author Tobias Eljasik-Swoboda
 *
 */
public class Configuration {
	
	private long id;

	public Configuration(long id) {
		super();
		this.id = id;
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
	
	public String toString(){
		return ""+id;
	}

}
