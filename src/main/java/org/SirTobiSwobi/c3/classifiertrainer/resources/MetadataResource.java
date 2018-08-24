package org.SirTobiSwobi.c3.classifiertrainer.resources;

import org.SirTobiSwobi.c3.classifiertrainer.ClassifierTrainerConfiguration;
import org.SirTobiSwobi.c3.classifiertrainer.api.TCMetadata;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Optional;

@Path("/metadata")
@Produces(MediaType.APPLICATION_JSON)
public class MetadataResource {
	private ClassifierTrainerConfiguration configuration;
	
	public MetadataResource(){
		super();
	}
	
	public MetadataResource(ClassifierTrainerConfiguration configuration){
		this.configuration = configuration;
	}
	
	@GET
    @Timed
	public TCMetadata getMetadata(){
		TCMetadata metadata = new TCMetadata(
				configuration.getName(),
				configuration.getCalls(),
				configuration.getAlgorithm(),
				configuration.getPhases(),
				configuration.getAlgorithm(),
				configuration.getConfiguration(),
				configuration.getConfigOptions(),
				configuration.getArchetype()
				);
		
		return metadata;
		
	}
	
}
