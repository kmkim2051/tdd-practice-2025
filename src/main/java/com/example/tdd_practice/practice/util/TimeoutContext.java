package com.example.tdd_practice.practice.util;

public class TimeoutContext {
  private static final ThreadLocal<Long> thresholdHolder = new ThreadLocal<>();

  public static void set(long threshold) {
  }

  public static Long get() {
    return 5L;
  }

  public static void clear() {
    thresholdHolder.remove();
  }
}
