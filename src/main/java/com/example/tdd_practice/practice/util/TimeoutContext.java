package com.example.tdd_practice.practice.util;

public class TimeoutContext {
  private static final ThreadLocal<Long> thresholdHolder = new ThreadLocal<>();

  public static void set(long threshold) {
    thresholdHolder.set(threshold);
  }

  public static Long get() {
    return thresholdHolder.get();
  }

  public static void clear() {
    thresholdHolder.remove();
  }
}
