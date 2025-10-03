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

    Long testThreshold = TimeoutContext.getThreshold();
    Long threshold = (testThreshold == null) ? timeoutCheck.threshold() : testThreshold;

    TimeoutCheck.TimeUnit testTimeUnit = TimeoutContext.getTimeUnit();
    TimeoutCheck.TimeUnit unit = testTimeUnit == null ?
        timeoutCheck.unit() :
        testTimeUnit;

    log.info("unit : {}", unit);
    threshold = unit == TimeoutCheck.TimeUnit.SECONDS ? threshold * 1000 : threshold;

    long margin = 30L; // 실행에 따른 조정치
    long marginedDuration = duration - margin;

    log.info("threshold: {}, marginedDuration: {}", threshold, marginedDuration);

    if (marginedDuration >= threshold) {
      throw new RuntimeException();
    }

    return result;
  }
}
