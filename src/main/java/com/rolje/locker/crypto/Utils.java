package com.rolje.locker.crypto;

import javax.crypto.Cipher;
import java.security.NoSuchAlgorithmException;

/**
 * Collection of cryptography utilities we might need to add later to other classes.
 */
public class Utils {

    /* Validate installation of Java Cryptography Extension */
    static {
        validateJCEUnlimited();
    }

    public static void validateJCEUnlimited() {
        final int unlimited = 2_147_483_647; /* 32 bit max int */

        // Ciphers to check for installation of the Java Cryptography Extension (JCE) unlimited strength jurisdiction policy files
        final String[] ciphers = {
                "AES", "BouncyCastle", "X.509",
                "PKCS12", "BCPKCS12", "PKCS12-DEF",
                "DES", "DESEDE", "RSA", "DSA",
                "SHA-1", "SHA-256", "SHA-512"
        };

        for (String cipher : ciphers) {
            int keyLength = 0;
            try {
                keyLength = Cipher.getMaxAllowedKeyLength(cipher);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Problem while checking the maximum key length of cipher " + cipher + ".", e);
            }

            if (keyLength < unlimited) {
                String msg = String.format("The maximum allowed key length for cipher %s was %d.\n" +
                        "This indicates that you might not have installed the Java Cryptography \n" +
                        "Extension (JCE) unlimited strength jurisdiction policy files in your JVM.\n" +
                        "To do so, download these policy files at:\n\n" +
                        "Java 6: http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html\n" +
                        "Java 7: http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html\n" +
                        "Java 8: http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html\n\n" +
                        "Then, copy local_policy.jar and US_export_policy.jar extracted from above zip file to\n" +
                        "the $JAVA_HOME/jre/lib/security directory.\n", cipher, keyLength);
                throw new RuntimeException(msg);
            }
        }
    }
}
