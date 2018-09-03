package org.SirTobiSwobi.c3.classifiertrainer.resources;

//import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
//import javax.ws.rs.core.UriBuilder;

import org.SirTobiSwobi.c3.classifiertrainer.api.TCDocument;
import org.SirTobiSwobi.c3.classifiertrainer.api.TCDocuments;
import org.SirTobiSwobi.c3.classifiertrainer.db.Document;
import org.SirTobiSwobi.c3.classifiertrainer.db.DocumentManager;

@Path("/documents")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DocumentsResource {
	
	private DocumentManager manager;
	private Client client;

	public DocumentsResource(DocumentManager manager, Client client) {
		super();
		this.manager = manager;
		this.client = client;
	}
	
	@POST
	public Response addDocument(@NotNull @Valid TCDocuments documents){
		if(documents.getDocuments().length==0){
			Response response = Response.status(400).build();
			return response;
		}
		else if(documents.getDocuments().length>0){
			for(int i=0; i<documents.getDocuments().length; i++){
				TCDocument doc=documents.getDocuments()[i];
				add(doc);
			}
		}
		//URI location = UriBuilder.fromPath("/documents").build();
		Response response = Response.status(201).build();
		return response;
	}
	
	
	private void add(TCDocument doc){
		if(doc.getId()>=0){
			if(doc.getUrl().length()>0&&doc.getUrl().startsWith("http")){
				//downloading content from URL
				String content = client.target(doc.getUrl()).request().get(String.class);
				Document newDoc = new Document(doc.getId(),doc.getLabel(),content,doc.getUrl());
				manager.setDocument(newDoc);
			}else{
				manager.setDocument(new Document(doc.getId(),doc.getLabel(),doc.getContent()));
			}
		}else{
			if(doc.getUrl().length()>0&&doc.getUrl().startsWith("http")){
				//downloading content from URL
				String content = client.target(doc.getUrl()).request().get(String.class);
				manager.addDocumentWithoutId(doc.getLabel(), content, doc.getUrl());
			}else{
				manager.addDocumentWithoutId(doc.getLabel(), doc.getContent());
			}
			
		}
	}
	
	@GET
	public TCDocuments getDocuments(){
		Document[] documents = manager.getDocumentArray();
		TCDocument[] TCdocumentArray = new TCDocument[documents.length];
		for(int i=0; i<documents.length;i++){
			Document doc = documents[i];
			TCDocument TCdoc = new TCDocument(doc.getId(),doc.getLabel(),doc.getContent(),doc.getURL());
			TCdocumentArray[i]=TCdoc;
		}
		TCDocuments TCdocuments;
		if(documents.length>0){
			TCdocuments = new TCDocuments(TCdocumentArray);
		}else{
			TCdocuments = new TCDocuments();
		}
		
		return TCdocuments;
	}
	
	@DELETE
	public Response deleteAllDocuments(){
		manager=new DocumentManager();
		Response response = Response.ok().build();
		return response;
	}
	

}
