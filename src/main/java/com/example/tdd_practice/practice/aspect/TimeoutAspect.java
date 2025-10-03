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
  private final long MARGIN = 30L;

  @Around("@annotation(timeoutCheck)")
  public Object checkTimeout(ProceedingJoinPoint joinPoint, TimeoutCheck timeoutCheck) throws Throwable {
    long start = System.currentTimeMillis();
    Object result = joinPoint.proceed();
    long duration = System.currentTimeMillis() - start;

    // --------------- TIME UNIT ---------------
    TimeoutCheck.TimeUnit unit = getUnit(timeoutCheck);
    log.info("unit : {}", unit);

    // --------------- THRESHOLD ---------------
    Long threshold = getThreshold(timeoutCheck);
    threshold = convertToMs(threshold, unit);

    // --------------- CALCULATION ---------------
    long marginedDuration = getMarginedDuration(duration);
    log.info("threshold: {}, marginedDuration: {}", threshold, marginedDuration);

    if (marginedDuration >= threshold) {
      throw new RuntimeException();
    }

    return result;
  }

  private long getThreshold(TimeoutCheck timeoutCheck) {
    Long testThreshold = TimeoutContext.getThreshold();
    return (testThreshold == null) ? timeoutCheck.threshold() : testThreshold;
  }

  private TimeoutCheck.TimeUnit getUnit(TimeoutCheck timeoutCheck) {
    TimeoutCheck.TimeUnit testTimeUnit = TimeoutContext.getTimeUnit();
    return testTimeUnit == null ?
        timeoutCheck.unit() :
        testTimeUnit;
  }

  private long getMarginedDuration(long duration) {
    return duration - MARGIN;
  }

  private long convertToMs(Long threshold, TimeoutCheck.TimeUnit unit) {
    return (unit == TimeoutCheck.TimeUnit.SECONDS) ? threshold * 1000 : threshold;
  }
}
