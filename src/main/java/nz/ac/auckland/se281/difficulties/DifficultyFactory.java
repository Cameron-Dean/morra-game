package nz.ac.auckland.se281.difficulties;

import java.util.List;
import nz.ac.auckland.se281.Main.Difficulty;
import nz.ac.auckland.se281.Round;

public class DifficultyFactory {

  public static DifficultyLevel chooseDifficultyLevel(Difficulty difficultyLevel,
      List<Round> rounds) {
    // factory to swap between difficulty levels depending on what user chooses
    switch (difficultyLevel) {
      case EASY:
        return new EasyDifficulty();
      case MEDIUM:
        return new MediumDifficulty(rounds);
      case HARD:
        return new HardDifficulty(rounds);
      case MASTER:
        return new MasterDifficulty(rounds);
      default:
        return new EasyDifficulty();
    }
  }
}
