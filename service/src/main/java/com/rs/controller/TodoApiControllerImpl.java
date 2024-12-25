package com.rs.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.rs.api.V1ApiDelegate;
import com.rs.mapper.TodoEntityDtoMapper;
import com.rs.model.CreateTodoDto;
import com.rs.model.GetTodoDto;
import com.rs.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TodoApiControllerImpl implements V1ApiDelegate {

  private final TodoRepository todoRepository;
  private final TodoEntityDtoMapper todoEntityDtoMapper;

  @Override
  public ResponseEntity<GetTodoDto> createTodo(CreateTodoDto createTodoDto) {
    var entity = todoEntityDtoMapper.fromDto(createTodoDto);
    var savedEntity = todoRepository.save(entity);
    var dto = todoEntityDtoMapper.fromEntity(savedEntity);

    return ResponseEntity.status(CREATED).body(dto);
  }

  @Override
  public ResponseEntity<Void> deleteTodo(Long id) {
    todoRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<GetTodoDto> getTodo(Long id) {
    var entity = todoRepository.findById(id);
    if (entity.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    var dto = todoEntityDtoMapper.fromEntity(entity.get());
    return ResponseEntity.ok(dto);
  }

  @Override
  public ResponseEntity<GetTodoDto> updateTodo(Long id, CreateTodoDto createTodoDto) {
    var optExistingEntity = todoRepository.findById(id);
    if (optExistingEntity.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    var existingEntity = optExistingEntity.get();
    todoEntityDtoMapper.updateEntityFromDto(createTodoDto, existingEntity);

    var updatedEntity = todoRepository.save(existingEntity);
    var dto = todoEntityDtoMapper.fromEntity(updatedEntity);
    return ResponseEntity.ok(dto);
  }
}
