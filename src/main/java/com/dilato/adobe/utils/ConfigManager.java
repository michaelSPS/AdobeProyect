package com.dilato.adobe.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    // Método reutilizable para cargar cualquier archivo .properties desde el classpath
    public static String getProperty(String key, String filePath) throws IOException {
        InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(filePath);
        if (input == null) {
            throw new IOException("❌ File not found in classpath: " + filePath);
        }

        Properties pr = new Properties();
        pr.load(input);
        String value = pr.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("❌ Key '" + key + "' not found in file: " + filePath);
        }
        return value;
    }

    // Acceso específico a config.properties (ubicado en src/main/resources/configfiles/)
    public static String getConfig(String key) throws IOException {
        return getProperty(key, "configfiles/config.properties");
    }

    // Acceso específico a locators.properties (ubicado en src/main/resources/configfiles/)
    public static String getLocator(String key) throws IOException {
        return getProperty(key, "configfiles/locators.properties");
    }
}