package DiskFragmenter;

import java.io.*;
import java.util.*;

// https://adventofcode.com/2024/day/9

public class Solution {
  public static void main(String[] args) {
    if (args.length == 0) {
      return;
    }
    final String filePath = args[0];
    System.out.println(filePath);
    final String diskMap = readFile(filePath);
    solve(diskMap);
  }

  private static void solve(final String diskMap) {
    System.out.println("diskMap " + diskMap);
    List<Character> blocks = getMemoryBlocks(diskMap);
    System.out.println("Blocks " + blocks);
    final List<Integer> blocksProcessed = freeSpace(blocks);
    System.out.println("blocksProcessed " + blocksProcessed);
    long result = getCheckSum(blocksProcessed);
    System.out.println("Solution part one: " + result);
  }

  private static List<Character> getMemoryBlocks(final String diskMap) {
    List<Character> result = new ArrayList<>();
    boolean isFreeSpace = false;
    int id = 0;
    for (int i = 0; i < diskMap.length(); i++) {
      int n = diskMap.charAt(i) - '0';
      // System.out.println("i " + i + ", n " + n + ", id " + id);
      for (int j = 0; j < n; j++) {
        if (isFreeSpace) {
          result.add('.');
        } else {
          char c = (char) (id + '0');
          result.add(c);
        }
      }
      isFreeSpace = !isFreeSpace;
      if (!isFreeSpace) id++;
    }
    return result;
  }

  private static List<Integer> freeSpace(final List<Character> blocks) {
    List<Integer> result = new ArrayList<>();
    int j = blocks.size() - 1;
    for (int i = 0; i < blocks.size() && i <= j; ) {
      if (blocks.get(i) != '.') {
        int n = blocks.get(i) - '0';
        result.add(n);
        i++;
        continue;
      }
      if (blocks.get(j) == '.') {
        j--;
      } else {
        int n = blocks.get(j) - '0';
        result.add(n);
        j--;
        i++;
      }
    }
    return result;
  }

  private static long getCheckSum(final List<Integer> memoryBlocks) {
    long result = 0;
    for (int i = 0; i < memoryBlocks.size(); i++) {
      result += i * memoryBlocks.get(i);
    }
    return result;
  } 

  private static String readFile(final String filePath) {
    String line = "";
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      line = reader.readLine(); // Reads the single long line
      System.out.println(line);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return line;
  }
}