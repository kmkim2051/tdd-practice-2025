package com.example.tdd_practice.practice.controller;

import com.example.tdd_practice.practice.annotation.TimeoutCheck;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggingController {

  @TimeoutCheck(threshold = 2)
  @GetMapping
  public ResponseEntity<String> hello() {
    return ResponseEntity.ok("Hello");
  }

  @TimeoutCheck(threshold = 2000, unit = TimeoutCheck.TimeUnit.MILLISECONDS) // default threshold
  public ResponseEntity<String> slowMethod(long sleepMillis) throws InterruptedException {
    Thread.sleep(sleepMillis);
    return ResponseEntity.ok("success");
  }

  @TimeoutCheck(threshold = 2, unit = TimeoutCheck.TimeUnit.SECONDS) // default threshold
  @GetMapping("/fast")
  public ResponseEntity<String> fastMethod() throws InterruptedException {
    Thread.sleep(1000);
    return ResponseEntity.ok("success");
  }
}
