package com.rolfje.locker.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Provides secrets to the client based on the certificate of the
 * client and the requested key.
 */
@Path(SecretsResource.PATH)
public class SecretsResource extends SecureHttpsResource {
    private static final Logger logger = LoggerFactory.getLogger(SecretsResource.class);

    public static final String PATH = "secrets";

    @GET
    public String getHello() {
        doAudit();
        return "Secrets for everybody!";
    }
}
