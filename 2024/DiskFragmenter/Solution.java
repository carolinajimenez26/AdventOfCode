package DiskFragmenter;

import java.io.*;
import java.util.*;

// https://adventofcode.com/2024/day/9

public class Solution {
  private static class Pair {
    List<Integer> a, b;
    Pair(List<Integer> a, List<Integer> b) {
      this.a = a;
      this.b = b;
    }
  }

  public static void main(String[] args) {
    if (args.length == 0) {
      return;
    }
    final String filePath = args[0];
    System.out.println(filePath);
    final String diskMap = readFile(filePath);
    // solvePartOne(diskMap);
    solvePartTwo(diskMap);
  }

  private static void solvePartOne(final String diskMap) {
    System.out.println("diskMap " + diskMap);
    List<Character> blocks = getMemoryBlocksPartOne(diskMap);
    System.out.println("Blocks " + blocks);
    final List<Integer> blocksProcessed = freeSpacePartOne(blocks);
    System.out.println("blocksProcessed " + blocksProcessed);
    long result = getCheckSum(blocksProcessed);
    System.out.println("Solution: " + result);
  }

  private static void solvePartTwo(final String diskMap) {
    System.out.println("diskMap " + diskMap);
    List<Integer> blocks = getMemoryBlocksPartTwo(diskMap);
    System.out.println("Blocks " + blocks);
    final List<Integer> blocksProcessed = freeSpacePartTwo(blocks);
    System.out.println("blocksProcessed " + blocksProcessed);
    long result = getCheckSum(blocksProcessed);
    System.out.println("Solution: " + result);
  }

  private static List<Character> getMemoryBlocksPartOne(final String diskMap) {
    List<Character> result = new ArrayList<>();
    boolean isFreeSpace = false;
    int id = 0;
    for (int i = 0; i < diskMap.length(); i++) {
      int n = diskMap.charAt(i) - '0';
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

  private static List<Integer> getMemoryBlocksPartTwo(final String diskMap) {
    List<Integer> result = new ArrayList<>();
    boolean isFreeSpace = false;
    int id = 0;
    for (int i = 0; i < diskMap.length(); i++) {
      int n = diskMap.charAt(i) - '0';
      for (int j = 0; j < n; j++) {
        if (isFreeSpace) {
          result.add(-1);
        } else {
          result.add(id);
        }
      }
      isFreeSpace = !isFreeSpace;
      if (!isFreeSpace) id++;
    }
    return result;
  }

  private static List<Integer> freeSpacePartOne(final List<Character> blocks) {
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

  private static List<Integer> freeSpacePartTwo(final List<Integer> blocks) {
    final Pair sizes = getSizes(blocks);
    
    final List<Integer> freeSpaceSize = sizes.a;
    System.out.println("freeSpaceSize " + freeSpaceSize);
    
    final List<Integer> memorySpaceSize = sizes.b;
    System.out.println("memorySpaceSize " + memorySpaceSize);

    final List<Integer> spacesStartIdx = getSpacesStartIdx(blocks);
    System.out.println("spacesStartIdx " + spacesStartIdx);

    List<Integer> memoryStartIdx = getMemoryStartIdx(blocks);
    System.out.println("memoryStartIdx " + memoryStartIdx);

    for (int k = freeSpaceSize.size() - 1; k >= 0; k--) {
      // System.out.println("k " + k);
      int currFreeSpace = freeSpaceSize.get(k);
      // System.out.println("currFreeSpace " + currFreeSpace);

      for (int freeSpaceIdx = 0; freeSpaceIdx < memorySpaceSize.size(); freeSpaceIdx++) {
        // System.out.println("freeSpaceIdx " + freeSpaceIdx);
        int memorySpace = memorySpaceSize.get(freeSpaceIdx);
        // System.out.println("memorySpace " + memorySpace);

        if (currFreeSpace <= memorySpace && memorySpace != 0) {
          // System.out.println("currFreeSpace <= space!");
          memorySpace -= currFreeSpace;
          memorySpaceSize.set(freeSpaceIdx, memorySpace);
          freeSpaceSize.set(k, 0);

          int i = spacesStartIdx.get(freeSpaceIdx);
          int j = memoryStartIdx.get(k);
          for (int l = 0; l < currFreeSpace; l++) {
            blocks.set(i, k);
            blocks.set(j, -1);
            i++;
            j++;
          }
          spacesStartIdx.set(freeSpaceIdx, i);

          // System.out.println("blocks " + blocks);
          break;
        }
      }
    }

    return blocks;
  }

  private static Pair getSizes(final List<Integer> blocks) {
    List<Integer> memorySizes = new ArrayList<>();
    List<Integer> freeMemorySizes = new ArrayList<>();

    for (int i = 0; i < blocks.size();) {
      int j = 0;
      Integer prev = blocks.get(i);
      Integer curr = prev;
      while (i < blocks.size() && blocks.get(i) != -1) {
        curr = blocks.get(i);
        if (!curr.equals(prev)) break;
        j++;
        i++;
      }
      if (j > 0) memorySizes.add(j);
      int k = 0;
      while (i < blocks.size() && blocks.get(i) == -1) {
        k++;
        i++;
      }
      if (k > 0) freeMemorySizes.add(k);
    }

    return new Pair(memorySizes, freeMemorySizes);
  }

  private static List<Integer> getMemoryStartIdx(final List<Integer> blocks) {
    List<Integer> result = new ArrayList<>();

    for (int i = 0, prev = 0; i < blocks.size();) {
      if (blocks.get(i) != -1) {
        result.add(i);
        prev = blocks.get(i);
        while (i < blocks.size() && blocks.get(i) != -1) {
          int curr = blocks.get(i);
          if (prev != curr) break;
          i++;
        }
      } else {
        i++;
      }
    }
    return result;
  }

  private static List<Integer> getSpacesStartIdx(final List<Integer> blocks) {
    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < blocks.size();) {
      if (blocks.get(i) == -1) {
        result.add(i);
        while (i < blocks.size() && blocks.get(i) == -1) i++;
      } else {
        i++;
      }
    }
    return result;
  }

  private static long getCheckSum(final List<Integer> memoryBlocks) {
    long result = 0;
    for (int i = 0; i < memoryBlocks.size(); i++) {
      if (memoryBlocks.get(i) == -1) continue;
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