package com.example.paralle;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(value = 2, jvmArgs = {"-Xms4G", "-Xmx4G"})
public class JMHDemo {

  public static void main(String[] args) {
    JMHDemo jmhDemo = new JMHDemo();
    int i = jmhDemo.doubleAndSumParallel();
    int i1 = jmhDemo.doubleAndSumSequential();
  }

  public int doubleIt(int n) {
    try {
      Thread.sleep(100);
    } catch (InterruptedException ignored) {
    }
    return n * 2;
  }

  @Benchmark
  public int doubleAndSumSequential() {
    return IntStream.of(3, 1, 4, 1, 5, 9).map(this::doubleIt).sum();
  }

  @Benchmark
  public int  doubleAndSumParallel() {
    return IntStream.of(3, 1, 4, 1, 5, 9).parallel().map(this::doubleIt).sum();
  }
}