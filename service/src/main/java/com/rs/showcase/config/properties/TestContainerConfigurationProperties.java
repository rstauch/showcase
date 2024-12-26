package com.rs.showcase.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.tc")
public class TestContainerConfigurationProperties {

  /** The port to expose the PostgreSQL container on. A non 0 zero value results in a fixed port. */
  private Integer postgresPort = 0;
}
