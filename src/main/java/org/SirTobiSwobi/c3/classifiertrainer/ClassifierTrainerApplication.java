package org.SirTobiSwobi.c3.classifiertrainer;

import javax.ws.rs.client.Client;

import org.SirTobiSwobi.c3.classifiertrainer.db.Category;
import org.SirTobiSwobi.c3.classifiertrainer.db.CategoryManager;
import org.SirTobiSwobi.c3.classifiertrainer.db.DocumentManager;
import org.SirTobiSwobi.c3.classifiertrainer.db.RelationshipType;
import org.SirTobiSwobi.c3.classifiertrainer.db.RelationshipManager;
import org.SirTobiSwobi.c3.classifiertrainer.health.ConfigHealthCheck;
import org.SirTobiSwobi.c3.classifiertrainer.resources.CategoriesResource;
import org.SirTobiSwobi.c3.classifiertrainer.resources.CategoryResource;
import org.SirTobiSwobi.c3.classifiertrainer.resources.DocumentResource;
import org.SirTobiSwobi.c3.classifiertrainer.resources.DocumentsResource;
import org.SirTobiSwobi.c3.classifiertrainer.resources.MetadataResource;
import org.SirTobiSwobi.c3.classifiertrainer.resources.RelationshipResource;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ClassifierTrainerApplication extends Application<ClassifierTrainerConfiguration> {

	public static void main(String[] args) throws Exception {
		new ClassifierTrainerApplication().run(args);
	}
	
	@Override
	public String getName() {
		return "classifier-trainer";
	}
	
	@Override
	public void initialize(Bootstrap<ClassifierTrainerConfiguration> bootstrap){
		bootstrap.addBundle(new AssetsBundle("/assets/", "/html/", "index.html"));
	}

	@Override
	public void run(ClassifierTrainerConfiguration configuration, Environment environment){
		/*
		 * Initializing data structures
		 */
		DocumentManager docMan = new DocumentManager();
		CategoryManager catMan = new CategoryManager(); 
		RelationshipManager relMan = new RelationshipManager(catMan);
		catMan.setRelationshipManager(relMan);
		
		/*
		 * Initializing HTTP client
		 */
		
		final Client client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration()).build(getName());
		
		/*
		 * Initializing resources requiring data structures and clients. 
		 */
	
		final MetadataResource metadata = new MetadataResource(configuration);
		final DocumentsResource documents = new DocumentsResource(docMan,client);
		final DocumentResource document = new DocumentResource(docMan,client);
		final CategoriesResource categories = new CategoriesResource(catMan);
		final CategoryResource category = new CategoryResource(catMan);
		final RelationshipResource relationship = new RelationshipResource(catMan, relMan);
		
		/*
		 * Initializing health checks
		 */
		
		final ConfigHealthCheck configHealth = new ConfigHealthCheck(configuration);
		
		/*
		 * Registering everything 
		 */
		
		environment.healthChecks().register("config", configHealth);
		environment.jersey().register(metadata);
		environment.jersey().register(documents);
		environment.jersey().register(document);
		environment.jersey().register(categories);
		environment.jersey().register(category);
		environment.jersey().register(relationship);
		
		/*
		 * Generating example data for manual testing during development
		 */
		
		Category cat = new Category(0,"Diseases","");
		catMan.setCategory(cat);
		cat = new Category(4,"Neoplasms","");
		catMan.setCategory(cat);
		cat = new Category(557,"Neoplasms by Histologic Type","");
		catMan.setCategory(cat);
		cat = new Category(665,"Nevi and Melanomas","");
		catMan.setCategory(cat);
		cat = new Category(510,"Melanoma","");
		catMan.setCategory(cat);
		cat = new Category(385,"Hutchinson's Melanotic Freckle","");
		catMan.setCategory(cat);
		cat = new Category(515,"Melanoma, Amelanotic","");
		catMan.setCategory(cat);
		cat = new Category(525,"Melanoma, Experimental","");
		catMan.setCategory(cat);
		cat = new Category(2,"Virus Diseases","");
		catMan.setCategory(cat);
		cat = new Category(256,"DNA Virus Infections","");
		catMan.setCategory(cat);
		cat = new Category(466,"Herpesviridae Infections","");
		catMan.setCategory(cat);
		cat = new Category(382,"Herpes Simplex","");
		catMan.setCategory(cat);
		cat = new Category(465,"Keratitis, Herpetic","");
		catMan.setCategory(cat);
		cat = new Category(450,"Keratitis, Dendritic","");
		catMan.setCategory(cat);
		
		relMan.addRelatonshipWithoutId(0, 4, RelationshipType.Sub);
		relMan.addRelatonshipWithoutId(4, 557, RelationshipType.Sub);
		relMan.addRelatonshipWithoutId(557, 665, RelationshipType.Sub);
		relMan.addRelatonshipWithoutId(665, 510, RelationshipType.Sub);
		relMan.addRelatonshipWithoutId(510, 385, RelationshipType.Sub);
		relMan.addRelatonshipWithoutId(510, 515, RelationshipType.Sub);
		relMan.addRelatonshipWithoutId(510, 525, RelationshipType.Sub);
		relMan.addRelatonshipWithoutId(0, 2, RelationshipType.Sub);
		relMan.addRelatonshipWithoutId(2, 256, RelationshipType.Sub);
		relMan.addRelatonshipWithoutId(256, 466, RelationshipType.Sub);
		relMan.addRelatonshipWithoutId(466, 382, RelationshipType.Sub);
		relMan.addRelatonshipWithoutId(382, 465, RelationshipType.Sub);
		relMan.addRelatonshipWithoutId(465, 450, RelationshipType.Sub);
		
		
	}

}
