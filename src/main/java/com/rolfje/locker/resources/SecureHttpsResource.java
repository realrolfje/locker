package com.rolfje.locker.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 * Provides functionality for authenticating and authorizing calls based on SSL certificate.
 */
public class SecureHttpsResource {
    private static final Logger logger = LoggerFactory.getLogger(SecureHttpsResource.class);

    @Context
    SecurityContext security;

    @Context
    private UriInfo context;

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
