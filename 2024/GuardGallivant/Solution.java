package GuardGallivant;

import java.io.*;
import java.util.*;

// https://adventofcode.com/2024/day/6

public class Solution {
  private static char[][] matrix = null;

  private static class Position {
    int row, col;
    Position(int row, int col) {
      this.row = row;
      this.col = col;
    }
  }

  public static void main(String[] args) {
    if (args.length == 0) {
      return;
    }
    final String fileName = args[0];
    System.out.println(fileName);

    readFile(fileName);
    print(matrix);
    solve();
  }

  private static void solve() {
    int result = 0;
    Position currentPos = findElement('^');
    if (currentPos == null) {
      System.out.println("Error, couldn't find element");  
      return;
    }
    while (!isInBorder(currentPos.row, currentPos.col)) {
      currentPos = advance(currentPos);
    }
    result = countElements('X') + 1;
    System.out.println("Solution " + result);
  }

  private static int countElements(final char target) {
    int result = 0;
    for (int row = 0; row < matrix.length; row++) {
      for (int col = 0; col < matrix[row].length; col++) {
        if (matrix[row][col] == target) {
          result++;
        }
      }
    }
    return result;
  }

  private static Position advance(final Position pos) {
    System.out.println("Adancing from " + pos.row + ", " + pos.col);
    int row = pos.row, col = pos.col;
    char dir = matrix[row][col];
    switch(dir) {
      case '^':
        while (!isInBorder(row, col) && !isABlocker(row - 1, col)) {
          matrix[row][col] = 'X';
          row--;
        }
        matrix[row][col] = '>';
        break;
      case '>':
        while (!isInBorder(row, col) && !isABlocker(row, col + 1)) {
          matrix[row][col] = 'X';
          col++;
        }
        matrix[row][col] = 'v';
        break;
      case 'v':
        while (!isInBorder(row, col) && !isABlocker(row + 1, col)) {
          matrix[row][col] = 'X';
          row++;
        }
        matrix[row][col] = '<';
        break;
      case '<':
        while (!isInBorder(row, col) && !isABlocker(row, col - 1)) {
          matrix[row][col] = 'X';
          col--;
        }
        matrix[row][col] = '^';
        break;
    }
    pos.row = row;
    pos.col = col;
    print(matrix);
    return pos;
  }

  private static boolean isABlocker(final int row, final int col) {
    if (row < 0 || row >= matrix.length || col < 0 || col >= matrix.length) {
      return true;
    }
    char target = matrix[row][col];
    boolean result = target == '#';
    System.out.println("isABlocker? " + row + ", " + col + " " + target + ", " + result);
    return result;
  }

  private static boolean isInBorder(final int row, final int col) {
    System.out.println("isInBorder? " + row + ", " + col);
    if (row == 0 || row == matrix.length - 1) {
      System.out.println("Yes, is in row border");
      return true;
    }
    if (col == 0 || col == matrix.length - 1) {
      System.out.println("Yes, is in col border");
      return true;
    }
    System.out.println("No, is not in a border");
    return false;
  }

  private static Position findElement(char target) {
    for (int row = 0; row < matrix.length; row++) {
      for (int col = 0; col < matrix[row].length; col++) {
        if (matrix[row][col] == target) {
          return new Position(row, col);
        }
      }
    }
    return null;
  }

  private static void readFile(final String fileName) {
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