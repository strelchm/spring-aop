package ru.strelchm.springaop;

import org.springframework.stereotype.Service;

@Service
public class TimeService {
  public void timeMetric(Long startTime) {
    System.out.printf("Time is %d%n", System.currentTimeMillis() - startTime);
  }
}
