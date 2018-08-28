package org.SirTobiSwobi.c3.classifiertrainer;

import org.SirTobiSwobi.c3.classifiertrainer.db.Document;
import org.SirTobiSwobi.c3.classifiertrainer.db.DocumentManager;
import org.SirTobiSwobi.c3.classifiertrainer.health.ConfigHealthCheck;
import org.SirTobiSwobi.c3.classifiertrainer.resources.DocumentResource;
import org.SirTobiSwobi.c3.classifiertrainer.resources.DocumentsResource;
import org.SirTobiSwobi.c3.classifiertrainer.resources.MetadataResource;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
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
		DocumentManager docMan =  new DocumentManager();
		
		docMan.addDocument(new Document(0,"first label","first content"));
		docMan.addDocument(new Document(1,"second label","second content"));
		docMan.addDocument(new Document(2,"third label","third content"));
	
		
		final MetadataResource metadata = new MetadataResource(configuration);
		final DocumentsResource documents = new DocumentsResource(docMan);
		final DocumentResource document = new DocumentResource(docMan);
		final ConfigHealthCheck configHealth = new ConfigHealthCheck(configuration);
		environment.healthChecks().register("config", configHealth);
		environment.jersey().register(metadata);
		environment.jersey().register(documents);
		environment.jersey().register(document);
		
		
	}

}
