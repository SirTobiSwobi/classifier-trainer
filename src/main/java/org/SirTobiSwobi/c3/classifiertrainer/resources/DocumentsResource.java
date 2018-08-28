package org.SirTobiSwobi.c3.classifiertrainer.resources;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.SirTobiSwobi.c3.classifiertrainer.api.TCDocumentWithoutId;
import org.SirTobiSwobi.c3.classifiertrainer.db.DocumentManager;

@Path("/documents")
@Produces(MediaType.APPLICATION_JSON)
public class DocumentsResource {
	
	private DocumentManager manager;

	public DocumentsResource(DocumentManager manager) {
		super();
		this.manager = manager;
	}
	
	@POST
	public Response addDocument(@NotNull @Valid TCDocumentWithoutId document){
		manager.addDocumentWithoutId(document.getLabel(), document.getContent());
		Response response = Response.ok().build();
		return response;
	}
	

}
