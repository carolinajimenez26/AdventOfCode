package BridgeRepair;

import java.io.*;
import java.util.*;

// https://adventofcode.com/2024/day/7

public class Solution {
  public static void main(String[] args) {
    if (args.length == 0) {
      return;
    }
    final String fileName = args[0];
    System.out.println(fileName);

    Map<Long, List<Integer>> equations = readFile(fileName);
    print(equations);
    solve(equations);
  }

  private static void solve(final Map<Long, List<Integer>> map) {
    Long result = 0L;
    for (Map.Entry<Long, List<Integer>> entry : map.entrySet()) {
      if (canSolveTo(entry.getValue(), entry.getKey())) {
        result += entry.getKey();
      }
    }
    System.out.println("Solution " + result);
  }

  private static boolean canSolveTo(final List<Integer> nums, final Long expected) {
    if (nums.size() == 0) return false;
    Long currentResult = (long) nums.get(0);
    if (nums.size() == 1) return currentResult.equals(expected);
    final boolean result = canSolveTo(nums, 1, currentResult, expected);
    System.out.println("Can solve to " + expected + "? " + result);
    return result;
  }

  private static boolean canSolveTo(final List<Integer> nums, int index, 
    Long currentResult, final Long expected) {
    
    // System.out.println("canSolveTo " + index + ", " + currentResult);

    if (index >= nums.size()) { // last number, nothing else to compute
      return currentResult.equals(expected);
    }

    if (currentResult > expected) {
      return false;
    }

    Integer x = nums.get(index);
    Long sum = currentResult + x;
    Long mul = currentResult * x;
    
    if (canSolveTo(nums, index + 1, sum, expected)) {
      // System.out.println("Using sum " + index);
      return true;
    }

    if (canSolveTo(nums, index + 1, mul, expected)) {
      // System.out.println("Using mul " + index);
      return true;
    }

    return false;
  }

  private static void print(final Map<Long, List<Integer>> map) {
    System.out.println("------------");
    for (Map.Entry<Long, List<Integer>> entry : map.entrySet()) {
      System.out.println(entry.getKey() + ": " + entry.getValue());
    }
    System.out.println("------------");
  }

  private static Map<Long, List<Integer>> readFile(String fileName) {
    Map<Long, List<Integer>> result = new HashMap<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      String line;
      while ((line = reader.readLine()) != null) {
        // Split the line into two parts: key and values
        String[] parts = line.split(":");
        if (parts.length == 2) {
          // Parse the key
          Long key = Long.parseLong(parts[0].trim());

          // Parse the values into a list of integers
          String[] valueParts = parts[1].trim().split(" ");
          List<Integer> values = new ArrayList<>();
          for (String value : valueParts) {
            values.add(Integer.parseInt(value));
          }

          // Add the key-value pair to the map
          result.put(key, values);
        } else {
          System.err.println("Skipping invalid line: " + line);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NumberFormatException e) {
      System.err.println("Error parsing number: " + e.getMessage());
    }

    return result;
  }

}