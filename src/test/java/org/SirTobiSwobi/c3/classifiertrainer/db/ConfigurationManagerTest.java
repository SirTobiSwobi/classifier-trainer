package org.SirTobiSwobi.c3.classifiertrainer.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfigurationManagerTest {

	@Test
	public void test() {
		Configuration config = new Configuration(0);
		ConfigurationManager confMan = new ConfigurationManager();
		
		confMan.setConfiguration(config);
		confMan.addConfigurationWithoutId("This is not used here");
		
		assertTrue("There are two documents",confMan.getSize()==2);
		confMan.deleteConfiguration(1);
		assertTrue("There is only one document",confMan.getSize()==1);
		assertTrue("First config has id 0",confMan.getByAddress(0).getId()==0);
	}

}
