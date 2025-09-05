package com.example.reg;

/**
 * 正则基础
 *
 * @author shing.shi
 * @since 2025/6/20
 */
public class BaseRegexp {

  /**
   * 在Java中，\\的意思是“我正在插入一个正则表达式反斜杠，所以后面的字符有特殊含义”。
   * 例如，要表示一个数字，你的正则表达式字符串应该是\\d。要插入普通的反斜杠，你可以用\\\\。
   * 但是，换行符和制表符之类的符号只使用一个反斜杠：\n\t
   */

  public static void main(String[] args) {
    blankSlashes();
    integerMatches();
  }

  public static void blankSlashes() {
    System.out.println("\n#blankSlashes()");
    // java 字符串中 \\ 为 \
    String one = "\\";
    String two = "\\\\";
    String three = "\\\\\\\\";
    System.out.println(one);  // 输出 \
    System.out.println(two);
    System.out.println(three);

    // 在正则表达式中，我们需要使用四个反斜杠才能与单个反斜杠匹配
    System.out.println(one.matches("\\\\"));
    System.out.println(two.matches("\\\\\\\\"));
    System.out.println(three.matches("\\\\\\\\\\\\\\\\"));
  }

  public static void integerMatches() {
    System.out.println("\n#integerMatches()");
    /**
     * 要表示一个数前面可能有也可能没有减号，可以在减号后面加上一个问号
     * 表达式里表示“前面有一个或多个”，请使用 +
     */
    // 是否为数值
    System.out.println("-1234".matches("-?\\d+"));
    System.out.println("-1234.1".matches("-?\\d+"));
    System.out.println("1234".matches("(-|\\+)?\\d+"));
  }

  public static  void strSplit(){
    System.out.println("\n#strSplit()");
    String text = "Then, when you have the shurbbery";
  }
}
