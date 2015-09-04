package com.rolje.locker.crypto;

import junit.framework.TestCase;

/**
 * Tests the JCE Unlimited check.
 */
public class UtilsTest extends TestCase {

    public void testJECUnlimited() throws Exception {
        Utils.validateJCEUnlimited();
    }
}