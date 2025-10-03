package com.example.tdd_practice.practice.aspect;

import com.example.tdd_practice.practice.annotation.TimeoutCheck;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeoutAspect {
  @Around("@annotation(timeoutCheck)")
  public Object checkTimeout(ProceedingJoinPoint joinPoint, TimeoutCheck timeoutCheck) throws Throwable {
    long start = System.currentTimeMillis();
    Object result = joinPoint.proceed();
    long duration = System.currentTimeMillis() - start;

    return null;
  }
}
