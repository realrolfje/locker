package com.rolfje.locker.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 * Provides secrets to the client based on the certificate of the
 * client and the requested key.
 */
@Path(SecretsResource.PATH)
public class SecretsResource {
    private static final Logger logger = LoggerFactory.getLogger(SecretsResource.class);

    public static final String PATH = "secrets";

    @Context
    private UriInfo context;

    @Context
    SecurityContext security;

    @GET
    public String getHello() {
        doAudit();
        return "Secrets for everybody!";
    }


    protected void doAudit() {
        String userPrincipal = getUserPrincipal();
        String requestURI = getRequestURI();
        logger.info("AUDIT: User {} accessed URL {}.", userPrincipal, requestURI);
    }

    private String getRequestURI() {
        String requestURI = "UNKNOWN";
        if (context != null && context.getRequestUri() != null) {
            requestURI = context.getRequestUri().toString();
        } else {
            logger.info("URIContext or RequestUri was null.");
        }
        return requestURI;
    }

    private String getUserPrincipal() {
        String userPrincipal = "UNKNOWN";
        if (security != null && security.getUserPrincipal() != null) {
            userPrincipal = security.getUserPrincipal().toString();
        } else {
            logger.debug("SecurityContext or UserPrincipal was null.");
        }
        return userPrincipal;
    }
}
