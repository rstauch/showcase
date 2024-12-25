package com.rs;

import static com.rs.BaseIT.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@Profile("!" + IT_PROFILE)
@TestConfiguration(proxyBeanMethods = false)
@Slf4j
public class Showcase {

  private static final String POSTGRES_CONTAINER_VERSION = "17.2-alpine3.21";
  private static final String POSTGRES_DB_NAME = "showcase";
  private static final String POSTGRES_USERNAME = "user";
  private static final String POSTGRES_PASSWORD = "password";

  @Bean
  @ServiceConnection
  public JdbcDatabaseContainer postgreSqlContainer(
      // FIXME: custom property processing
      @Value("${app.tc.postgres.port:0}") Integer postgresPort) {
    var container =
        new PostgreSQLContainer<>(DockerImageName.parse("postgres:" + POSTGRES_CONTAINER_VERSION))
            .withDatabaseName(POSTGRES_DB_NAME)
            .withUsername(POSTGRES_USERNAME)
            .withPassword(POSTGRES_PASSWORD)
            .withStartupTimeoutSeconds(300);

    if (postgresPort != null && postgresPort > 0) {
      container.addExposedPort(postgresPort);
      container.setPortBindings(List.of(postgresPort + ":5432"));
    }
    return container;
  }

  // application.properties & application-tc.properties are active from here
  public static void main(String[] args) {
    SpringApplication.from(Main::main).with(Showcase.class).withAdditionalProfiles("tc").run(args);
  }
}
