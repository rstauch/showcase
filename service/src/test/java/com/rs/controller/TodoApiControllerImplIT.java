package com.rs.controller;

import static com.rs.BaseIT.TestConfig.FIXED_TIME;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpStatus.*;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.DataSetFormat;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.github.database.rider.core.api.exporter.ExportDataSet;
import com.rs.model.CreateTodoDto;
import com.rs.model.GetTodoDto;
import org.junit.jupiter.api.Test;

class TodoApiControllerImplIT extends BaseControllerIT {

  @Test
  @DataSet(
      skipCleaningFor = FLYWAY_SCHEMA_TABLE,
      cleanBefore = true,
      transactional = false,
      cleanAfter = false,
      disableConstraints = true,
      strategy = SeedStrategy.TRUNCATE_INSERT)
  @ExportDataSet(
      format = DataSetFormat.YML,
      outputName = "target/exported/TodoApiControllerImplIT/return201AndCreateNewTodo.yaml",
      includeTables = {"todos"})
  @ExpectedDataSet(
      value = {"datasets/todo/dataset.yaml"},
      ignoreCols = "id")
  void return201AndCreateNewTodo() {
    var createTodoDto = new CreateTodoDto();
    createTodoDto.setTitle("some-title");
    createTodoDto.setContent("some-content");

    var json = toJson(createTodoDto);

    var result =
        given()
            .body(json)
            .header("Content-Type", "application/json")
            .when()
            .post("/api/v1/todo")
            .then()
            .statusCode(CREATED.value())
            .extract()
            .as(GetTodoDto.class);

    assertAll(
        () -> assertThat(result.getId()).isNotNull(),
        () -> assertThat(result.getTitle()).isEqualTo(createTodoDto.getTitle()),
        () -> assertThat(result.getContent()).isEqualTo(createTodoDto.getContent()),
        () -> assertThat(result.getCompleted()).isFalse(),
        () -> assertThat(result.getCreatedAt()).isEqualTo(FIXED_TIME),
        () -> assertThat(result.getUpdatedAt()).isEqualTo(FIXED_TIME));
  }

  @Test
  @DataSet(
      value = {"datasets/todo/dataset.yaml"},
      skipCleaningFor = FLYWAY_SCHEMA_TABLE,
      cleanBefore = true,
      transactional = false,
      cleanAfter = false,
      disableConstraints = true,
      strategy = SeedStrategy.TRUNCATE_INSERT)
  @ExportDataSet(
      format = DataSetFormat.YML,
      outputName = "target/exported/TodoApiControllerImplIT/return204AndDeleteExistingTodo.yaml",
      includeTables = {"todos"})
  @ExpectedDataSet(value = {"datasets/todo/empty.yaml"})
  void return204AndDeleteExistingTodo() {
    given()
        .header("Content-Type", "application/json")
        .when()
        .delete("/api/v1/todo/1")
        .then()
        .statusCode(NO_CONTENT.value());
  }

  @Test
  @DataSet(
      skipCleaningFor = FLYWAY_SCHEMA_TABLE,
      cleanBefore = true,
      transactional = false,
      cleanAfter = false,
      disableConstraints = true,
      strategy = SeedStrategy.TRUNCATE_INSERT)
  @ExportDataSet(
      format = DataSetFormat.YML,
      outputName = "target/exported/TodoApiControllerImplIT/return204IfTodoDoesNotExist.yaml",
      includeTables = {"todos"})
  @ExpectedDataSet(value = {"datasets/todo/empty.yaml"})
  void return204IfTodoDoesNotExist() {
    given()
        .header("Content-Type", "application/json")
        .when()
        .delete("/api/v1/todo/1")
        .then()
        .statusCode(NO_CONTENT.value());
  }

  @Test
  @DataSet(
      value = {"datasets/todo/dataset.yaml"},
      skipCleaningFor = FLYWAY_SCHEMA_TABLE,
      cleanBefore = true,
      transactional = false,
      cleanAfter = false,
      disableConstraints = true,
      strategy = SeedStrategy.TRUNCATE_INSERT)
  @ExportDataSet(
      format = DataSetFormat.YML,
      outputName = "target/exported/TodoApiControllerImplIT/return404IfTodoDoesNotExist.yaml",
      includeTables = {"todos"})
  @ExpectedDataSet(value = {"datasets/todo/dataset.yaml"})
  void return404IfTodoDoesNotExist() {
    given().when().get("/api/v1/todo/9999").then().statusCode(NOT_FOUND.value());
  }

  @Test
  @DataSet(
      value = {"datasets/todo/dataset.yaml"},
      skipCleaningFor = FLYWAY_SCHEMA_TABLE,
      cleanBefore = true,
      transactional = false,
      cleanAfter = false,
      disableConstraints = true,
      strategy = SeedStrategy.TRUNCATE_INSERT)
  @ExportDataSet(
      format = DataSetFormat.YML,
      outputName = "target/exported/TodoApiControllerImplIT/return404IfTodoDoesNotExist.yaml",
      includeTables = {"todos"})
  @ExpectedDataSet(value = {"datasets/todo/dataset.yaml"})
  void returnExistingTodo() {
    var result =
        given()
            .header("Content-Type", "application/json")
            .when()
            .get("/api/v1/todo/1")
            .then()
            .statusCode(OK.value())
            .extract()
            .as(GetTodoDto.class);

    assertAll(
        () -> assertThat(result.getId()).isNotNull(),
        () -> assertThat(result.getTitle()).isEqualTo("some-title"),
        () -> assertThat(result.getContent()).isEqualTo("some-content"),
        () -> assertThat(result.getCompleted()).isFalse(),
        () -> assertThat(result.getCreatedAt()).isEqualTo(FIXED_TIME),
        () -> assertThat(result.getUpdatedAt()).isEqualTo(FIXED_TIME));
  }

  @Test
  @DataSet(
      value = {"datasets/todo/dataset.yaml"},
      skipCleaningFor = FLYWAY_SCHEMA_TABLE,
      cleanBefore = true,
      transactional = false,
      cleanAfter = false,
      disableConstraints = true,
      strategy = SeedStrategy.TRUNCATE_INSERT)
  @ExportDataSet(
      format = DataSetFormat.YML,
      outputName = "target/exported/TodoApiControllerImplIT/return200AndUpdateTodo.yaml",
      includeTables = {"todos"})
  @ExpectedDataSet(value = {"datasets/todo/dataset_updated.yaml"})
  void return200AndUpdateTodo() {
    var updatedTodoDto = new CreateTodoDto();
    updatedTodoDto.setTitle("some-updated-title");
    updatedTodoDto.setContent("some-updated-content");
    updatedTodoDto.setCompleted(true);

    var json = toJson(updatedTodoDto);

    var result =
        given()
            .body(json)
            .header("Content-Type", "application/json")
            .when()
            .put("/api/v1/todo/1")
            .then()
            .statusCode(OK.value())
            .extract()
            .as(GetTodoDto.class);

    assertAll(
        () -> assertThat(result.getId()).isNotNull(),
        () -> assertThat(result.getTitle()).isEqualTo(updatedTodoDto.getTitle()),
        () -> assertThat(result.getContent()).isEqualTo(updatedTodoDto.getContent()),
        () -> assertThat(result.getCompleted()).isTrue(),
        () -> assertThat(result.getCreatedAt()).isEqualTo(FIXED_TIME),
        () -> assertThat(result.getUpdatedAt()).isEqualTo(FIXED_TIME));
  }
}
