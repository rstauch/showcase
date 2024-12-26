package com.rs;

import static com.rs.BaseIT.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.junit5.api.DBRider;
import jakarta.annotation.PostConstruct;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import(TestConfig.class)
@ActiveProfiles(IT_PROFILE)
@DBRider
@DBUnit(
    caseInsensitiveStrategy = Orthography.LOWERCASE,
    caseSensitiveTableNames = false,
    alwaysCleanBefore = true,
    alwaysCleanAfter = false,
    cacheTableNames = false,
    cacheConnection = false)
@Slf4j
@RunWith(SpringRunner.class)
public abstract class BaseIT {

  static {
    System.setProperty("user.timezone", "GMT");
  }

  @PostConstruct
  void started() {
    TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
  }

  public static final String IT_PROFILE = "test";
  public static final String FLYWAY_SCHEMA_TABLE = "mps_flyway_schema_history";

  @TestConfiguration
  public static class TestConfig {

    public static final OffsetDateTime FIXED_TIME =
        OffsetDateTime.of(2025, 4, 1, 6, 19, 10, 0, ZoneOffset.UTC);

    @Bean
    @Primary
    public Clock fixedClock() {
      return Clock.fixed(FIXED_TIME.toInstant(), ZoneOffset.UTC);
    }
  }
}
