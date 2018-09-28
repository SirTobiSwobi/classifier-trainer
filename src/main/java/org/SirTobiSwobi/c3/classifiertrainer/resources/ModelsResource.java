package org.SirTobiSwobi.c3.classifiertrainer.resources;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.SirTobiSwobi.c3.classifiertrainer.api.TCHash;
import org.SirTobiSwobi.c3.classifiertrainer.api.TCModel;
import org.SirTobiSwobi.c3.classifiertrainer.api.TCModels;
import org.SirTobiSwobi.c3.classifiertrainer.api.TCProgress;
import org.SirTobiSwobi.c3.classifiertrainer.db.Model;
import org.SirTobiSwobi.c3.classifiertrainer.db.ReferenceHub;

@Path("/models")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ModelsResource {
	private ReferenceHub refHub;

	public ModelsResource(ReferenceHub refHub) {
		super();
		this.refHub = refHub;
	}
	
	@GET
	public Response getModels(@QueryParam("hash") String hash){
		if(hash!=null&&hash.equals("1")){
			TCHash h = new TCHash("models",refHub.getModelManager().getModelHash());
			return Response.ok(h).build();
		}else{
			Model[] models = refHub.getModelManager().getModelArray();
			TCModel[] TCmodelArray = new TCModel[models.length];
			for(int i=0; i<models.length;i++){
				Model mod = models[i];
				TCModel TCmod = new TCModel(mod.getId(),mod.getConfigurationId());
				TCmodelArray[i]=TCmod;
			}
			TCModels TCmodels;
			if(models.length>0){
				TCmodels = new TCModels(TCmodelArray);
			}else{
				TCmodels = new TCModels();
			}
			return Response.ok(TCmodels).build();
				
				
		}	
	}
	
	@POST
	public Response startTraining(@QueryParam("confId") long conf){
		
		if(!refHub.getConfigurationManager().containsConfiguration(conf)){
			Response response = Response.status(400).build();
			return response;
		}
		else{
			//Spawn training progress
			long modId=refHub.getModelManager().addModelWithoutId(conf);
			TCProgress progress = new TCProgress("/models/"+modId,.0);
			Response response = Response.ok(progress).build();
			return response;
			
		}		
	}

}
