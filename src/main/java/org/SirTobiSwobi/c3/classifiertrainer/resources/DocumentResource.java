package org.SirTobiSwobi.c3.classifiertrainer.resources;

import com.codahale.metrics.annotation.Timed;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.SirTobiSwobi.c3.classifiertrainer.api.TCDocument;
import org.SirTobiSwobi.c3.classifiertrainer.db.Document;
import org.SirTobiSwobi.c3.classifiertrainer.db.DocumentManager;

@Path("/documents/{doc}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DocumentResource {
	
	private DocumentManager manager;
	private Client client;
	
	public DocumentResource(DocumentManager manager, Client client){
		this.manager=manager;
		this.client=client;
	}
	
	@GET
    @Timed
	public TCDocument getDocument(@PathParam("doc") long doc){
		TCDocument output = new TCDocument(manager.getByAddress(doc).getId(),
				manager.getByAddress(doc).getLabel(),
				manager.getByAddress(doc).getContent(),
				manager.getByAddress(doc).getURL());
		return output;
		
	}
	
	@PUT
	public Response setDocument(@PathParam("doc") long doc, @NotNull @Valid TCDocument document){
		if(document.getId()!=doc){
			Response response = Response.status(400).build();
			return response;
		}
		if(document.getUrl().length()>0&&document.getUrl().startsWith("http")){
			//downloading content from URL
			String content = client.target(document.getUrl()).request().get(String.class);
			Document newDoc = new Document(document.getId(),document.getLabel(),content,document.getUrl());
			manager.setDocument(newDoc);
		}else{
			manager.setDocument(new Document(document.getId(),document.getLabel(),document.getContent()));
		}
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
