package com.zebra.constant.util;

import junit.framework.TestCase;

import com.zebra.constant.model.TestConstant;
import com.zebra.constant.util.ConstantUtil;

public class ConstantUtilTest extends TestCase {

    public void testGetKeyFromI18nValue() {
        assertEquals(TestConstant.VALUE1,
                ConstantUtil.getKeyFromI18nValue(TestConstant.class, "valor número 1"));
    }

}
