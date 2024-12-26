package com.rs.todo.internal.repository;

import com.rs.showcase.db.ClockAwareAuditingEntityListener;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "todos")
@EntityListeners(ClockAwareAuditingEntityListener.class)
@Data
public class TodoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @NotBlank
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT")
  @NotBlank
  private String content;

  @Column(nullable = false)
  @NotNull
  private boolean completed = false;

  @CreatedDate
  @Column(
      name = "created_at",
      nullable = false,
      columnDefinition = "TIMESTAMP WITH TIME ZONE AT TIME ZONE UTC")
  @NotNull
  private OffsetDateTime createdAt;

  @LastModifiedDate
  @Column(
      name = "updated_at",
      nullable = false,
      columnDefinition = "TIMESTAMP WITH TIME ZONE AT TIME ZONE UTC")
  @NotNull
  private OffsetDateTime updatedAt;
}
