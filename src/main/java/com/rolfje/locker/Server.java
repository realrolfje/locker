package com.rolfje.locker;

import com.rolfje.grizzly.HttpServerBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

/**
 * Simple REST web server.
 */
public class Server {
    private static final Logger log = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws Exception {
        HttpServer httpServer = startHttpServer();

        log.info("Hit enter to stop the server.");
        System.in.read();

        httpServer.shutdownNow();
    }

    private static HttpServer startHttpServer() {
        long startMillis = System.currentTimeMillis();

        URI baseUri = Configuration.getBaseURI();
        HttpServer httpServer = new HttpServerBuilder()
                .withApplicationName("locker")
                .withBaseURI(baseUri)
                .withKeyStoreFile(Configuration.getServerKeyStore())
                .withKeyStorePass(Configuration.getServerKeystorePassword())
                .buildAndStart();

        log.info("Locker WADL available at {}/application.wadl", baseUri.toString());
        log.info("Server started up in " + (System.currentTimeMillis() - startMillis) + "mS.");
        return httpServer;
    }
}
