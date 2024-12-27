package HoofIt;

import java.io.*;
import java.util.*;

// https://adventofcode.com/2024/day/10

public class Solution {
  private static int[][] matrix = null;

  private static class Position {
    int row, col;
    Position(int row, int col) {
      this.row = row;
      this.col = col;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      Position position = (Position) obj;
      return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
      return Objects.hash(row, col);
    }
  }

  private static class Zero {
    Position pos;
    Set<Position> nines = new HashSet<>();
    List<Position> ninesAll = new ArrayList<>();
    Zero(Position pos) {
      this.pos = pos;
    }

    public int getScore() {
      return nines.size();
    }

    public int getRating() {
      return ninesAll.size();
    }
  }

  public static void main(String[] args) {
    if (args.length == 0) {
      return;
    }
    final String fileName = args[0];
    System.out.println(fileName);

    readFile(fileName);
    print();
    solve();
  }

  private static void solve() {
    int result1 = 0, result2 = 0;
    List<Zero> zeros = getZeros();
    findPaths(zeros);
    for (Zero zero : zeros) {
      result1 += zero.getScore();
      result2 += zero.getRating();
      System.out.println("Zero " + zero.pos + " = " + zero.ninesAll);
    }
    System.out.println("Solution part one " + result1);
    System.out.println("Solution part two " + result2);
  }

  private static void findPaths(List<Zero> zeros) {
    for (int i = 0; i < zeros.size(); i++) {
      findPaths(zeros.get(i), zeros.get(i).pos);
    }
  }

  private static void findPaths(Zero zero, Position pos) {
    // System.out.println("findPaths " + pos + " = " + matrix[pos.row][pos.col]);
    if (matrix[pos.row][pos.col] == 9) {
      final Position ninePos = new Position(pos.row, pos.col);
      zero.nines.add(ninePos);
      zero.ninesAll.add(ninePos);
      return;
    }

    // up
    Position next = new Position(pos.row - 1, pos.col);
    if (canMove(pos, next)) { 
      findPaths(zero, next);
    }

    // right
    next.row = pos.row;
    next.col = pos.col + 1;
    if (canMove(pos, next)) { 
      findPaths(zero, next);
    }

    // down
    next.row = pos.row + 1;
    next.col = pos.col;
    if (canMove(pos, next)) { 
      findPaths(zero, next);
    }

    // left
    next.row = pos.row;
    next.col = pos.col - 1;
    if (canMove(pos, next)) { 
      findPaths(zero, next);
    }
  }

  private static List<Zero> getZeros() {
    List<Zero> result = new ArrayList<>();
    for (int row = 0; row < matrix.length; row++) {
      for (int col = 0; col < matrix[row].length; col++) {
        if (matrix[row][col] == 0) {
          System.out.println("Zero in " + row + ", " + col);
          result.add(new Zero(new Position(row, col)));
        }
      }
    }
    return result;
  }

  private static boolean isValidPosition(final Position pos) {
    if (pos.row >= 0 && pos.row < matrix.length && pos.col >= 0 && pos.col < matrix.length) {
      return true;
    }
    return false;
  }

  private static boolean canMove(final Position curr, final Position next) {
    if (!isValidPosition(next)) {
      return false;
    }
    if (matrix[curr.row][curr.col] + 1 == matrix[next.row][next.col]) {
      return true;
    }
    return false;
  }

  private static void print() {
    if (matrix != null) {
      for (int[] row : matrix) {
        System.out.println(Arrays.toString(row));
      }
    }
  }

  private static void readFile(final String fileName) {
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      List<int[]> rows = new ArrayList<>();
      String line;
      
      while ((line = br.readLine()) != null) {
        int[] row = new int[line.length()];
        for (int i = 0; i < line.length(); i++) {
          row[i] = Character.getNumericValue(line.charAt(i));
        }
        rows.add(row);
      }

      // Convert List<int[]> to int[][]
      matrix = rows.toArray(new int[rows.size()][]);
    } catch (IOException e) {
      System.err.println("Error reading file: " + e.getMessage());
    }
  }
}