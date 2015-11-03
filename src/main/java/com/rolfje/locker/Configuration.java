package com.rolfje.locker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * Class to hold static configuration. This class is here so we can
 * refactor configuration later to something more dynamic.
 */
public class Configuration {
    private static final Logger log = LoggerFactory.getLogger(Configuration.class);

    public static File getServerKeyStore() {
        return getClasspathResources("default.jks");
    }

    public static char[] getServerKeystorePassword() {
        return "defaultpassword".toCharArray();
    }

    public static URI getBaseURI() {
        return UriBuilder.fromUri("https://localhost/api")
                .port(8443)
                .build();
    }

    private static File getClasspathResources(String fileName) {
        URL resource = Configuration.class
                .getClassLoader()
                .getResource(fileName);

        if (resource == null) {
            log.error("File " + fileName + " not found on the classpath.");
            return null;
        }

        return new File(resource.getFile());
    }

    public static File getAccessLogFile() {
        try {
            File tempFile = File.createTempFile("locker-access.", ".log");
            log.info("Access log will be written to " + tempFile.getAbsolutePath());
            return tempFile;
        } catch (IOException e) {
            log.error("Can not create temp file for access logging.", e);
            return null;
        }
    }
}