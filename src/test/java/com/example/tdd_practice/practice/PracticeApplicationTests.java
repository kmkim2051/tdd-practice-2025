package com.example.tdd_practice.practice;

import com.example.tdd_practice.practice.annotation.TimeoutCheck;
import com.example.tdd_practice.practice.controller.LoggingController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
class PracticeApplicationTests {

  @Autowired
  private LoggingController loggingController;

  @Test
  void 컨트롤러에_TimeoutCheck_라는_애너테이션_달려있음() throws Exception {
    Method method = LoggingController.class.getMethod("hello");
    assertThat(method.isAnnotationPresent(TimeoutCheck.class)).isTrue();
  }

  @Test
  void N초이상_걸리면_실패() {
    assertThrows(RuntimeException.class, () -> loggingController.slowMethod(5000));
    // req: N초 설정
  }

  @Test
  void Aspect_타임아웃_ThreadLocal_세팅() {
    TimeoutContext.setThreshold(5);
    long threshold = TimeoutContext.getThreshold();
    assertThat(threshold).isEqualTo(5L);
  }
}
