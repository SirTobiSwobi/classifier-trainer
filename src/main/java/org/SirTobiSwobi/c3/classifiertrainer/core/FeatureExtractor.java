package org.SirTobiSwobi.c3.classifiertrainer.core;

import org.SirTobiSwobi.c3.classifiertrainer.db.Model;

public interface FeatureExtractor {
	public double[] getVector(long docId);
	public double[] getVector(String text);
	public Model getModel();
	public void setModel(Model model);

}
