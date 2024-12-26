package com.rs;

import com.tngtech.archunit.core.domain.JavaClass;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

public class ArchTest {

  @Test
  void verifiesModularStructure() {
    var modules =
        ApplicationModules.of(
            Main.class, JavaClass.Predicates.resideInAnyPackage("com.rs.showcase.."));
    modules.verify();
  }
}
