package com.rs;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@Slf4j
public abstract class BaseControllerIT extends BaseIT {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @BeforeEach
  protected void beforeEach() {
    RestAssuredMockMvc.mockMvc(mockMvc);
  }

  public String toJson(Object o) {
    return toJson(o, true);
  }

  @SneakyThrows
  public String toJson(Object o, boolean condensed) {
    var str = objectMapper.writeValueAsString(o);

    if (condensed) {
      return str.replaceAll("\\R", " ").replaceAll("\\s+", " ").trim();
    }
    return str;
  }
}
