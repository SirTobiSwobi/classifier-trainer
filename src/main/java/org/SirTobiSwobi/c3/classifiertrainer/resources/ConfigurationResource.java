package org.SirTobiSwobi.c3.classifiertrainer.resources;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.SirTobiSwobi.c3.classifiertrainer.api.TCConfiguration;
import org.SirTobiSwobi.c3.classifiertrainer.db.Configuration;
import org.SirTobiSwobi.c3.classifiertrainer.db.ReferenceHub;

import com.codahale.metrics.annotation.Timed;

@Path("/configurations/{conf}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConfigurationResource {
	private ReferenceHub refHub;

	public ConfigurationResource(ReferenceHub refHub) {
		super();
		this.refHub = refHub;
	}
	
	@GET
    @Timed
	public Response getConfiguration(@PathParam("conf") long conf){
		if(!refHub.getConfigurationManager().containsConfiguration(conf)){
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		Configuration configuration = refHub.getConfigurationManager().getByAddress(conf);	
		TCConfiguration output = new TCConfiguration(configuration.getId());	
		return Response.ok(output).build();
		
	}
	
	@PUT
	public Response setConfiguration(@PathParam("conf") long conf, @NotNull @Valid TCConfiguration configuration){
		if(configuration.getId()!=conf){
			Response response = Response.status(400).build();
			return response;
		}
		
		refHub.getConfigurationManager().setConfiguration(new Configuration(configuration.getId()));
		Response response = Response.ok().build();
		return response;
	}
	
	@DELETE
	public Response deleteConfiguration(@PathParam("conf") long conf){
		if(!refHub.getConfigurationManager().containsConfiguration(conf)){
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		refHub.getConfigurationManager().deleteConfiguration(conf);
		Response response = Response.ok().build();
		return response;
	}

}
