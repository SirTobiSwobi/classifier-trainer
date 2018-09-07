package org.SirTobiSwobi.c3.classifiertrainer.resources;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.SirTobiSwobi.c3.classifiertrainer.api.TCHash;
import org.SirTobiSwobi.c3.classifiertrainer.api.TCTargetFunction;
import org.SirTobiSwobi.c3.classifiertrainer.db.Assignment;
import org.SirTobiSwobi.c3.classifiertrainer.db.ReferenceHub;
import org.SirTobiSwobi.c3.classifiertrainer.db.TCAssignment;
import org.SirTobiSwobi.c3.classifiertrainer.db.TargetFunctionManager;

@Path("/targetfunction")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TargetFunctionResource {
	private ReferenceHub refHub;

	public TargetFunctionResource(ReferenceHub refHub) {
		super();
		this.refHub = refHub;
	}
	
	@GET
	public Response getTargetfunction(@QueryParam("hash") String hash){
		if(hash!=null&&hash.equals("1")){
			TCHash h = new TCHash("targetfunction",refHub.getTargetFunctionManager().getAssignmentHash());
			return Response.ok(h).build();
		}else{
		
			TCTargetFunction output = buildTargetFunction(refHub.getTargetFunctionManager().getAssignmentArray());
			return Response.ok(output).build();
		}	
	}
	
	@GET
	@Path("/documents/{docId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAssignmentsForDocument(@PathParam("docId") long docId){
		
		Assignment[] assignments = refHub.getTargetFunctionManager().getDocumentAssignments(docId);
		return Response.ok(buildTargetFunction(assignments)).build();
	}
	
	@GET
	@Path("/categories/{catId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRelationshipsTo(@PathParam("catId") long catId){
		Assignment[] assignments = refHub.getTargetFunctionManager().getCategoryAssignments(catId);
		return Response.ok(buildTargetFunction(assignments)).build();
	}
	
	
	
	private TCTargetFunction buildTargetFunction(Assignment[] assignments){
		TCAssignment[] TCassignmentArray = new TCAssignment[assignments.length];
		for(int i=0; i<assignments.length;i++){
			Assignment ass = assignments[i];
			TCAssignment TCass = new TCAssignment(ass.getId(), ass.getDocumentId(), ass.getCategoryId());
			TCassignmentArray[i]=TCass;
		}
		TCTargetFunction TCtargetFunction;
		if(assignments.length>0){
			TCtargetFunction = new TCTargetFunction(TCassignmentArray);
		}else{
			TCtargetFunction = new TCTargetFunction();
		}
		
		return TCtargetFunction;
		
	}
	
	@DELETE
	public Response deleteAllAssignments(){
		refHub.setTargetFunctionManager(new TargetFunctionManager());
		refHub.getTargetFunctionManager().setRefHub(refHub);
		Response response = Response.ok().build();
		return response;
	}
	
	@POST
	public Response addTargetFunction(@NotNull @Valid TCTargetFunction targetFunction){
		
		if(targetFunction.getAssignments().length==0){
			Response response = Response.status(400).build();
			return response;
		}
		else if(targetFunction.getAssignments().length>0){
			for(int i=0; i<targetFunction.getAssignments().length; i++){
				TCAssignment ass=targetFunction.getAssignments()[i];
			
				
				if(ass.getId()>=0){
					refHub.getTargetFunctionManager().setAssignment(ass.getId(), ass.getDocumentId(), ass.getCategoryId());
				}else{			
					refHub.getTargetFunctionManager().addAssignmentWithoutId(ass.getDocumentId(), ass.getCategoryId());
				}
			}
		}
		
		Response response = Response.ok().build();
		return response;
	}
}
