package MullItOver;

import java.io.*;
import java.util.*;

// https://adventofcode.com/2024/day/3

public class Solution {
  private static class Pair {
    int index;
    Integer value;
    Pair() {}
    Pair(int index, Integer value) {
      this.index = index;
      this.value = value;
    }
  };

  public static void main(String[] args) {
    if (args.length == 0) {
      return;
    }
    final String fileName = args[0];
    System.out.println(fileName);

    List<String> lines = readFile(fileName);
    solve(lines);
  }

  private static void solve(final List<String> lines) {
    int result = 0;
    for (final String line : lines) {
      result += solveLine(line);
    }

    System.out.println("Solution " + result + " should not be 97728793");
    
  }

  private static int solveLine(final String line) {
    int result = 0;
    boolean enabled = true;
    final String mulWord = "mul(", enabledWord = "do()", disabledWord = "don't()";

    for (int i = 0; i < line.length(); ) {
      if (lineHasPreffix(line, i, disabledWord)) {
        enabled = false;
        i += disabledWord.length();
        continue;
      }
      if (lineHasPreffix(line, i, enabledWord)) {
        enabled = true;
        i += enabledWord.length();
        continue;
      }
      if (enabled && lineHasPreffix(line, i, mulWord)) {
        i += mulWord.length();
        result += parseMul(line, i);
      } else {
        i++;
      }
    }

    return result;
  }

  private static int parseMul(final String line, int i) {
    int result = 0;
    Pair pair = new Pair(0, 0);
    Integer num1 = 0, num2 = 0;
    pair = parseIntegerUntilTarget(line, ',', i);
    num1 = pair.value;
    pair = parseIntegerUntilTarget(line, ')', pair.index);
    num2 = pair.value;
    result += num1 * num2;
    System.out.println("result " + result);
    // return new Pair(pair.index, result);
    return result;
  }

  private static boolean lineHasPreffix(final String line, int i, 
    final String target) {

    System.out.print("lineHasPreffix " + target + " ?, ");
    StringBuilder prefix = new StringBuilder();
    for (int j = 0; j < target.length(); j++) {
      prefix.append(getOrDefault(line, i + j));
    }

    final boolean result = prefix.toString().equals(target);
    System.out.println(result + " : " + prefix.toString());

    return result;
  }

  private static int solveLinePartOne(final String line) {
    int result = 0;
    StringBuilder prefix = new StringBuilder();
    Integer num1 = 0;
    Integer num2 = 0;

    for (int i = 0; i < line.length();) {
      prefix.append(getOrDefault(line, i))
        .append(getOrDefault(line, i + 1))
        .append(getOrDefault(line, i + 2))
        .append(getOrDefault(line, i + 3));
      
      System.out.println("prefix " + prefix);
      if (prefix.toString().equals("mul(")) {
        i += 4;
        Pair pair;
        pair = parseIntegerUntilTarget(line, ',', i);
        num1 = pair.value;
        pair = parseIntegerUntilTarget(line, ')', pair.index);
        num2 = pair.value;
        System.out.println(num1 + " * " + num2);
        if (!num1.equals(0) && !num2.equals(0)) {
          result += num1 * num2;
          System.out.println("result " + result);
          i = pair.index;
        }
      } else {
        i++;
      }
      prefix.setLength(0);
    }
    return result;
  }

  private static Pair parseIntegerUntilTarget(final String s, 
    char target, int index) {

    System.out.println("parseIntegerUntilTarget " + index + " " + target);
    
    StringBuilder num = new StringBuilder();
    while (index < s.length() && s.charAt(index) != target) {
      num.append(s.charAt(index));
      index++;
    }
    System.out.println("Parsed " + num);
    Integer result = 0;
    try {
      result = Integer.parseInt(num.toString());
    } catch (NumberFormatException e) {
      System.out.println("Error parsing " + num);
      result = 0;
    }

    return new Pair(index + 1, result);
  }

  private static char getOrDefault(final String s, int index) {
    if (index < s.length()) {
      return s.charAt(index);
    }
    return 'x';
  }

  private static List<String> readFile(final String fileName) {
    List<String> result = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {  
      String line;
      while ((line = br.readLine()) != null) {
        System.out.println("line " + line);
        result.add(line);
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    return result;
  }
}