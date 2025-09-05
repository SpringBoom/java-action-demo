package com.example.mthread.stop;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 使用 volatile 设置标记位来停止线程，当陷入阻塞时，此方式行不通
 * <p>
 * 生产消费者模式
 *
 * @author shing.shi
 * @since 2025/8/18
 */
public class StopByVolatileWrongCase {

  private volatile boolean canceled = false;

  public static void main(String[] args) throws InterruptedException {
    BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);

    Producer producer = new Producer(queue);
    Thread producerThread = new Thread(producer);
    Consumer consumer = new Consumer(queue);
    Thread consumerThread = new Thread(consumer);

    producerThread.start();
    consumerThread.start();

    Thread.sleep(1005);
    producer.canceled = true;
    consumer.canceled = true;
    // producerThread.interrupt();
    consumerThread.interrupt();
    consumerThread.isInterrupted();
    consumerThread.interrupted();
    consumerThread.interrupt();

  }

}

class Producer implements Runnable {
  volatile boolean canceled = false;
  BlockingQueue<Integer> queue;

  public Producer(BlockingQueue<Integer> queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
    int num = 0;
    while (num <= 100 && !canceled) {
      try {
        num++;
        // 生产者，每秒生产一个数据
        queue.put(1);
        System.out.println("生产一个数据，queue size=" + queue.size());
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}


class Consumer implements Runnable {
  volatile boolean canceled = false;
  BlockingQueue<Integer> queue;

  public Consumer(BlockingQueue<Integer> queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
    int num = 0;
    while (num <= 100 && !canceled) {
      try {
        // 消费者被阻塞
        System.out.println("queue.take()...");
        // ArrayBlockingQueue 中没有数据时， consumer queue.take() 时会阻塞
        Integer take = queue.take();
        System.out.println("消费：" + take);
        System.out.println("消费 ，queue size=" + queue.size());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}