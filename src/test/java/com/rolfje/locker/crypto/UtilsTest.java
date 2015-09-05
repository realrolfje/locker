package com.rolfje.locker.crypto;

import junit.framework.TestCase;

/**
 * Tests the JCE Unlimited check.
 */
public class UtilsTest extends TestCase {

    public void testJECUnlimited() throws Exception {
        /* Because everyone has the right, no, duty to install strong crypto */
        Utils.validateJCEUnlimited();
    }
}