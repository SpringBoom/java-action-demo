package com.example.util;

import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LinkedHashMap 是基于 HashMap 实现的有序集合
 *
 * @since 2024/11/5
 */
public class LinkedHashMapAction {

  private static final Logger log = LoggerFactory.getLogger(LinkedHashMapAction.class);
  public static void main(String[] args) {
    LinkedHashMap<String,String> map = new LinkedHashMap<>();
    LinkedHashMap<String,String> accessOrderMap = new LinkedHashMap<>(16,0.75f,true);
    accessOrderMap.put("key1","value1");
    accessOrderMap.put("key2","value2");
    accessOrderMap.put("key3","value3");
    accessOrderMap.put("key4","value4");
    accessOrderMap.put("key5","value5");
    log.info("{}", accessOrderMap);

    String key3 = accessOrderMap.get("key3");
    log.info("after get {}", key3);
    log.info("{}", accessOrderMap);
  }
}
