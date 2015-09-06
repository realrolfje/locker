package com.rolfje.grizzly;

import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import java.io.File;

/**
 * Builds the {@link org.glassfish.grizzly.ssl.SSLEngineConfigurator} for
 * the {@link GrizzlyHttpServerFactory} which us started in the
 * {@link com.rolfje.locker.Server}.
 */
public class SSLEngineConfiguratorBuilder {

    private File keyStoreFile;
    private char[] keyStorePass;


    public SSLEngineConfiguratorBuilder withKeyStoreFile(File keyStoreFile) {
        this.keyStoreFile = keyStoreFile;
        return this;
    }

    public SSLEngineConfiguratorBuilder withKeyStorePass(char[] password) {
        this.keyStorePass = password;
        return this;
    }

    public SSLEngineConfigurator build() {
        SSLContextConfigurator sslContextConfigurator = new SSLContextConfigurator();
        sslContextConfigurator.setKeyStoreFile(keyStoreFile.getAbsolutePath());
        sslContextConfigurator.setKeyStorePass(keyStorePass);

        return new SSLEngineConfigurator(sslContextConfigurator);
    }


}
