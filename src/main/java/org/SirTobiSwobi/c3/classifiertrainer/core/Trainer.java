package org.SirTobiSwobi.c3.classifiertrainer.core;

import java.util.ArrayList;
import java.util.Arrays;

import org.SirTobiSwobi.c3.classifiertrainer.db.Configuration;
import org.SirTobiSwobi.c3.classifiertrainer.db.Document;
import org.SirTobiSwobi.c3.classifiertrainer.db.ReferenceHub;

public class Trainer {
	private ReferenceHub refHub;
	
	public Trainer(ReferenceHub refHub) {
		super();
		this.refHub = refHub;
	}
	
	private long[] computeTrainingIdsFromEvaluationIds(long[] allIds, long[] evaluationIds){
		long [] output = new long[allIds.length-evaluationIds.length];
		ArrayList<Long> allIdsList = new ArrayList<Long>();
		ArrayList<Long> evaluationIdsList = new ArrayList<Long>();
		for(int i=0;i<allIds.length;i++){
			allIdsList.add(allIds[i]);
		}
		for(int i=0;i<evaluationIds.length;i++){
			evaluationIdsList.add(evaluationIds[i]);
		}
		allIdsList.removeAll(evaluationIdsList);
		for(int i=0;i<output.length;i++){
			output[i]=allIdsList.get(i);
		}
		return output;
	}

	public void startTraining(long configId, long modelId){
		Configuration config = refHub.getConfigurationManager().getByAddress(configId);
		int folds = config.getFolds();
		Document[] allDocs=refHub.getDocumentManager().getDocumentArray();
		int overallSteps = allDocs.length*folds;
		refHub.getModelManager().getModelByAddress(modelId).setSteps(overallSteps);
		
		long[] allIds = new long[allDocs.length];
		for(int i=0;i<allIds.length;i++){
			allIds[i]=allDocs[i].getId();
		}
		
		for(int i=0; i<folds;i++){
			int start=(allDocs.length/folds)*i;
			int end=((allDocs.length/folds)*(i+1));
			if(i==folds-1){
				end=allDocs.length;
			}
			
			long[] evaluationIds = Arrays.copyOfRange(allIds, start, end);
			long[] trainingIds = computeTrainingIdsFromEvaluationIds(allIds,evaluationIds);
			
			(new Fold(refHub, trainingIds, evaluationIds, i, modelId)).start();
		}
		
	}
}
