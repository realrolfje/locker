package com.rolfje.locker;

import javax.ws.rs.core.UriBuilder;
import java.io.File;
import java.net.URI;

/**
 * Class to hold static configuration. This class is here so we can
 * refactor configuration later to something more dynamic.
 */
public class Configuration {

    public static File getServerKeyStore() {
        return new File("serverkeystore.jks");
    }

    public static char[] getServerKeystorePassword(){
        return "supersecretpassword".toCharArray();
    }

    public static URI getBaseURI(){
        return UriBuilder.fromUri("http://localhost/api")
                .port(8081)
                .build();
    }
}