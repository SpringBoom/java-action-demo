package com.example.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 2024/10/30
 */
public class HashMapAction {

  private static final Logger log = LoggerFactory.getLogger(HashMapAction.class);

  public static void main(String[] args) {
    HashMap<CollisionKey,String> map = new HashMap<>();
    /*
     * HashMap 添加元素的大致逻辑：
     * 1. putVal(hash(key), key, value, false, true)
     *  (1). 计算 hash
     * 2. 判断 table 数组是否为 null，为 null 则创建一个长度为 16 的数组，并将数组赋值给 table
     * 3. 判断 table 数组中是否存在 hash 相等的键
     *  (1). 如果不存在，则创建 Node 节点，并将 Node 节点添加到 table 数组中
     *  (2). 如果存在，则进一步判断是否为同一个对象
     *    a. 这时候就已经要对链表或者红黑树的遍历了
     * 4. 最后判断是否需要扩容
     */
    // 创建多个 CollisionKey 对象，它们有相同的 hashCode，但不同的内容
    CollisionKey key1 = new CollisionKey("key1", 123);
    CollisionKey key2 = new CollisionKey("key2", 123);
    CollisionKey key3 = new CollisionKey("key3", 123);
    CollisionKey key4 = new CollisionKey("key4", 123);
    CollisionKey key5 = new CollisionKey("key5", 123);
    CollisionKey key6 = new CollisionKey("key6", 123);
    CollisionKey key7 = new CollisionKey("key7", 123);
    CollisionKey key8 = new CollisionKey("key8", 123);
    map.put(key1, "value1");
    map.put(key2, "value2");
    map.put(key3, "value3");
    map.put(key4, "value4");
    map.put(key5, "value5");
    map.put(key6, "value6");
    map.put(key7, "value7");
    map.put(key8, "value8");
    log.info("{}", map);

    Iterator<Map.Entry<CollisionKey, String>> iterator = map.entrySet().iterator();
    while (iterator.hasNext()){
      Map.Entry<CollisionKey, String> next = iterator.next();
      log.info("{}:{}", next.getKey(),next.getValue());
      // 可以在迭代过程中删除元素
      // iterator.remove();
    }
  }

  // 自定义键类 CollisionKey，使得多个对象的 hashCode 相同
  static class CollisionKey {
    private final String name;
    private final int fixedHashCode;

    public CollisionKey(String name, int fixedHashCode) {
      this.name = name;
      this.fixedHashCode = fixedHashCode;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      CollisionKey that = (CollisionKey) o;
      return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
      return fixedHashCode; // 返回相同的 hashCode，制造哈希碰撞
    }

    @Override
    public String toString() {
      return "CollisionKey{" +
          "name='" + name + '\'' +
          '}';
    }
  }
}
