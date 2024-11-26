package com.example.juc.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 2024/11/18
 */
public class ConditionAction {
  private static Logger log = LoggerFactory.getLogger(ConditionAction.class);

  public static void main(String[] args) throws InterruptedException {
    ConditionDemo demo = new ConditionDemo();
    new Thread(demo::waitTest, "Thread-await").start();
    log.info("main thread sleep...");
    Thread.sleep(10000);
    new Thread(demo::notifyTest, "Thread-notify").start();
  }

  public static class ConditionDemo {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void waitTest() {
      try {
        lock.lock();
        try {
          log.info("condition.await, start");
          condition.await();
          log.info("condition.await, end");
        } catch (InterruptedException e) {
          log.error("condition.await error", e);
        }
      } finally {
        lock.unlock();
      }
    }

    public void notifyTest() {
      try {
        lock.lock();
        log.info("condition.signal, start");
        condition.signal();
        log.info("condition.signal, end");
      }finally {
        lock.unlock();
      }
    }
  }

}
