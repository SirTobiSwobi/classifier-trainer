package org.SirTobiSwobi.c3.classifiertrainer.db;

import java.util.ArrayList;

import org.SirTobiSwobi.c3.classifiertrainer.db.Document;

public class DocumentManager {
	
	private AVLTree<Document> documents;
	
	public DocumentManager(){
		documents = new AVLTree<Document>();
	}
	
	public long getSize(){
		return documents.getSize();
	}
	
	public Document getByAddress(long address){
		
		return documents.getContent(address);
	}
	
	public void addDocument(Document document){
		documents.setContent(document, document.getId());
	}

	public synchronized void addDocumentWithoutId(String label, String content){
		long id = documents.getMaxId()+1;
		Document doc = new Document(id,label,content);
		documents.setContent(doc,id);
	}
	
	public synchronized void addDocumentWithoutId(String label, String content, String url){
		long id = documents.getMaxId()+1;
		Document doc = new Document(id,label,content,url);
		documents.setContent(doc,id);
	}
	
	public void deleteDocument(long id){
		documents.deleteNode(id);
	}
	
	public Document[] getDocumentArray(){
		ArrayList<Document> allContent = documents.toArrayList();
		Document[] docArray = allContent.toArray(new Document[0]);
		return docArray;
	}

}
