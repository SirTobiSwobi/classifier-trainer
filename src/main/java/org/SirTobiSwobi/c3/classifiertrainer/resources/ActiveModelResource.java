package org.SirTobiSwobi.c3.classifiertrainer.resources;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.SirTobiSwobi.c3.classifiertrainer.api.TCModel;
import org.SirTobiSwobi.c3.classifiertrainer.db.Model;
import org.SirTobiSwobi.c3.classifiertrainer.db.ReferenceHub;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;

@Path("/model")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActiveModelResource {
	private ReferenceHub refHub;
	private Client client;

	public ActiveModelResource(ReferenceHub refHub, Client client) {
		super();
		this.refHub = refHub;
		this.client = client;
	}
	
	@GET
	public Response getModel(){
		Model model = refHub.getActiveModel();
		if(model==null){
			return Response.status(Response.Status.NOT_FOUND).build();
		}	
		TCModel output = new TCModel(model.getId(), model.getConfigurationId(), model.getProgress(), model.getTrainingLog(), model.isIncludeImplicits());
		
		return Response.ok(output).build();
		
	}
	
	@POST
	public Response setActiveModel(TCModel model, @QueryParam("loadFrom") String source){
		if(source!=null&&source.length()>5&&source.startsWith("http")){
			String content = client.target(source).request().get(String.class);
			/*
			Response res = client.target(source).request("application/json").get();
			if(res.getStatusInfo() != Response.Status.OK){
				return Response.status(res.getStatus()).build();
			}else{
				ObjectMapper MAPPER = Jackson.newObjectMapper();
				String content = res.readEntity(String.class);
				try{
					TCModel retrievedModel = MAPPER.readValue(content, TCModel.class);
					Model activeModel = new Model(retrievedModel.getId(), retrievedModel.getConfigurationId(), retrievedModel.getTrainingLog());
					refHub.setActiveModel(activeModel);
				}catch(Exception e){
					return Response.status(404).build();
				}
			}
			*/		
			ObjectMapper MAPPER = Jackson.newObjectMapper();
			try{
				TCModel retrievedModel = MAPPER.readValue(content, TCModel.class);
				Model activeModel = new Model(retrievedModel.getId(), retrievedModel.getConfigurationId(), retrievedModel.isIncludeImplicits(), retrievedModel.getTrainingLog());
				refHub.setActiveModel(activeModel);
			}catch(Exception e){
				return Response.status(404).build();
			}	
			refHub.setNeedsRetraining(false);
			return Response.ok().build();
		}else if(model!=null){
			Model activeModel = new Model(model.getId(), model.getConfigurationId(), model.isIncludeImplicits(), model.getTrainingLog());
			refHub.setActiveModel(activeModel);
			refHub.setNeedsRetraining(false);
			return Response.ok().build();
		}else{
			return Response.status(400).build();
		}
		
	
		
	}
	
	@PUT
	public Response updateActiveModel(TCModel model, @QueryParam("loadFrom") String source){
		return setActiveModel(model, source);
	}
}
