package com.rs.todo.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.rs.api.V1ApiDelegate;
import com.rs.model.CreateTodoDto;
import com.rs.model.GetTodoDto;
import com.rs.todo.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TodoApiControllerImpl implements V1ApiDelegate {

  private final TodoService todoService;

  @Override
  public ResponseEntity<GetTodoDto> createTodo(CreateTodoDto createTodoDto) {
    var dto = todoService.createTodo(createTodoDto);
    return ResponseEntity.status(CREATED).body(dto);
  }

  @Override
  public ResponseEntity<Void> deleteTodo(Long id) {
    todoService.deleteTodo(id);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<GetTodoDto> getTodo(Long id) {
    var dto = todoService.getTodo(id);
    if (dto.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(dto.get());
  }

  @Override
  public ResponseEntity<GetTodoDto> updateTodo(Long id, CreateTodoDto createTodoDto) {
    var dto = todoService.updateTodo(id, createTodoDto);
    if (dto.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(dto.get());
  }
}
