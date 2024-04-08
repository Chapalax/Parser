package integration;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.jetbrains.annotations.NotNull;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Used for setting up common environment for intergration (PostreSQL container & database, liquibase, path to migrations, JDBC connection and properties). Used for integration testing.
 * Every class which integration is to be tested has to be extended from current class.
 * @author Maxim Berezhnoy
 * @version 1.0 
 */

@Testcontainers
public abstract class IntegrationEnvironment {
    static final PostgreSQLContainer<?> SQL_CONTAINER;
    private static final Path MIGRATIONS_PATH = new File(".")
            .toPath()
            .toAbsolutePath()
            .getParent()
            .getParent()
            .resolve("migrations");

    static {
        SQL_CONTAINER = new PostgreSQLContainer<>("postgres:15");
        SQL_CONTAINER.start();

        try (Connection connection = SQL_CONTAINER.createConnection("");
             PostgresDatabase database = new PostgresDatabase()) {
            database.setConnection(new JdbcConnection(connection));
            Liquibase liquibase = new liquibase.Liquibase("master.xml",
                    new DirectoryResourceAccessor(MIGRATIONS_PATH.normalize()), database);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (SQLException | FileNotFoundException | LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }

    @DynamicPropertySource
    static void jdbcProperties(@NotNull DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", SQL_CONTAINER::getPassword);
    }
}
