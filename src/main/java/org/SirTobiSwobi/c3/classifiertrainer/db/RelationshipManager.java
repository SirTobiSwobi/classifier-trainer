package org.SirTobiSwobi.c3.classifiertrainer.db;

import java.util.ArrayList;

public class RelationshipManager {
	private AVLTree<Relationship> relationships;
	private CategoryManager categories;
	private AVLTree<ArrayList<Relationship>> fromIndex;
	private AVLTree<ArrayList<Relationship>> toIndex;
	
	public RelationshipManager(CategoryManager categories){
		assert(categories!=null);
		this.categories = categories;
		this.relationships = new AVLTree<Relationship>();
		this.fromIndex = new AVLTree<ArrayList<Relationship>>();
		this.toIndex = new AVLTree<ArrayList<Relationship>>();
	}
	
	public synchronized void setRelationship(long id, long fromId, long toId, RelationshipType type){
		assert(categories.getByAddress(fromId)!=null&&categories.getByAddress(toId)!=null);
		Relationship relationship = new Relationship(id, categories.getByAddress(fromId), categories.getByAddress(toId), type);
		relationships.setContent(relationship, id);
		if(fromIndex.getSize()==0){
			ArrayList<Relationship> newList = new ArrayList<Relationship>();
			newList.add(relationship);
			fromIndex.setContent(newList, fromId);
		}else if(fromIndex.containsId(fromId)){
			fromIndex.getContent(fromId).add(relationship);
		}else{
			ArrayList<Relationship> newList = new ArrayList<Relationship>();
			newList.add(relationship);
			fromIndex.setContent(newList, fromId);
		}
		if(toIndex.getSize()==0){
			ArrayList<Relationship> newList = new ArrayList<Relationship>();
			newList.add(relationship);
			toIndex.setContent(newList, toId);
		}else if(toIndex.containsId(toId)&&toIndex.getSize()>0){
			toIndex.getContent(toId).add(relationship);
		}else{
			ArrayList<Relationship> newList = new ArrayList<Relationship>();
			newList.add(relationship);
			toIndex.setContent(newList, toId);
		}
	};
	
	public synchronized void deleteRelationship(long id){
		Relationship rel = relationships.getContent(id);
		fromIndex.getContent(rel.getFrom().getId()).remove(rel);
		if(fromIndex.getContent(rel.getFrom().getId()).isEmpty()){
			fromIndex.deleteNode(rel.getFrom().getId());
		}
		toIndex.getContent(rel.getTo().getId()).remove(rel);
		if(toIndex.getContent(rel.getTo().getId()).isEmpty()){
			toIndex.deleteNode(rel.getTo().getId());
		}
		relationships.deleteNode(id);
	}
	
	public Relationship getByAddress(long address){
		
		return relationships.getContent(address);
	}
	
	public synchronized void addRelatonshipWithoutId(long fromId, long toId, RelationshipType type){
		assert(categories.getByAddress(fromId)!=null&&categories.getByAddress(toId)!=null);
		long id = relationships.getMaxId()+1;
		setRelationship(id, fromId, toId, type);
	}
	
	public long getSize(){
		return relationships.getSize();
	}
	
	public Relationship[] getRelationshipArray(){
		ArrayList<Relationship> allContent = relationships.toArrayList();
		Relationship[] relArray = allContent.toArray(new Relationship[0]);
		return relArray;
	}
	
	public Relationship[] getAllRelationshipsFrom(long fromId){
		ArrayList<Relationship> allContent = fromIndex.getContent(fromId);
		Relationship[] relArray;
		if(allContent!=null){
			relArray = allContent.toArray(new Relationship[0]);
		}else {
			relArray = new Relationship[0];
		}
		return relArray;
	}
	
	public Relationship[] getAllRelationshipsTo(long toId){
		ArrayList<Relationship> allContent = toIndex.getContent(toId);
		Relationship[] relArray;
		if(allContent!=null){
			relArray = allContent.toArray(new Relationship[0]);
		}else {
			relArray = new Relationship[0];
		}
		
		return relArray;
	}
	
	public synchronized void deleteAllRelationshipsContaining(Category cat){
		//toDo: Implement
	}
	
}
