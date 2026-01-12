package com.example.paralle.future;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * 假设我们的应用程序需要根据产品 ID 检索相关产品。
 * 由于涉及某种形式的远程访问，检索过程可能会引入大量开销。
 * 这些开销包括对 RESTful Web 服务的网络调用、数据库调用以及其他相对耗时的操作。
 * 为此，我们采用映射的形式在本地创建产品缓存：
 * 请求某个产品时，系统首先在映射中检索。如果返回 null ，再进行开销较大的操作。
 *
 * @author SpringBoom
 * @date 2024/7/12
 */
public class CompletableFutureDemo {

  public static void main(String[] args) throws Exception {
    CompletableFutureDemo demo = new CompletableFutureDemo();
    CompletableFuture<Product> productFuture = demo.getProduct(1);
    Product product = productFuture.get();
    System.out.println(product);

    System.out.println("");
    demo.completableThenApply();
    System.out.println("after completableThenApply");
    boolean b = ForkJoinPool.commonPool().awaitQuiescence(60, TimeUnit.SECONDS);
    System.out.println(b);
    // demo.sleepThenReturnString(1000);

    demo.compose();
    demo.combine();
  }

  private CompletableFuture<Integer> getIntegerCompletableFuture(String num) {
    return CompletableFuture.supplyAsync(() -> Integer.parseInt(num))
            .handle((val, exc) -> val != null ? val : 0);
  }

  public void handleWithException() throws Exception {
    String num = "abc";
    CompletableFuture<Integer> value = getIntegerCompletableFuture(num);
  }

  public void handleWithoutException() throws Exception {
    String num = "42";
    CompletableFuture<Integer> value = getIntegerCompletableFuture(num);
  }

  public void combine() throws  Exception {
    int x = 2;
    int y = 3;
    CompletableFuture<Integer> completableFuture =
        CompletableFuture.supplyAsync(() -> x)
            .thenCombine(CompletableFuture.supplyAsync(() -> y), (a, b) -> a + b);

    System.out.println(completableFuture.get());
  }

  public void compose() throws Exception {
    int x = 2;
    int y = 3;
    CompletableFuture<Integer> completableFuture =
        CompletableFuture.supplyAsync(() -> x)
            .thenCompose(n -> CompletableFuture.supplyAsync(() -> n + y));

    System.out.println(completableFuture.get());
  }

  private String sleepThenReturnString() {
    try {
      Thread.sleep(100);
    } catch (InterruptedException ignored) {
    }
    return "42";
  }

  private String sleepThenReturnString(long millis) {
    try {
      Thread.sleep(100);
    } catch (InterruptedException ignored) {
    }
    return "42";
  }


  private void completableThenApply() {
    System.out.println("completableThenApply");
    CompletableFuture.supplyAsync(this::sleepThenReturnString)
        .thenApply(Integer::parseInt)
        .thenApply(x -> 2 * x)
        .thenAccept(System.out::println);
    System.out.println("Running...");
  }

  private Map<Integer, Product> cache = new HashMap<>();

  private Product getLocal(int id) {
    return cache.get(id);
  }

  private Product getRemote(int id) {
    try {
      System.out.println("getRemote: " + id);
      Thread.sleep(2000);
      if (id == 666) {
        throw new RuntimeException("Evil request");
      }
    } catch (InterruptedException ignored) {
      ignored.printStackTrace();
    }
    return new Product(id, "name");
  }

  public CompletableFuture<Product> getProduct(int id) {
    try {
      Product product = getLocal(id);
      if (product != null) {
        // 在缓存中检索到产品（如果有的话），返回 completedFuture
        return CompletableFuture.completedFuture(product);
      } else {
        CompletableFuture<Product> future = CompletableFuture.supplyAsync(() -> {
          Product p = getRemote(id);
          cache.put(id, p);
          return p;
        });
        System.out.println("return future");
        return future;
      }
    } catch (Exception e) {
      // 如果出现问题，在抛出异常后完成
      CompletableFuture<Product> future = new CompletableFuture<>();
      future.completeExceptionally(e);
      return future;
    }
  }

  public static class Product {
    private int id;
    private String name;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Product(int id, String name) {
      this.id = id;
      this.name = name;
    }

    @Override
    public String toString() {
      return "Product: " + id + ", " + name;
    }
  }
}
