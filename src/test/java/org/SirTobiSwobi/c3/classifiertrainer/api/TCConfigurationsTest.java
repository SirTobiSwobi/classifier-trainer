package org.SirTobiSwobi.c3.classifiertrainer.api;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

import org.SirTobiSwobi.c3.classifiertrainer.db.Configuration;
import org.SirTobiSwobi.c3.classifiertrainer.db.ConfigurationManager;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;

public class TCConfigurationsTest {

	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

	@Test
	public void serializesToJSON() throws Exception {
		
		ConfigurationManager confMan =  new ConfigurationManager();
		confMan.addConfigurationWithoutId("This needs to be replaced with an actual configuration in the actual classifier trainer");
		confMan.addConfigurationWithoutId("As does this");
		confMan.setConfiguration(new Configuration(5));
		
		
		Configuration[] configurations = confMan.getConfigurationArray();
		TCConfiguration[] TCconfigurationArray = new TCConfiguration[configurations.length];
		for(int i=0; i<configurations.length;i++){
			Configuration conf = configurations[i];
			TCConfiguration TCconf = new TCConfiguration(conf.getId());
			TCconfigurationArray[i]=TCconf;
		}
		TCConfigurations TCconfigurations = new TCConfigurations(TCconfigurationArray);
	
		final String expected = MAPPER.writeValueAsString(MAPPER.readValue(fixture("fixtures/TCConfigurations.json"), TCConfigurations.class));
		
		assertThat(MAPPER.writeValueAsString(TCconfigurations)).isEqualTo(expected);
		
	}


}
