package nz.ac.auckland.se281.difficulties;

import nz.ac.auckland.se281.Main.Difficulty;

public class DifficultyFactory {

  public static DifficultyLevel chooseDifficultyLevel(Difficulty difficultyLevel) {
    switch (difficultyLevel) {
      case EASY:
        return new EasyDifficulty();
      default:
        return new EasyDifficulty();
    }
  }
}
