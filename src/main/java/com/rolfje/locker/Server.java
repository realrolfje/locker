package com.rolfje.locker;

import com.rolfje.locker.resources.SecretsResource;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * Simple REST web server.
 */
public class Server {
    private static final Logger log = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws Exception {
        URI baseUri = getBaseURI();
        HttpServer httpServer = startHttpServer(baseUri);

        log.info("Hit enter to stop the server.");
        System.in.read();

        httpServer.shutdownNow();
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost/api")
                .port(8081)
                .build();
    }

    private static HttpServer startHttpServer(URI baseUri) {
        long start = System.currentTimeMillis();

        final ResourceConfig rc = new ResourceConfig()
                .packages(SecretsResource.class.getPackage().getName())
                .setApplicationName("locker");

        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(baseUri, rc);

        log.info("Locker WADL available at {}/application.wadl", baseUri.toString());
        log.info("Server started up in " + (System.currentTimeMillis() - start) + "mS.");
        return httpServer;
    }
}
