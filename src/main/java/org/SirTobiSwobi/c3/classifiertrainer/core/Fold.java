package org.SirTobiSwobi.c3.classifiertrainer.core;

import org.SirTobiSwobi.c3.classifiertrainer.db.Assignment;
import org.SirTobiSwobi.c3.classifiertrainer.db.Model;
import org.SirTobiSwobi.c3.classifiertrainer.db.ReferenceHub;

public class Fold extends Thread {
	private ReferenceHub refHub;
	private long[] trainingIds, evaluationIds;
	private int foldId;
	private long modelId;
	
	public Fold(ReferenceHub refHub, long[] trainingIds, long[] evaluationIds, int foldId, long modelId) {
		super();
		this.refHub = refHub;
		this.trainingIds = trainingIds;
		this.evaluationIds = evaluationIds;
		this.foldId = foldId;
		this.modelId = modelId;
	}
	
	public static Fold produceFold(ReferenceHub refHub, long[] trainingIds, long[] evaluationIds, int foldId, long modelId){
		return new Fold(refHub, trainingIds, evaluationIds, foldId, modelId);
	}

	public void run(){
		Model model=refHub.getModelManager().getModelByAddress(modelId);
		for(int i=0; i<trainingIds.length; i++){
			
			String appendString = "Fold "+foldId+" learned from document "+trainingIds[i]+": ";
			Assignment[] explicitAssignments=refHub.getTargetFunctionManager().getDocumentAssignments(trainingIds[i]);
			for(int j=0;j<explicitAssignments.length;j++){
				appendString = appendString +" Explicitly belongs to category "+explicitAssignments[j].getCategoryId()+", ";
			}
			long[] implicitAssignments = refHub.getTargetFunctionManager().getImplicitCatIdsForDocument(trainingIds[i]);
			for(int j=0; j<implicitAssignments.length; j++){
				appendString = appendString +" Implicitly belongs to category "+implicitAssignments[j]+", ";
			}
			model.appendToTrainingLog(appendString);
			model.incrementCompletedSteps();
			try{
				Thread.sleep(5000);
			}catch(InterruptedException e){
				//This is just use to simulate the actual training progress.
			}
			
		}
		for(int i=0; i<evaluationIds.length; i++){
			String appendString = "Fold "+foldId+" was evaluated using document "+evaluationIds[i]+" which: ";
			Assignment[] explicitAssignments=refHub.getTargetFunctionManager().getDocumentAssignments(evaluationIds[i]);
			for(int j=0;j<explicitAssignments.length;j++){
				appendString = appendString +" Explicitly belongs to category "+explicitAssignments[j].getCategoryId()+", ";
			}
			long[] implicitAssignments = refHub.getTargetFunctionManager().getImplicitCatIdsForDocument(evaluationIds[i]);
			for(int j=0; j<implicitAssignments.length; j++){
				appendString = appendString +" Implicitly belongs to category "+implicitAssignments[j]+", ";
			}
			model.appendToTrainingLog(appendString);
			model.incrementCompletedSteps();
			try{
				Thread.sleep(5000);
			}catch(InterruptedException e){
				//This is just use to simulate the actual training progress.
			}
		}
		
	}

}
