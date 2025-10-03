package com.example.tdd_practice.practice.util;

import com.example.tdd_practice.practice.annotation.TimeoutCheck;

public class TimeoutContext {
  private static final ThreadLocal<Long> thresholdHolder = new ThreadLocal<>();
  private static final ThreadLocal<TimeoutCheck.TimeUnit> timeUnitHolder = new ThreadLocal<>();

  public static void set(long threshold) {
    thresholdHolder.set(threshold);
    timeUnitHolder.set(TimeoutCheck.TimeUnit.MILLISECONDS); // default
  }
  public static void set(long threshold, TimeoutCheck.TimeUnit timeUnit) {
    thresholdHolder.set(threshold);
    timeUnitHolder.set(timeUnit);
  }

  public static Long getThreshold() {
    return thresholdHolder.get();
  }

  public static TimeoutCheck.TimeUnit getTimeUnit() {
    return timeUnitHolder.get();
  }

  public static void clear() {
    thresholdHolder.remove();
    timeUnitHolder.remove();

  }
}
