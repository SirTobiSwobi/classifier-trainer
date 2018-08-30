package org.SirTobiSwobi.c3.classifiertrainer;

import javax.ws.rs.client.Client;

import org.SirTobiSwobi.c3.classifiertrainer.db.CategoryManager;
import org.SirTobiSwobi.c3.classifiertrainer.db.DocumentManager;
import org.SirTobiSwobi.c3.classifiertrainer.health.ConfigHealthCheck;
import org.SirTobiSwobi.c3.classifiertrainer.resources.CategoriesResource;
import org.SirTobiSwobi.c3.classifiertrainer.resources.CategoryResource;
import org.SirTobiSwobi.c3.classifiertrainer.resources.DocumentResource;
import org.SirTobiSwobi.c3.classifiertrainer.resources.DocumentsResource;
import org.SirTobiSwobi.c3.classifiertrainer.resources.MetadataResource;

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
		
		
	}

}
