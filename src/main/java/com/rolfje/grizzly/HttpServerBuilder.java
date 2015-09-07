package com.rolfje.grizzly;

import com.rolfje.locker.Configuration;
import com.rolfje.locker.resources.SecretsResource;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.accesslog.AccessLogBuilder;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * Builds a Grizzly HttpServer.
 */
public class HttpServerBuilder {
    private static final Logger log = LoggerFactory.getLogger(HttpServerBuilder.class);

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

        HttpServer httpServer = buildServer(resourceConfig);

        final AccessLogBuilder builder = new AccessLogBuilder(Configuration.getAccessLogFile());
        builder.instrument(httpServer.getServerConfiguration());

        if (start) {
            try {
                httpServer.start();
            } catch (IOException e) {
                log.error("Failed to start the httpserver.", e);
            }
        }

        return httpServer;

    }

    private HttpServer buildServer(ResourceConfig resourceConfig) {
        if (secure) {
            SSLEngineConfigurator sslEngineConfigurator = new SSLEngineConfiguratorBuilder()
                    .withKeyStoreFile(Configuration.getServerKeyStore())
                    .withKeyStorePass(Configuration.getServerKeystorePassword())
                    .build();

            return GrizzlyHttpServerFactory
                    .createHttpServer(baseUri, resourceConfig, secure, sslEngineConfigurator, false);
        } else {
            return GrizzlyHttpServerFactory
                    .createHttpServer(baseUri, resourceConfig, false);
        }
    }

    public HttpServer buildAndStart() {
        start = true;
        return build();
    }
}
