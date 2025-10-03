package com.example.tdd_practice.practice;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
class PracticeApplicationTests {

  @Test
  void 컨트롤러에_CheckOnly_라는_애너테이션_달려있음() throws Exception {
    Method method = LoggingController.class.getMethod("hello");
    assertThat(method.isAnnotationPresent(CheckOnly.class)).isTrue();
  }

}
