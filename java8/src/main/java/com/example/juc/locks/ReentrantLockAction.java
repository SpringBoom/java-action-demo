package com.example.juc.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @since 2024/11/8
 */
public class ReentrantLockAction {

  public static void main(String[] args) {
    int i1=1, i2=2,i3=3;
    i1=i2=i3;
    System.out.println(i1);
    System.out.println(i2);
    System.out.println(i3);

    Lock lock = new ReentrantLock();
    lock.lock();
    try {
      // do something
    } finally {
      lock.unlock();
    }
  }

}
