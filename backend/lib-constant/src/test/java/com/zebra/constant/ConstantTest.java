package com.zebra.constant;

import java.util.Locale;

import junit.framework.TestCase;

import com.zebra.constant.model.TestConstant;
import com.zebra.constant.util.ConstantUtil;

public class ConstantTest extends TestCase {

    public void testToString() {
        assertEquals("VALUE1", TestConstant.VALUE1.toString());
    }

    public void testName() {
        assertEquals("VALUE1", TestConstant.VALUE1.name());
    }

    public void testKet() {
        assertEquals("VALUE1", ConstantUtil.key(TestConstant.VALUE1));
    }

    public void testValue() {
        assertEquals("valor número 1", ConstantUtil.value(TestConstant.VALUE1, new Locale("es")));
    }

}