package com.example.mthread.stop;

/**
 * run 方法内没有 sleep 和 wait 时，如何停止线程
 *
 * @author shing.shi
 * @since 2025/8/18
 */
public class RightWayStopWithSleep implements Runnable {

  @Override
  public void run() {
    int num = 0;
    while (!Thread.interrupted() && num <= 100) {
      try {
        System.out.println("num = " + num);

        Thread.sleep(1000);
      } catch (InterruptedException e) {
        // e.printStackTrace();
        System.out.println("sleep interrupted");
        break;
      }
      num++;
    }

    // try {
    //   while (!Thread.interrupted() && num <= 100) {
    //     System.out.println("num = " + num);
    //
    //     Thread.sleep(1000);
    //     num++;
    //   }
    // } catch (InterruptedException e) {
    //   e.printStackTrace();
    // }

    System.out.println("run finished");
  }

  public static void main(String[] args) throws InterruptedException {
    RightWayStopWithSleep rightWayStop = new RightWayStopWithSleep();
    Thread thread = new Thread(rightWayStop);
    thread.start();
    Thread.sleep(500);
    thread.interrupt();
    Thread.sleep(500);
    thread.interrupt();
  }
}
