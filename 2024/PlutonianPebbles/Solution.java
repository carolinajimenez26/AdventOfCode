package PlutonianPebbles;

import java.io.*;
import java.util.*;

// https://adventofcode.com/2024/day/11

public class Solution {
  private static class Pair {
    long a, b;
    Pair(long a, long b) {
      this.a = a;
      this.b = b;
    }

    @Override
    public String toString() {
      return "(" + a + ", " + b + ")";
    }
  }

  private static class Stone {
    long number;
    int digits;
    Pair transformed;

    Stone(long number) {
      this.number = number;
      this.digits = getAmountDigits();
      this.transformed = transform();
    }

    private Pair transform() {
      if (number == 0) return new Pair(1, -1);
      if (digits % 2 != 0) return new Pair(number * 2024, -1);
      return split();
    }

    private Pair split() {
      int halfDigits = digits / 2;
      int divider = 10;
      while (halfDigits > 1) {
        divider *= 10;
        halfDigits--;
      }
      long a = number / divider;
      long b = number % divider;
      return new Pair(a, b);
    }

    private int getAmountDigits() {
      long curr = number;
      int result = 0;
      while (curr / 10 != 0) {
        curr /= 10;
        result++;
      }
      return result + 1;
    }

    public String print() {
      return "[" + number + " digits " + digits + " transformed " + transformed + "]";
    }

    @Override
    public String toString() {
      return number + " ";
    }
  }

  public static void main(String[] args) {
    if (args.length == 0) {
      return;
    }
    final String filePath = args[0];
    System.out.println(filePath);
    final List<Integer> stonesRaw = readFile(filePath);
    System.out.println("stonesRaw " + stonesRaw);
    final List<Stone> stones = convert(stonesRaw);
    System.out.println("stones " + stones);
    solve(stones, 25);
  }

  private static void solve(List<Stone> stones, int times) {
    for (int i = 0; i < times; i++) {
      stones = blink(stones);
      // System.out.println("-----");
      // System.out.println("stones " + stones);
    }
    System.out.println("Solution: " + stones.size());
  }

  private static List<Stone> blink(final List<Stone> stones) {
    List<Stone> result = new ArrayList<>();
    for (Stone stone : stones) {
      Pair transformed = stone.transformed;
      long a = transformed.a, b = transformed.b;
      result.add(new Stone(a));
      if (b != -1) result.add(new Stone(b));
    }
    return result;
  }

  private static List<Stone> convert(final List<Integer> stones) {
    List<Stone> result = new ArrayList<>();
    for (int number : stones) {
      result.add(new Stone(number));
    }
    return result;
  }

  private static List<Integer> readFile(final String filePath) {
    List<Integer> numbers = new ArrayList<>();
    try (Scanner scanner = new Scanner(new File(filePath))) {
      while (scanner.hasNextInt()) {
        numbers.add(scanner.nextInt());
      }
    } catch (FileNotFoundException e) {
      System.err.println("File not found: " + filePath);
    }
    return numbers;
  }
}