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

    if (duration >= threshold * 1000) {
      throw new RuntimeException();
    }

    return result;
  }
}
