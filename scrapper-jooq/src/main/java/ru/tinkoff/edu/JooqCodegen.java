package ru.tinkoff.edu;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

public class JooqCodegen {
    static final PostgreSQLContainer<?> SQL_JOOQ_CONTAINER;
    private static final Path MIGRATIONS_PATH = new File(".")
            .toPath()
            .toAbsolutePath()
            .getParent()
            .resolve("migrations");

    static {
        SQL_JOOQ_CONTAINER = new PostgreSQLContainer<>("postgres:15");
        SQL_JOOQ_CONTAINER.start();

        try (Connection connection = SQL_JOOQ_CONTAINER.createConnection("");
             PostgresDatabase database = new PostgresDatabase()) {
            database.setConnection(new JdbcConnection(connection));
            Liquibase liquibase = new liquibase.Liquibase("master.xml",
                    new DirectoryResourceAccessor(MIGRATIONS_PATH.normalize()), database);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (SQLException | FileNotFoundException | LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws Exception {

        Jdbc jdbc = new Jdbc()
                .withDriver(SQL_JOOQ_CONTAINER.getDriverClassName())
                .withUrl(SQL_JOOQ_CONTAINER.getJdbcUrl())
                .withUser(SQL_JOOQ_CONTAINER.getUsername())
                .withPassword(SQL_JOOQ_CONTAINER.getPassword());

        Database database = new Database()
                .withName("org.jooq.meta.postgres.PostgresDatabase")
                .withProperties(
                        new Property().withKey("rootPath").withValue("migrations"),
                        new Property().withKey("scripts").withValue("master.xml")
                )
                .withIncludes(".*")
                .withExcludes("")
                .withInputSchema("public");

        Generate options = new Generate()
                .withGeneratedAnnotation(true)
                .withGeneratedAnnotationDate(false)
                .withNullableAnnotation(true)
                .withNullableAnnotationType("org.jetbrains.annotations.Nullable")
                .withNonnullAnnotation(true)
                .withNonnullAnnotationType("org.jetbrains.annotations.NotNull")
                .withJpaAnnotations(false)
                .withValidationAnnotations(true)
                .withSpringAnnotations(true)
                .withConstructorPropertiesAnnotation(true)
                .withConstructorPropertiesAnnotationOnPojos(true)
                .withConstructorPropertiesAnnotationOnRecords(true)
                .withFluentSetters(false)
                .withDaos(false)
                .withPojos(true);

        Target target = new Target()
                .withPackageName("ru.tinkoff.edu.java.scrapper.domain.jooq.generated")
                .withDirectory("scrapper/src/main/java");

        Configuration configuration = new Configuration()
                .withJdbc(jdbc)
                .withGenerator(
                        new Generator()
                                .withDatabase(database)
                                .withGenerate(options)
                                .withTarget(target)
                                .withStrategy(new Strategy()
                                        .withName("org.jooq.codegen.DefaultGeneratorStrategy"))
                );

        GenerationTool.generate(configuration);
    }
}
