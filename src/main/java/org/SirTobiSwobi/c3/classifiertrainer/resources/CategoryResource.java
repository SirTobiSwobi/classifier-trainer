package org.SirTobiSwobi.c3.classifiertrainer.resources;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.SirTobiSwobi.c3.classifiertrainer.api.TCCategory;
import org.SirTobiSwobi.c3.classifiertrainer.db.Category;
import org.SirTobiSwobi.c3.classifiertrainer.db.CategoryManager;

import com.codahale.metrics.annotation.Timed;

@Path("/categories/{cat}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {
	private CategoryManager manager;

	public CategoryResource(CategoryManager manager) {
		this.manager = manager;
	}
	
	@GET
    @Timed
	public TCCategory getCategory(@PathParam("cat") long cat){
		TCCategory output = new TCCategory(manager.getByAddress(cat).getId(),
				manager.getByAddress(cat).getLabel(),
				manager.getByAddress(cat).getDescription());
		return output;
		
	}

	@PUT
	public Response setCategory(@PathParam("cat") long cat, @NotNull @Valid TCCategory category){
		if(category.getId()!=cat){
			Response response = Response.status(400).build();
			return response;
		}
		
		manager.addCategory(new Category(category.getId(),category.getLabel(),category.getDescription()));

		Response response = Response.ok().build();
		return response;
	}
	
	@DELETE
	public Response deleteDocument(@PathParam("cat") long cat){
		manager.deleteCategory(cat);
		Response response = Response.ok().build();
		return response;
	}
}
