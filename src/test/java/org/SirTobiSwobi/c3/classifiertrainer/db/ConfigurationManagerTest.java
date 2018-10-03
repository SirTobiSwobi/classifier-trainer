package org.SirTobiSwobi.c3.classifiertrainer.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfigurationManagerTest {

	@Test
	public void test() {
		ConfigurationManager confMan =  new ConfigurationManager();
		Configuration cfgn = new Configuration(0,2, true, 0.5,SelectionPolicy.MicroaverageF1);
		confMan.setConfiguration(cfgn);
		cfgn = new Configuration(3,3, true, 0.6,SelectionPolicy.MacroaverageF1);
		confMan.setConfiguration(cfgn);
		cfgn = new Configuration(5,5, true, 0.4,SelectionPolicy.MicroaverageRecall);
		confMan.setConfiguration(cfgn);
		
		assertTrue("There are 3 configs",confMan.getSize()==3);
		confMan.deleteConfiguration(1);
		assertTrue("There are only two documents",confMan.getSize()==2);
		assertTrue("First config has id 0",confMan.getByAddress(0).getId()==0);
	}

}
