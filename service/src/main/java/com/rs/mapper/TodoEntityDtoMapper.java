package com.rs.mapper;

import com.rs.model.CreateTodoDto;
import com.rs.model.GetTodoDto;
import com.rs.repository.TodoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TodoEntityDtoMapper {

  TodoEntity fromDto(CreateTodoDto createTodoDto);

  GetTodoDto fromEntity(TodoEntity todoEntity);

  void updateEntityFromDto(CreateTodoDto dto, @MappingTarget TodoEntity entity);
}
