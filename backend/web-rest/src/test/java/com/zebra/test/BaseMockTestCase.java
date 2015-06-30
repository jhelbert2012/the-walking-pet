package com.zebra.test;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;

public abstract class BaseMockTestCase extends TestCase {

    protected IMocksControl control;

    protected void setUp() throws Exception {

        super.setUp();

        control = EasyMock.createControl();

    }

    protected void runTest() throws Throwable {
        super.runTest();
        control.verify();
    }

    protected <T> T createMock(Class<T> clazz) {
        return control.createMock(clazz);
    }

    protected <T> T createMock(String name, Class<T> clazz) {
        return control.createMock(name, clazz);
    }

    protected void replay() {
        control.replay();
    }
}
