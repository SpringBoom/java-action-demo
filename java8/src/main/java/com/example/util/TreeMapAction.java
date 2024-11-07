package com.example.util;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 2024/11/7
 */
public class TreeMapAction {

  private static final Logger log = LoggerFactory.getLogger(TreeMapAction.class);

  public static void main(String[] args) {
    TreeMap<String,String> treeMap = new TreeMap<>();
    treeMap.put("key1","value1");
    treeMap.put("key2","value2");
    treeMap.put("key3","value3");
    treeMap.put("key4","value4");
    treeMap.put("key5","value5");
    log.info("{}", treeMap);

    // head 结果不包含 toKey
    Map<String, String> headMap = treeMap.headMap("key2");
    log.info("headMap by key2: {}",headMap);

    // tail 结果会包含 fromKey
    SortedMap<String, String> tailMap = treeMap.tailMap("key2");
    log.info("tailMap by key2: {}",tailMap);
  }
}
