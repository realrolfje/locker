package com.rolfje.locker;

import javax.ws.rs.core.UriBuilder;
import java.io.File;
import java.net.URI;
import java.net.URL;

/**
 * Class to hold static configuration. This class is here so we can
 * refactor configuration later to something more dynamic.
 */
public class Configuration {

    public static File getServerKeyStore() {
        return getResources();
    }

    public static char[] getServerKeystorePassword(){
        return "defaultpassword".toCharArray();
    }

    public static URI getBaseURI(){
        return UriBuilder.fromUri("https://localhost/api")
                .port(8443)
                .build();
    }

    private static File getResources() {
        URL resource = Configuration.class
                .getClassLoader()
                .getResource("default.jks");
        return new File(resource.getFile());
    }
}