package ru.strelchm.springaop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
  @GetMapping
  @Timed(disabled = false)
  public void test(@RequestParam(required = false) User user) {
    System.out.println("just 4 test");
  }
}
