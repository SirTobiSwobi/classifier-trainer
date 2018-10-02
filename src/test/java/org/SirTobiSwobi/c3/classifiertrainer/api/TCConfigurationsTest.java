package org.SirTobiSwobi.c3.classifiertrainer.api;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

import org.SirTobiSwobi.c3.classifiertrainer.db.Configuration;
import org.SirTobiSwobi.c3.classifiertrainer.db.ConfigurationManager;
import org.SirTobiSwobi.c3.classifiertrainer.db.SelectionPolicy;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;

public class TCConfigurationsTest {

	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

	@Test
	public void serializesToJSON() throws Exception {
		//new Configuration(long id, int folds, boolean includeImplicits, double assignmentThreshold, SelectionPolicy selectionPolicy) 
		ConfigurationManager confMan =  new ConfigurationManager();
		Configuration cfgn = new Configuration(1,2, true, 0.5,SelectionPolicy.MicroaverageF1);
		confMan.setConfiguration(cfgn);
		cfgn = new Configuration(3,3, true, 0.6,SelectionPolicy.MacroaverageF1);
		confMan.setConfiguration(cfgn);
		cfgn = new Configuration(5,5, true, 0.4,SelectionPolicy.MicroaverageRecall);
		confMan.setConfiguration(cfgn);
		
		
		Configuration[] configurations = confMan.getConfigurationArray();
		TCConfiguration[] TCconfigurationArray = new TCConfiguration[configurations.length];
		for(int i=0; i<configurations.length;i++){
			Configuration conf = configurations[i];
			TCConfiguration TCconf = new TCConfiguration(conf.getId(),
					conf.getFolds(),
					conf.isIncludeImplicits(), 
					conf.getAssignmentThreshold(),
					conf.getSelectionPolicy().toString());
			TCconfigurationArray[i]=TCconf;
		}
		TCConfigurations TCconfigurations = new TCConfigurations(TCconfigurationArray);
	
		final String expected = MAPPER.writeValueAsString(MAPPER.readValue(fixture("fixtures/TCConfigurations.json"), TCConfigurations.class));
		
		assertThat(MAPPER.writeValueAsString(TCconfigurations)).isEqualTo(expected);
		
	}


}
