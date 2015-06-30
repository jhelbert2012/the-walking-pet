package com.zebra.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.easymock.IMocksControl;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public abstract class BaseSpringMockTestCase extends AbstractDependencyInjectionSpringContextTests {

    // automatically injected by Spring
    protected IMocksControl control;

    public void setControl(IMocksControl control) {
        this.control = control;
    }

    @Override
    protected void runTest() throws Throwable {

        configureMethodMocks();

        control.replay();

        super.runTest();

        control.verify();

        control.reset();

        setDirty();
    }

    protected abstract void configureMocks();

    private void configureMethodMocks() {
        String testMethodName = getName();

        try {
            testMethodName =
                    testMethodName.substring(0, 1).toUpperCase() + testMethodName.substring(1);

            // Setup commons mocks
            configureMocks();

            // Obtain the method to configure this particular test.
            Method testMethod = getClass().getDeclaredMethod("configure" + testMethodName);
            testMethod.setAccessible(true);
            testMethod.invoke(this);

        } catch (SecurityException e) {
            // Do not nothing... does not exist configure method for this test
            logger.warn(e.getMessage());
        } catch (NoSuchMethodException e) {
            // Do not nothing... does not exist configure method for this test
            logger.warn(e.getMessage());
        } catch (IllegalArgumentException e) {
            // Do not nothing... does not exist configure method for this test
            logger.warn(e.getMessage());
        } catch (IllegalAccessException e) {
            // Do not nothing... does not exist configure method for this test
            logger.warn(e.getMessage());
        } catch (InvocationTargetException e) {
            // Do not nothing... does not exist configure method for this test
            logger.warn(e.getMessage());
        }
    }

    /**
     * Default location of application context definition is the same package,
     * and the default name is className + "Beans.xml"
     */
    @Override
    protected String[] getConfigLocations() {
        String defaultApplicationContext =
                this.getClass().getCanonicalName().replace('.', '/') + "Beans.xml";
        String[] configLocations = { defaultApplicationContext };

        return configLocations;
    }

}
