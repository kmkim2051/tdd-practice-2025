package com.example.tdd_practice.practice;

import com.example.tdd_practice.practice.annotation.TimeoutCheck;
import com.example.tdd_practice.practice.controller.LoggingController;
import com.example.tdd_practice.practice.util.TimeoutContext;
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
  void N초이상_걸리면_실패() throws InterruptedException {
    long threshold = 3L; // 3초 이상 걸리면 실패 판정
    TimeoutContext.set(threshold);
    assertThrows(RuntimeException.class, () -> loggingController.slowMethod(5000));
  }

  @Test
  void N초미만_성공() throws InterruptedException {
    long threshold = 3000L; // 3초 이상 걸리면 실패 판정
    TimeoutContext.set(threshold);
    assertThat(loggingController.fastMethod()).isNotNull();
  }


  @Test
  void Aspect_타임아웃_ThreadLocal_세팅() {
    TimeoutContext.set(7000);
    long threshold = TimeoutContext.get();
    assertThat(threshold).isEqualTo(7000L);
  }

  /*
   신규 요구사항 - second, millisecond 선택
   */
  @Test
  void SECOND_선택시_초단위_적용() throws NoSuchMethodException, InterruptedException {
    Method method = LoggingController.class.getMethod("slowMethod", Long.class);
    TimeoutCheck annotation = method.getAnnotation(TimeoutCheck.class);

    // 예상 소요시간
    long expectedMs = annotation.unit() == TimeoutCheck.TimeUnit.SECONDS
        ? annotation.value() * 1000 : annotation.value();

    // 실제 소요시간
    long start = System.currentTimeMillis();
    loggingController.slowMethod(2000); // 2초
    long duration = System.currentTimeMillis();

    assertThat(duration - 30 < expectedMs); // 30은 시스템적인 실행시간
  }
}
