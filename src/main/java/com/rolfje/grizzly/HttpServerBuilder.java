package com.rolfje.grizzly;

import com.rolfje.locker.Configuration;
import com.rolfje.locker.resources.SecretsResource;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.File;
import java.net.URI;

/**
 * Builds a Grizzly HttpServer.
 */
public class HttpServerBuilder {
    private File keyStoreFile;
    private char[] keyStorePass;

    private boolean secure = false;
    private boolean start = false;
    private String applicationName = "application";
    private URI baseUri;

    public HttpServerBuilder withKeyStoreFile(File keyStoreFile) {
        this.secure = true;
        this.keyStoreFile = keyStoreFile;
        return this;
    }

    public HttpServerBuilder withKeyStorePass(char[] password) {
        this.secure = true;
        this.keyStorePass = password;
        return this;
    }

    public HttpServerBuilder withApplicationName(String applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    public HttpServerBuilder withBaseURI(URI baseURI) {
        this.baseUri = baseURI;
        return this;
    }

    public HttpServer build() {
        final ResourceConfig resourceConfig = new ResourceConfig()
                .packages(SecretsResource.class.getPackage().getName())
                .setApplicationName(applicationName);

        if (secure) {
            SSLEngineConfigurator sslEngineConfigurator = new SSLEngineConfiguratorBuilder()
                    .withKeyStoreFile(Configuration.getServerKeyStore())
                    .withKeyStorePass(Configuration.getServerKeystorePassword())
                    .build();

            return GrizzlyHttpServerFactory
                    .createHttpServer(baseUri, resourceConfig, secure, sslEngineConfigurator, start);
        } else {
            return GrizzlyHttpServerFactory
                    .createHttpServer(baseUri, resourceConfig, start);
        }
    }

    public HttpServer buildAndStart() {
        start = true;
        return build();
    }
}
