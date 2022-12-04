import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Solution {
  public static void main(String[] args) {
    if (args.length == 0) {
      return;
    }

    final String fileName = args[0];
    System.out.println("Solution part 1: " +
        findMaxCaloriesCarriedByElf(fileName));
    System.out.println("Solution part 2: " +
        findMaxCaloriesCarriedByElfTopX(fileName, 3));
  }

  private static int findMaxCaloriesCarriedByElf(final String fileName) {
    int maxSum = 0;

    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      String line;
      int currSum = 0;

      while ((line = br.readLine()) != null) {

        if (line.isEmpty()) {
          maxSum = Math.max(maxSum, currSum);
          currSum = 0;
        } else {
          int n = Integer.parseInt(line);
          currSum += n;
        }
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    return maxSum;
  }

  private static int findMaxCaloriesCarriedByElfTopX(final String fileName,
      final int x) {
    final List<Integer> calories = new ArrayList<Integer>();
    int currSum = 0;

    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      String line;
      while ((line = br.readLine()) != null) {
        if (line.isEmpty()) {
          calories.add(currSum);
          currSum = 0;
        } else {
          int n = Integer.parseInt(line);
          currSum += n;
        }
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    Collections.sort(calories);
    Collections.reverse(calories);

    int result = 0;
    int count = 0;
    for (final int calory : calories) {
      if (count == x) {
        break;
      }
      count += 1;
      result += calory;
    }

    return result;
  }
}