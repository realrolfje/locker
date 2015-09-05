package com.rolfje.locker;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * Simple REST web server.
 */
public class Server {

    public static void main(String[] args) throws Exception {
        URI baseUri = UriBuilder.fromUri("http://localhost/api")
                .port(8081)
                .build();

        final ResourceConfig rc = new ResourceConfig().packages("com.rolfje.locker.resources");
        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(baseUri, rc);

        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", baseUri.toString()));

        System.in.read();
        httpServer.shutdownNow();
    }
}
