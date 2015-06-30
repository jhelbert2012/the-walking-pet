package com.zebra.test;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public abstract class BaseSpringTestCase extends
		AbstractDependencyInjectionSpringContextTests {

	@Override
	protected String[] getConfigLocations() {
		String defaultApplicationContext = BaseSpringTestCase.class
				.getCanonicalName().replace('.', '/') + "Beans.xml";
		String[] configLocations = { defaultApplicationContext };

		return configLocations;
	}

}
