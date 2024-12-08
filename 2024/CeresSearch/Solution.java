package CeresSearch;

import java.io.*;
import java.util.*;

// https://adventofcode.com/2024/day/4

public class Solution {
  private static final String word = "XMAS";

  private static class XmasOptions {
    final static String word = "MAS";
    static char[][] a = {
      {'M', '#', 'S'},
      {'#', 'A', '#'},
      {'M', '#', 'S'}
    };
    static char[][] b = {
      {'M', '#', 'M'},
      {'#', 'A', '#'},
      {'S', '#', 'S'}
    };
    static char[][] c = {
      {'S', '#', 'S'},
      {'#', 'A', '#'},
      {'M', '#', 'M'}
    };
    static char[][] d = {
      {'S', '#', 'M'},
      {'#', 'A', '#'},
      {'S', '#', 'M'}
    };
  }

  public static void main(String[] args) {
    if (args.length == 0) {
      return;
    }
    final String fileName = args[0];
    System.out.println(fileName);

    char[][] matrix = readFile(fileName);
    // print(matrix);
    // solvePartOne(matrix);
    solvePartTwo(matrix);
  }

  private static void solvePartTwo(final char[][] matrix) {
    int result = 0;
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        result += findWord(matrix, i, j);
      }
    }
    System.out.println("Solution " + result);
  }

  private static int findWord(final char[][] matrix, int row, int col) {
    if (overlaps(matrix, row, col, XmasOptions.a)) return 1;
    if (overlaps(matrix, row, col, XmasOptions.b)) return 1;
    if (overlaps(matrix, row, col, XmasOptions.c)) return 1;
    if (overlaps(matrix, row, col, XmasOptions.d)) return 1;
    return 0;
  }

  private static boolean overlaps(final char[][] matrix, int row, int col, 
    final char[][] option) {
    
    int optionRow = 1, optionCol = 1;
    for (int i = -1; i < 2; i++) {
      for (int j = -1; j < 2; j++) {
        if (option[optionRow + i][optionCol + j] == '#') {
          continue;
        }
        if (getOrDefault(matrix, row + i, col + j) != option[optionRow + i][optionCol + j]) {
          return false;
        }
      }
    }
    return true;
  }

  private static void solvePartOne(final char[][] matrix) {
    int result = 0;
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        result += findWord(matrix, i, j, "right");
        result += findWord(matrix, i, j, "left");
        result += findWord(matrix, i, j, "down");
        result += findWord(matrix, i, j, "up");
        result += findWord(matrix, i, j, "downRight");
        result += findWord(matrix, i, j, "downLeft");
        result += findWord(matrix, i, j, "upRight");
        result += findWord(matrix, i, j, "upLeft");
      }
    }
    System.out.println("Solution " + result);
  }

  private static int findWord(final char[][] matrix, int row, int col, 
    final String dir) {
    
    String found = "";
    for (int k = 0; k < word.length(); k++) {
      switch(dir) {
        case "right":
          found += getOrDefault(matrix, row, col + k);
          break;
        case "left":
          found += getOrDefault(matrix, row, col - k);
          break;
        case "down":
          found += getOrDefault(matrix, row + k, col);
          break;
        case "up":
          found += getOrDefault(matrix, row - k, col);
          break;
        case "downRight":
          found += getOrDefault(matrix, row + k, col + k);
          break;
        case "downLeft":
          found += getOrDefault(matrix, row + k, col - k);
          break;
        case "upRight":
          found += getOrDefault(matrix, row - k, col + k);
          break;
        case "upLeft":
          found += getOrDefault(matrix, row - k, col - k);
          break;
      }
    }
    // System.out.println("found " + found);
    return found.equals(word) ? 1 : 0;
  }

  private static char getOrDefault(final char[][] matrix, int row, int col) {
    if (row < 0 || row >= matrix.length) {
      return '.';
    }
    if (col < 0 || col >= matrix[row].length) {
      return '.';
    }
    return matrix[row][col];
  }

  private static char[][] readFile(final String fileName) {
    char[][] matrix = null;

    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      List<char[]> rows = new ArrayList<>();
      String line;
      
      while ((line = br.readLine()) != null) {
        // Convert each line into a char array and add to the list
        rows.add(line.toCharArray());
      }

      // Convert List<char[]> to char[][]
      matrix = rows.toArray(new char[0][]);
    } catch (IOException e) {
      System.err.println("Error reading file: " + e.getMessage());
    }

    return matrix;
  }

  private static void print(final char[][] matrix) {
    // Print the matrix to verify
    if (matrix != null) {
      for (char[] row : matrix) {
        System.out.println(Arrays.toString(row));
      }
    }
  }
}