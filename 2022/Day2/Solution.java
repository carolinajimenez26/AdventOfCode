import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Solution {
  private static enum Shape {
    ROCK("X"),
    PAPER("Y"),
    SICCORS("Z");

    private final String shape;

    Shape(final String shape) {
      this.shape = shape;
    }

    public static Shape getShape(final String shape) {
      switch (shape) {
        case "A":
        case "X":
          return ROCK;

        case "B":
        case "Y":
          return PAPER;

        default:
          return SICCORS;
      }
    }

    public static int getScore(final Shape shape) {
      switch (shape) {
        case ROCK:
          return 1;

        case PAPER:
          return 2;

        default:
          return 3;
      }
    }

    // which shape was used given shape and outcome
    public static Shape getShape(final Shape shape, final Outcome outcome) {
      if (outcome.equals(Outcome.DRAW)) {
        return shape;
      }
      // System.out.println("getShape given " + shape + " and " + outcome);

      switch (shape) {
        case ROCK:
          if (outcome.equals(Outcome.LOST)) {
            return SICCORS;
          }
          // WON
          return PAPER;

        case PAPER:
          if (outcome.equals(Outcome.LOST)) {
            return ROCK;
          }
          // WON
          return SICCORS;

        default: // SICCORS
          if (outcome.equals(Outcome.LOST)) {
            return PAPER;
          }
          // WON
          return ROCK;
      }
    }
  };

  private static enum Outcome {
    LOST,
    DRAW,
    WON;

    private static Outcome getOutcome(final Shape shapeChosenPlayer1,
        final Shape shapeChosenPlayer2) {

      if (shapeChosenPlayer1.equals(shapeChosenPlayer2)) {
        return Outcome.DRAW;
      }

      if (shapeChosenPlayer2.equals(Shape.ROCK)) {
        if (shapeChosenPlayer1.equals(Shape.PAPER)) {
          return Outcome.LOST;
        }
        return Outcome.WON;
      }

      if (shapeChosenPlayer2.equals(Shape.PAPER)) {
        if (shapeChosenPlayer1.equals(Shape.ROCK)) {
          return Outcome.WON;
        }
        return Outcome.LOST;
      }

      // SICCORS
      if (shapeChosenPlayer1.equals(Shape.ROCK)) {
        return Outcome.LOST;
      }
      return Outcome.WON;
    }

    private static int getOutcomeScore(final Outcome outcome) {
      switch (outcome) {
        case WON:
          return 6;
        case DRAW:
          return 3;
        default:
          return 0;
      }
    }

    private static Outcome getOutcome(final Shape shape) {
      switch (shape) {
        case ROCK:
          return LOST;
        case PAPER:
          return DRAW;
        default:
          return WON;
      }
    }
  };

  public static void main(String[] args) {
    if (args.length == 0) {
      return;
    }

    final String fileName = args[0];
    System.out.println("Solution part 1: " + calculateScore1(fileName));
    System.out.println("Solution part 2: " + calculateScore2(fileName));
  }

  private static long calculateScore1(final String fileName) {
    long result = 0;

    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      String line;

      while ((line = br.readLine()) != null) {

        if (!line.isEmpty()) {
          final String[] splited = line.split("\\s+");
          final Shape shapeChosenPlayer1 = Shape.getShape(splited[0]);
          final Shape shapeChosenPlayer2 = Shape.getShape(splited[1]);
          final Outcome currOutcome = Outcome.getOutcome(shapeChosenPlayer1, shapeChosenPlayer2);
          final int outcomeScore = Outcome.getOutcomeScore(currOutcome);
          final int shapeScore = Shape.getScore(shapeChosenPlayer2);

          result += outcomeScore + shapeScore;
        }
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    return result;
  }

  private static long calculateScore2(final String fileName) {
    long result = 0;

    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      String line;

      while ((line = br.readLine()) != null) {

        if (!line.isEmpty()) {
          final String[] splited = line.split("\\s+");
          final Shape shapeChosenPlayer1 = Shape.getShape(splited[0]);
          final Shape shapeChosenPlayer2 = Shape.getShape(splited[1]);
          final Outcome neededOutcome = Outcome.getOutcome(shapeChosenPlayer2);
          // System.out.println(shapeChosenPlayer2 + " = " + neededOutcome);

          final int outcomeScore = Outcome.getOutcomeScore(neededOutcome);
          final Shape neededShape = Shape.getShape(shapeChosenPlayer1, neededOutcome);
          // System.out.println("neededShape: " + neededShape);
          final int shapeScore = Shape.getScore(neededShape);

          // System.out.println(line + " = " + "(" + shapeScore + " + " + outcomeScore +
          // ")");
          result += outcomeScore + shapeScore;
        }
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    return result;
  }
}