package com.example.tdd_practice.practice.controller;

import com.example.tdd_practice.practice.annotation.TimeoutCheck;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggingController {

  @TimeoutCheck
  @GetMapping
  public ResponseEntity<String> hello() {
    return ResponseEntity.ok("Hello");
  }
}
