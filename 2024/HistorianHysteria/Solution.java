package HistorianHysteria;

import java.io.*;
import java.util.*;

// https://adventofcode.com/2024/day/1

public class Solution {
  public static void main(String[] args) {
    if (args.length == 0) {
      return;
    }
    final String fileName = args[0];
    System.out.println(fileName);

    List<Integer> left = new ArrayList<>();
    List<Integer> right = new ArrayList<>();

    readFile(fileName, left, right);
    solvePartOne(left, right);
    solvePartTwo(left, right);
  }

  private static void solvePartTwo(final List<Integer> left, 
  final List<Integer> right) {
    Map<Integer, Integer> freqs = new HashMap<>();
    for (int i = 0; i < right.size(); i++) {
      Integer n = right.get(i);
      Integer next = freqs.getOrDefault(n, 0) + 1;
      freqs.put(n, next);
      System.out.println(n + "->" + next);
    }
    int result = 0;
    for (int i = 0; i < left.size(); i++) {
      Integer n = left.get(i);
      Integer freq = freqs.getOrDefault(n, 0);
      System.out.println(n + " * " + freq + " = " + (n * freq));
      result += n * freq;
    }

    System.out.println("Solution " + result);
  }

  private static void solvePartOne(List<Integer> left, List<Integer> right) {
    Collections.sort(left);
    Collections.sort(right);

    int result = 0;
    for (int i = 0; i < left.size(); i++) {
      System.out.println("left " + left.get(i) + ", right " + right.get(i));
      result += Math.abs(left.get(i) - right.get(i));
    }

    System.out.println("Solution " + result);
  }

  private static void readFile(final String fileName, 
    final List<Integer> left, final List<Integer> right) {
    int i = 0;

    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {  
      String line;
      while ((line = br.readLine()) != null) {
        System.out.println("line " + line);
        if (!line.isEmpty()) {
          String[] parts = line.split("\\s+");
          System.out.println("parts " + parts[0] + ", " + parts[1]);
          left.add(Integer.parseInt(parts[0]));
          right.add(Integer.parseInt(parts[1]));
          i++;
        }
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}