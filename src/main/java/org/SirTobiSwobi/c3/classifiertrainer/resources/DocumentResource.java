package org.SirTobiSwobi.c3.classifiertrainer.resources;

import com.codahale.metrics.annotation.Timed;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.SirTobiSwobi.c3.classifiertrainer.api.TCDocument;
import org.SirTobiSwobi.c3.classifiertrainer.db.Document;
import org.SirTobiSwobi.c3.classifiertrainer.db.DocumentManager;

@Path("/documents/{doc}")
@Produces(MediaType.APPLICATION_JSON)
public class DocumentResource {
	
	private DocumentManager manager;
	
	public DocumentResource(DocumentManager manager){
		this.manager=manager;
	}
	
	@GET
    @Timed
	public TCDocument getDocument(@PathParam("doc") long doc){
		TCDocument output = new TCDocument(manager.getByAddress(doc).getId(),
				manager.getByAddress(doc).getLabel(),
				manager.getByAddress(doc).getContent());
		return output;
		
	}
	
	@PUT
	public Response setDocument(@PathParam("doc") long doc, @NotNull @Valid TCDocument document){
		if(document.getId()!=doc){
			Response response = Response.status(400).build();
			return response;
		}
		manager.addDocument(new Document(document.getId(),document.getLabel(),document.getContent()));
		Response response = Response.ok().build();
		return response;
	}
	
	@DELETE
	public Response deleteDocument(@PathParam("doc") long doc){
		manager.deleteDocument(doc);
		Response response = Response.ok().build();
		return response;
	}
	

}
