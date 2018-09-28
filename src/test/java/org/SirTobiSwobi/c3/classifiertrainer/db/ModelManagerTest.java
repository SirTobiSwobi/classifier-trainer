package org.SirTobiSwobi.c3.classifiertrainer.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class ModelManagerTest {

	@Test
	public void test() {
		Model model = new Model(0,1);
		ModelManager modMan = new ModelManager();
		
		modMan.setModel(model);
		modMan.addModelWithoutId(1);
		
		assertTrue("There are two models",modMan.getSize()==2);
		modMan.deleteModel(1);
		assertTrue("There is only one document",modMan.getSize()==1);
		assertTrue("First model has config-id 1",modMan.getModelByAddress(0).getConfigurationId()==1);
	}

}
