package com.rolfje.locker.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Provides secrets to the client based on the certificate of the
 * client and the requested key.
 */
@Path(SecretsResource.PATH)
public class SecretsResource {
    public static final String PATH = "secrets";

    @GET
    public String getHello() {
        return "Secrets for everybody!";
    }
}
