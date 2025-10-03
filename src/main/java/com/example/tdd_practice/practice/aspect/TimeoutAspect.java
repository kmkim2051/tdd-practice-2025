package com.example.tdd_practice.practice.aspect;

import com.example.tdd_practice.practice.annotation.TimeoutCheck;
import com.example.tdd_practice.practice.util.TimeoutContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TimeoutAspect {
  @Around("@annotation(timeoutCheck)")
  public Object checkTimeout(ProceedingJoinPoint joinPoint, TimeoutCheck timeoutCheck) throws Throwable {
    long start = System.currentTimeMillis();
    Object result = joinPoint.proceed();
    long duration = System.currentTimeMillis() - start;

    Long testThreshold = TimeoutContext.get();
    Long threshold = (testThreshold == null) ? timeoutCheck.threshold() : testThreshold;

    log.info("threshold: {}, duration: {}", threshold,duration);

    long margin = 30L; // 실행에 따른 조정치

    if (duration + margin >= threshold) {
      throw new RuntimeException();
    }

    return result;
  }
}
