package com.rs.repository;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.Clock;
import java.time.OffsetDateTime;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ClockAwareAuditingEntityListener implements ApplicationContextAware {

  private AutowireCapableBeanFactory beanFactory;

  @Override
  public void setApplicationContext(ApplicationContext context) throws BeansException {
    beanFactory = context.getAutowireCapableBeanFactory();
  }

  @PrePersist
  public void setCreatedAt(Object entity) {
    var clock = beanFactory.getBean(Clock.class);

    if (entity instanceof TodoEntity todo) {
      var now = OffsetDateTime.now(clock);
      todo.setCreatedAt(now);
      todo.setUpdatedAt(now);
    }
  }

  @PreUpdate
  public void setUpdatedAt(Object entity) {
    var clock = beanFactory.getBean(Clock.class);

    if (entity instanceof TodoEntity todo) {
      todo.setUpdatedAt(OffsetDateTime.now(clock));
    }
  }
}
