package org.example.util;

import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
    private Properties properties;

    public PropertyReader(String filename) {
        try {
            properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream(filename));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public Properties getProperties() {
        return properties;
    }
}
