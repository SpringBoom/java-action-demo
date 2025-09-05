package com.example.mthread.stop;

/**
 * 停止线程的错误方式： stop 会导致突然停止
 *
 * @author shing.shi
 * @since 2025/8/18
 */
public class WrongStopThread {

  public static void main(String[] args) throws InterruptedException {
    Thread thread = new Thread(() -> {
      // 数据要求：按组 i 整批处理数据
      for (int i = 0; i < 5; i++) {
        System.out.println("run: " + i);
        for (int j = 0; j < 10; j++) {
          try {
            Thread.sleep(50);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          System.out.println("run: " + i + "-" + j);
        }
        System.out.println("finish: " + i);
      }
    });

    thread.start();
    Thread.sleep(1000);
    thread.stop();
    System.out.println("main finished");
  }
}
