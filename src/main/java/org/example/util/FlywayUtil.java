package org.example.util;

import org.flywaydb.core.Flyway;

import java.util.Properties;

public class FlywayUtil {
    private static final String URL_KEY = "hibernate.connection.url";
    private static final String USERNAME_KEY = "hibernate.connection.username";
    private static final String PASSWORD_KEY = "hibernate.connection.password";

    public static void migrate() {
        Properties properties = new PropertyReader("hibernate.properties").getProperties();
        String url = properties.getProperty(URL_KEY);
        String username = properties.getProperty(USERNAME_KEY);
        String password = properties.getProperty(PASSWORD_KEY);

        Flyway flyway = Flyway.configure()
                .dataSource(url, username, password)
                .baselineOnMigrate(true)
                .load();
        flyway.migrate();
    }

    private FlywayUtil() {}
}
