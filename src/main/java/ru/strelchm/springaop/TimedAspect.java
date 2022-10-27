package ru.strelchm.springaop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Aspect
@Component
@ConditionalOnProperty(name="timed", havingValue = "true")
public class TimedAspect {
  private TimeService timeService;

  @Autowired
  public TimedAspect(TimeService timeService) {
    this.timeService = timeService;
  }

  @Pointcut("@annotation(Timed) && args(user,..)")
  public void callAtMyServiceSecurityAnnotation(User user) {
  }

  /**
   * Around type advice will be invoked and code before pjp.proceed() of Around type
   * advice will be executed where pjp is the reference variable of ProceedingJoinPoint interface.
   * <p>
   * Before type advice will be invoked and executed fully.
   * code inside jointpoint will be executed fully.
   * <p>
   * Code after pjp.proceed() of Around type advice will be executed if
   * jointpoint executes successfully otherwise skip this step and go to step 5.
   * <p>
   * If it's modified return value then this new return value will
   * be effected to the followings advice or method invocation.
   * After type advice will be invoked and executed fully.
   * <p>
   * AfterReturning type advice will be invoked and executed fully if jointpoint
   * executes successfully else if jointpoint throws any error then
   * AfterThrowing type advice will be invoked and executed fully.
   */
  @Around("callAtMyServiceSecurityAnnotation(user)")
  public Object aroundCallAt(ProceedingJoinPoint pjp, User user) throws Throwable {
    Object retVal;

    Long startTime = System.currentTimeMillis();
    retVal = pjp.proceed();
    if (!((MethodSignature) pjp.getSignature()).getMethod().getAnnotation(Timed.class).disabled()) {
      timeService.timeMetric(startTime);
    }

    return retVal;
  }
}
