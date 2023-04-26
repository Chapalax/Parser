package ru.tinkoff.edu;

import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.*;

public class JooqCodegen {
    public static void main(String[] args) throws Exception {

        Jdbc jdbc = new Jdbc()
                .withDriver("org.postgresql.Driver")
                .withUrl("jdbc:postgresql://localhost:5432/scrapper")
                .withUser("user")
                .withPassword("secret");

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
