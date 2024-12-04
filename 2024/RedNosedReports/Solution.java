package RedNosedReports;

import java.io.*;
import java.util.*;

// https://adventofcode.com/2024/day/2

public class Solution {
  public static void main(String[] args) {
    if (args.length == 0) {
      return;
    }
    final String fileName = args[0];
    System.out.println(fileName);

    List<String> lines = readFile(fileName);
    // solvePartOne(lines);
    solvePartTwo(lines);
  }

  private static void solvePartTwo(List<String> lines) {
    int result = 0;
    
    for (String line : lines) {
      System.out.print(line + "->");
      List<String> parts = new ArrayList<>(Arrays.asList(line.split("\\s+")));
      if (lineIsSafe(parts)) {
        System.out.println(": safe");
        result++;
      } else {
        // try removing each item
        boolean becameSafe = false;
        for (int i = 0; i < parts.size(); i++) {
          List<String> tmp = new ArrayList<>(parts);
          tmp.remove(i);

          if (lineIsSafe(tmp)) {
            becameSafe = true;
            break;
          }
        }
        if (becameSafe) {
          System.out.println(": safe");
          result++;
        } else {
          System.out.println(": unsafe");
        }
      }
    }

    System.out.println("Solution " + result);
  }

  private static void solvePartOne(List<String> lines) {
    int result = 0;
    
    for (String line : lines) {
      System.out.print(line + "->");
      List<String> parts = new ArrayList<>(Arrays.asList(line.split("\\s+")));
      if (lineIsSafe(parts)) {
        System.out.println(": safe");
        result++;
      } else {
        System.out.println(": unsafe");
      }
    }

    System.out.println("Solution " + result);
  }

  private static boolean lineIsSafe(final List<String> parts) {
    return lineIsSafe(parts, 0, true, true);
  }

  private static boolean lineIsSafe(final List<String> parts, int index, 
    boolean allIncreasing, boolean allDecreasing) {
    
    if (index >= parts.size() - 1) {
      return (allIncreasing || allDecreasing);
    }

    int a = Integer.valueOf(parts.get(index));
    int b = Integer.valueOf(parts.get(index + 1));

    if (!isAllowedDiff(a, b)) {
      return false;
    }

    if (a == b) {
      allDecreasing = false;
      allIncreasing = false;
    }
    if (a < b) {
      allDecreasing = false;
    }
    if (a > b) {
      allIncreasing = false;
    }
    
    return lineIsSafe(parts, index + 1, allIncreasing, allDecreasing);
  }

  private static boolean isSafe(final boolean lineHasAllowedDiff, 
    final boolean allIncreasing, final boolean allDecreasing) {
    System.out.print(lineHasAllowedDiff + "," + allIncreasing + "," + allDecreasing);
    return (lineHasAllowedDiff && (allIncreasing || allDecreasing));
  }

  private static boolean isAllowedDiff(int a, int b) {
    return Math.abs(a - b) <= 3;
  }

  private static List<String> readFile(final String fileName) {
    List<String> result = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {  
      String line;
      while ((line = br.readLine()) != null) {
        // System.out.println("line " + line);
        result.add(line);
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    return result;
  }
}