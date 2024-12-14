package PrintQueue;

import java.io.*;
import java.util.*;

public class Solution {
  private static Map<Integer, Set<Integer>> pageRules = new HashMap<>();
  private static List<List<Integer>> updates = new ArrayList<>();

  private static class RuleComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer a, Integer b) {
      if (!pageRules.containsKey(a)) {
        return 1; // a is smaller
      }
      if (pageRules.get(a).contains(b)) {
        return -1; // b is smaller
      }
      return 1; // a is smaller
    }
  }

  public static void main(String[] args) {
    if (args.length == 0) {
      return;
    }
    final String fileName = args[0];
    System.out.println(fileName);
    
    readFile(fileName);
    System.out.println("pageRules contents:");
    for (Map.Entry<Integer, Set<Integer>> entry : pageRules.entrySet()) {
      System.out.println(entry.getKey() + " -> " + entry.getValue());
    }
    System.out.println("\nupdates:");
    for (List<Integer> numbers : updates) {
      System.out.println(numbers);
    }

    solve();
  }

  private static void solve() {
    int result1 = 0;
    int result2 = 0;
    for (final List<Integer> update : updates) {
      if (isCorrect(update)) {
        result1 += getMiddlePageNumber(update);
      } else {
        System.out.println("Update before sorting: " + update);
        Collections.sort(update, new RuleComparator());
        System.out.println("Update sorted: " + update);
        result2 += getMiddlePageNumber(update);
      }
    }
    System.out.println("Solution part one: " + result1);
    System.out.println("Solution part two: " + result2);
  }

  private static int getMiddlePageNumber(final List<Integer> update) {
    int mid = update.get(update.size() / 2);
    System.out.println("mid " + mid);
    return mid;
  }

  private static boolean isCorrect(final List<Integer> update) {
    System.out.println("Checking correctness of " + update);
    for (int i = 0; i < update.size(); i++) {
      int num1 = update.get(i);
      Set<Integer> rules = pageRules.get(num1);
      System.out.println(num1 + " has rules " + rules);
      
      for (int j = i + 1; j < update.size(); j++) {
        int num2 = update.get(j);
        if (rules == null || !rules.contains(num2)) {
          System.out.println(num2 + " is NOT in rule ");
          return false;
        }
        System.out.println(num2 + " is in rule");
      }
    }
    return true;
  }

  private static void readFile(String filePath) {
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      boolean isProcessingPairs = true;

      while ((line = br.readLine()) != null) {
        line = line.trim();
        if (line.isEmpty()) {
          isProcessingPairs = false; // Switch to processing the second section
          continue;
        }

        if (isProcessingPairs) {
          // Process number1|number2 pairs
          String[] parts = line.split("\\|");
          int number1 = Integer.parseInt(parts[0]);
          int number2 = Integer.parseInt(parts[1]);

          if (!pageRules.containsKey(number1)) {
            pageRules.put(number1, new HashSet<>());
          }
          pageRules.get(number1).add(number2);
        } else {
          // Process comma-separated numbers
          String[] parts = line.split(",");
          List<Integer> numbers = new ArrayList<>();
          for (String part : parts) {
            numbers.add(Integer.parseInt(part));
          }
          updates.add(numbers);
        }
      }
    } catch (IOException e) {
      System.err.println("Error reading file: " + e.getMessage());
    }
  }
}