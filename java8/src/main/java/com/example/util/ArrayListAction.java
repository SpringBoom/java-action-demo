package com.example.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SpringBoom
 * @date 2024/10/28
 */
public class ArrayListAction {
  final static Logger LOG = LoggerFactory.getLogger(ArrayListAction.class);


  public static void main(String[] args) {

    /*
     * 查找某个元素最后出出现的位置
     */
    ArrayList<Integer> list = createList();
    list.add(2);
    LOG.info("list: {}", list);
    /*
     * lastIndexOf 实现逻辑为：
     * 1. 先判断参数 o 是否为 null
     * 2. 如果为 null，从后往前遍历，判断元素是否为 null
     * 3. 如果不为 null，从后往前遍历，判断元素是否与参数 o 相等
     */
    int i = list.lastIndexOf(2);
    LOG.info("list.lastIndexOf(2): {}", i);

    /*
     * 遍历：
     * 1. for
     * 2. Iterator
     * 3. for.each
     * 4. ListIterator
     */
    LOG.info("for 循环遍历：");
    for (int i1 = 0; i1 < list.size(); i1++) {
      LOG.info("list {}:{}", i1,list.get(i1));
    }

    ArrayList<Integer> iteratorList = createList();
    LOG.info("{}", iteratorList);

    // 返回的迭代器是 ArrayList 内部实现类：{@link java.util.ArrayList.Itr}
    Iterator<Integer> iterator = iteratorList.iterator();

    /*
     * List 中，删除和遍历需要分开，如可以先遍历获取到要删除的索引，然后在统一删除
     * iterator 中可以遍历时删除
     * iterator 遍历只能是从前往后遍历，如果要实现迭代器从后往前遍历可以使用 ListIterator
     */
    while (iterator.hasNext()){
      /*
       * 在进行next()方法调用的时候，会进行checkForComodification()调用。比较的是 ArrayList.modCount 和 Ity.expectedModCount
       *       if (modCount != expectedModCount)
       *                 throw new ConcurrentModificationException();
       * 该方法表示迭代器进行元素迭代时，如果同时进行增加和删除操作，会抛出ConcurrentModification Exception异常
       */
      Integer next = iterator.next();
      LOG.info("Ity: {}", next);
      if (next == 2) {
        LOG.info("Ity remove: {}", next);
        /*
         * iterator 内部也维护了一个修改计数器，每次 iterator.remove() 之后会重新赋值： expectedModCount = modCount
         */
        iterator.remove();
      }
      /*
       * 修改元素不会造成异常
       * 修改后的元素会被当前的 iterator 读取到，因为 iterator 只是维护了一个读取位置 cursor，读取时是直接对底层的元素数组进行下标读取
       */
      // iteratorList.set(0, -1);
      iteratorList.set(iteratorList.size() - 1, -1);

      /*
       * 遍历时增加元素会抛出ConcurrentModification
       * 因为添加会对元素数组进行扩容，所以会改变 modCount 的值，所以会抛出异常
       * iterator 没有 add() 方法
       */
      // iteratorList.add(-1);
      // iteratorList.add(0,-1);
    }
    LOG.info("{}", iteratorList);

    ArrayList<Integer> foreachList = createList();
    /*
     * 增强for循环遍历是JDK的一种语法糖，底层也是通过迭代器实现的，但是不需要显式地使用 Iterator
     */
    for (Integer integer : foreachList) {
      LOG.info("for.each: {}", integer);
    }

    ArrayList<Integer> listIteratorList = createList();
    /*
     * ListIterator
     * ArrayList 也是在其内部类实现的：
     *  ListItr extends Itr
     * ListIterator 可以实现一边遍历，一边进行新增或者删除操作
     */
    ListIterator<Integer> integerListIterator = listIteratorList.listIterator();
    while (integerListIterator.hasNext()){
      Integer next = integerListIterator.next();
      LOG.info("ListIterator: {}", next);
      if (next == 2) {
        LOG.info("ListIterator remove: {}", next);
        integerListIterator.remove();
      }
      /*
       * 增加元素不会被当前的 ListIterator 读取到，因为添加元素后，会重新赋值 cursor 的值：
       *         int i = cursor;
       *         ArrayList.this.add(i, e);
       *         cursor = i + 1;
       */
      integerListIterator.add(-1);
    }

    // 还可以从后往前遍历
    while (integerListIterator.hasPrevious()) {
      LOG.info("integerListIterator.previous(): {}", integerListIterator.previous());
    }

  }

  private static ArrayList<Integer> createList(){
    ArrayList<Integer> intList = new ArrayList<>();
    intList.add(1);
    intList.add(2);
    intList.add(3);
    return intList;
  }

}
