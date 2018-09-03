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

import org.SirTobiSwobi.c3.classifiertrainer.api.TCCategories;
import org.SirTobiSwobi.c3.classifiertrainer.api.TCCategory;
import org.SirTobiSwobi.c3.classifiertrainer.db.Category;
import org.SirTobiSwobi.c3.classifiertrainer.db.CategoryManager;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriesResource {
	
	CategoryManager manager;

	public CategoriesResource(CategoryManager manager) {
		this.manager = manager;
	}
	
	@POST
	public Response addCategories(@NotNull @Valid TCCategories categories){
		if(categories.getCategories().length==0){
			Response response = Response.status(400).build();
			return response;
		}
		else if(categories.getCategories().length>0){
			for(int i=0; i<categories.getCategories().length; i++){
				TCCategory cat=categories.getCategories()[i];
				if(cat.getId()>=0){
					manager.setCategory(new Category(cat.getId(),cat.getLabel(),cat.getDescription()));
				}else{			
					manager.addCategoryWithoutId(cat.getLabel(), cat.getDescription());
				}
			}
		}
		
		Response response = Response.ok().build();
		return response;
	}

	
	@GET
	public TCCategories getCategories(){
		Category[] categories = manager.getCategoryArray();
		TCCategory[] TCcategoryArray = new TCCategory[categories.length];
		for(int i=0; i<categories.length;i++){
			Category cat = categories[i];
			TCCategory TCcat = new TCCategory(cat.getId(),cat.getLabel(),cat.getDescription());
			TCcategoryArray[i]=TCcat;
		}
		TCCategories TCcategories;
		if(categories.length>0){
			TCcategories = new TCCategories(TCcategoryArray);
		}else{
			TCcategories = new TCCategories();
		}
		
		return TCcategories;
	}
	
	@DELETE
	public Response deleteAllCategories(){
		manager=new CategoryManager();
		Response response = Response.ok().build();
		return response;
	}

}
