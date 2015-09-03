package com.rolfje.locker.resources;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Application;

/**
 * Tests the Hello World SecretsResource
 */
public class SecretsResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(SecretsResource.class);
    }

    @Test
    public void test() {
        final String response = target(SecretsResource.PATH).request().get(String.class);
        Assert.assertEquals("Secrets for everybody!", response);
    }

}