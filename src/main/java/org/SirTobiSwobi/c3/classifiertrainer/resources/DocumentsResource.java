package org.SirTobiSwobi.c3.classifiertrainer.resources;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.SirTobiSwobi.c3.classifiertrainer.api.TCDocument;
import org.SirTobiSwobi.c3.classifiertrainer.api.TCDocuments;
import org.SirTobiSwobi.c3.classifiertrainer.db.Document;
import org.SirTobiSwobi.c3.classifiertrainer.db.DocumentManager;

@Path("/documents")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DocumentsResource {
	
	private DocumentManager manager;

	public DocumentsResource(DocumentManager manager) {
		super();
		this.manager = manager;
	}
	
	@POST
	public Response addDocument(@NotNull @Valid TCDocuments documents){
		if(documents.getDocuments().length==1){
			TCDocument doc=documents.getDocuments()[0];
			if(doc.getId()>0){
				manager.addDocument(new Document(doc.getId(),doc.getLabel(),doc.getContent()));
			}else{
				manager.addDocumentWithoutId(doc.getLabel(), doc.getContent());
			}
		}else if(documents.getDocuments().length>=1){
			for(int i=0; i<documents.getDocuments().length; i++){
				manager.addDocument(new Document(documents.getDocuments()[i].getId(),documents.getDocuments()[i].getLabel(),documents.getDocuments()[i].getContent()));
			}
		}
		
		Response response = Response.ok().build();
		return response;
	}
	
	@GET
	public TCDocuments getDocuments(){
		Document[] documents = manager.getDocumentArray();
		TCDocument[] TCdocumentArray = new TCDocument[documents.length];
		for(int i=0; i<documents.length;i++){
			Document doc = documents[i];
			TCDocument TCdoc = new TCDocument(doc.getId(),doc.getLabel(),doc.getContent());
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
