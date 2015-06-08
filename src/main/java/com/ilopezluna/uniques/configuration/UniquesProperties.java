package com.ilopezluna.uniques.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by ignasi on 7/6/15.
 */
public class UniquesProperties {

    private final static Logger logger = LoggerFactory.getLogger(UniquesProperties.class);

    private final static String resourceName = "config.properties";

    private static Properties properties;

    private static Properties getProperties() throws IOException {

        if (properties != null) {
            return properties;
        }
        InputStream input = null;
        try {
            properties = new Properties();
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
                properties.load(resourceStream);
            }
            return properties;
        }
        finally {
            if (input != null)
                input.close();
        }
    }

    public static String getProperty(String key) {
        try {
            return (String) getProperties().get(key);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
