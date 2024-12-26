package com.rs.todo;

import com.rs.model.CreateTodoDto;
import com.rs.model.GetTodoDto;
import com.rs.todo.internal.mapper.TodoEntityDtoMapper;
import com.rs.todo.internal.repository.TodoRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoService {

  private final TodoRepository todoRepository;
  private final TodoEntityDtoMapper todoEntityDtoMapper;

  public GetTodoDto createTodo(CreateTodoDto createTodoDto) {
    var entity = todoEntityDtoMapper.fromDto(createTodoDto);
    var savedEntity = todoRepository.save(entity);
    return todoEntityDtoMapper.fromEntity(savedEntity);
  }

  public void deleteTodo(Long id) {
    todoRepository.deleteById(id);
  }

  public Optional<GetTodoDto> getTodo(Long id) {
    var entity = todoRepository.findById(id);
    if (entity.isEmpty()) {
      return Optional.empty();
    }

    var dto = todoEntityDtoMapper.fromEntity(entity.get());
    return Optional.of(dto);
  }

  public Optional<GetTodoDto> updateTodo(Long id, CreateTodoDto createTodoDto) {
    var optExistingEntity = todoRepository.findById(id);
    if (optExistingEntity.isEmpty()) {
      return Optional.empty();
    }

    var existingEntity = optExistingEntity.get();
    todoEntityDtoMapper.updateEntityFromDto(createTodoDto, existingEntity);

    var updatedEntity = todoRepository.save(existingEntity);
    var dto = todoEntityDtoMapper.fromEntity(updatedEntity);
    return Optional.of(dto);
  }
}
