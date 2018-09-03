package org.SirTobiSwobi.c3.classifiertrainer.db;

import java.util.ArrayList;

public class CategoryManager {
	
	AVLTree<Category> categories;
	RelationshipManager relationshipManager;
	
	

	public RelationshipManager getRelationshipManager() {
		return relationshipManager;
	}

	public void setRelationshipManager(RelationshipManager relationshipManager) {
		this.relationshipManager = relationshipManager;
	}

	public CategoryManager(){
		categories = new AVLTree<Category>();
	}
	
	public long getSize(){
		return categories.getSize();
	}
	
	public Category getByAddress(long address){	
		return categories.getContent(address);
	}
	
	public void setCategory(Category category){
		categories.setContent(category, category.getId());
	}

	public synchronized void addCategoryWithoutId(String label, String description){
		long id = categories.getMaxId()+1;
		Category cat = new Category(id,label,description);
		categories.setContent(cat,id);
	}
	
	public void deleteCategory(long id){
		categories.deleteNode(id);
		if(relationshipManager!=null){
			//toDo: Implement automated deleting of all relationships containing this category
		}
	}
	
	public Category[] getCategoryArray(){
		ArrayList<Category> allContent = categories.toArrayList();
		Category[] catArray = allContent.toArray(new Category[0]);
		return catArray;
	}

}
