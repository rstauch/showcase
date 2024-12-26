package com.rs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.tc")
public class TestContainerConfigurationProperties {

  /** The port to expose the PostgreSQL container on. */
  private Integer postgresPort = 0;
}
