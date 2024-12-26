package com.rs.showcase.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rs.RFC3339DateFormat;
import com.rs.showcase.rest.MdcClearFilter;
import java.time.Clock;
import java.time.ZoneId;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class ShowcaseConfig {

  @Bean
  public Clock systemClock() {
    return Clock.system(ZoneId.of("UTC"));
  }

  @Bean
  public ObjectMapper objectMapper() {
    var objectMapper = new ObjectMapper();
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    JsonMapper.builder().configure(MapperFeature.ALLOW_COERCION_OF_SCALARS, false);
    objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, true);
    objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    objectMapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
    objectMapper.setDateFormat(new RFC3339DateFormat());
    objectMapper.registerModule(new JavaTimeModule());

    return objectMapper;
  }

  @Bean
  public FilterRegistrationBean<MdcClearFilter> mdcClearFilter() {
    var registration = new FilterRegistrationBean<MdcClearFilter>();
    registration.setFilter(new MdcClearFilter());
    registration.addUrlPatterns("/*");
    registration.setOrder(Integer.MAX_VALUE);
    return registration;
  }
}
