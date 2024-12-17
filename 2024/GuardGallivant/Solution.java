package GuardGallivant;

import java.io.*;
import java.util.*;

// https://adventofcode.com/2024/day/6

public class Solution {
  private static char[][] matrix = null;
  private static char[][] original = null;
  private static Set<Position> visitedPositions = new HashSet<>();
  private static boolean isPrintEnabled = false;

  private static class Position {
    int row, col;
    char dir = '^';
    Position(int row, int col) {
      this.row = row;
      this.col = col;
    }

    Position(int row, int col, char dir) {
      this.row = row;
      this.col = col;
      this.dir = dir;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Position position = (Position) o;
      return row == position.row && col == position.col && dir == position.dir;
    }

    @Override
    public int hashCode() {
      return Objects.hash(row, col, dir);
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
    // solvePartOne();
    solvePartTwo();
  }

  private static void solvePartTwo() {
    final Position initialPos = findElement('^');
    if (initialPos == null) {
      System.out.println("Error, couldn't find element");
      return;
    }
    int result = 0;
    original = copy(matrix);

    for (int row = 0; row < matrix.length; row++) {
      for (int col = 0; col < matrix[row].length; col++) {
        if (initialPos.row == row && initialPos.col == col) {
          continue; // can't override guard initial position
        }
        if (isABlocker(row, col)) {
          continue; // no need to add a blocker where already exists
        }
        if (isPrintEnabled) System.out.println("-------------------------");
        if (isPrintEnabled) System.out.println("Adding blocker in position " + row + ", " + col);
        matrix[row][col] = '#';
        print(matrix);
        if (hasALoop(initialPos.row, initialPos.col)) {
          result++;
        }
        matrix = copy(original);
        visitedPositions.clear();
      }
    }
    System.out.println("Solution " + result);
  }

  private static char[][] copy(final char[][] target) {
    char[][] result = new char[target.length][];
    for (int i = 0; i < target.length; i++) {
      // Allocate memory for each row and copy it
      result[i] = new char[target[i].length];
      System.arraycopy(target[i], 0, result[i], 0, target[i].length);
    }
    return result;
  }

  private static boolean hasALoop(int row, int col) {
    Position currentPos = new Position(row, col);
    while (!isInBorder(currentPos.row, currentPos.col)) {
      currentPos = advancePartTwo(currentPos);
      if (visitedPositions.contains(currentPos)) {
        if (isPrintEnabled) System.out.println("Found a loop!");
        return true;
      }
    }
    if (isPrintEnabled) System.out.println("Didn't find a loop!");
    return false;
  }

  private static Position advancePartTwo(final Position pos) {
    if (isPrintEnabled) System.out.println("Adancing from " + pos.row + ", " + pos.col);
    int row = pos.row, col = pos.col;
    char dir = matrix[row][col];
    boolean hasCycle = false;
    switch(dir) {
      case '^':
        while (!isInBorder(row, col) && !isABlocker(row - 1, col) && !hasCycle) {
          markStep(row, col);
          row--;
        }
        matrix[row][col] = '>';
        break;
      case '>':
        while (!isInBorder(row, col) && !isABlocker(row, col + 1)) {
          markStep(row, col);
          col++;
        }
        matrix[row][col] = 'v';
        break;
      case 'v':
        while (!isInBorder(row, col) && !isABlocker(row + 1, col)) {
          markStep(row, col);
          row++;
        }
        matrix[row][col] = '<';
        break;
      case '<':
        while (!isInBorder(row, col) && !isABlocker(row, col - 1)) {
          markStep(row, col);
          col--;
        }
        matrix[row][col] = '^';
        break;
    }
    pos.row = row;
    pos.col = col;
    dir = matrix[row][col];
    
    return new Position(row, col, dir);
  }

  private static boolean isValidPosition(final int row, final int col) {
    if (row >= 0 && row < matrix.length && col >= 0 && col < matrix.length) {
      return true;
    }
    return false;
  }

  private static void markStep(final int row, final int col) {
    final char dir = matrix[row][col];
    final Position currentPos = new Position(row, col, dir);
    visitedPositions.add(currentPos);
  }

  private static void solvePartOne() {
    int result = 0;
    Position currentPos = findElement('^');
    if (currentPos == null) {
      System.out.println("Error, couldn't find element");  
      return;
    }
    while (!isInBorder(currentPos.row, currentPos.col)) {
      currentPos = advancePartOne(currentPos);
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

  private static Position advancePartOne(final Position pos) {
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
    if (!isValidPosition(row, col)) {
      return true;
    }
    char target = matrix[row][col];
    boolean result = target == '#';
    if (isPrintEnabled) System.out.println("isABlocker? " + row + ", " + col + " " + target + ", " + result);
    return result;
  }

  private static boolean isInBorder(final int row, final int col) {
    if (isPrintEnabled) System.out.println("isInBorder? " + row + ", " + col);
    if (row == 0 || row == matrix.length - 1) {
      if (isPrintEnabled) System.out.println("Yes, is in row border");
      return true;
    }
    if (col == 0 || col == matrix.length - 1) {
      if (isPrintEnabled) System.out.println("Yes, is in col border");
      return true;
    }
    if (isPrintEnabled) System.out.println("No, is not in a border");
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
    if (!isPrintEnabled) {
      return;
    }
    // Print the matrix to verify
    if (matrix != null) {
      for (char[] row : matrix) {
        System.out.println(Arrays.toString(row));
      }
    }
  }
}
