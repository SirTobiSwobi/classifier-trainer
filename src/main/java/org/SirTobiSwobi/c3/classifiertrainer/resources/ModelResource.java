package org.SirTobiSwobi.c3.classifiertrainer.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.SirTobiSwobi.c3.classifiertrainer.api.TCModel;
import org.SirTobiSwobi.c3.classifiertrainer.db.Model;
import org.SirTobiSwobi.c3.classifiertrainer.db.ReferenceHub;

@Path("/models/{mod}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ModelResource {
	private ReferenceHub refHub;
	
	public ModelResource(ReferenceHub refHub) {
		super();
		this.refHub = refHub;
	}

	@GET
	public Response getModel(@PathParam("mod") long mod){
		if(!refHub.getModelManager().containsModel(mod)){
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		Model model = refHub.getModelManager().getModelByAddress(mod);
		
		TCModel output = new TCModel(model.getId(), model.getConfigurationId(), model.getProgress(), model.getTrainingLog(), model.isIncludeImplicits());
		
		return Response.ok(output).build();
		
	}
	
	@DELETE
	public Response deleteModel(@PathParam("mod") long mod){
		if(!refHub.getModelManager().containsModel(mod)){
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		refHub.getModelManager().deleteModel(mod);
		refHub.getEvaluationManager().deleteTrainingSession(mod);
		Response response = Response.ok().build();
		return response;
	}
}
