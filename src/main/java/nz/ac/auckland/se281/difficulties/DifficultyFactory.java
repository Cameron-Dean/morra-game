package nz.ac.auckland.se281.difficulties;

import java.util.List;
import nz.ac.auckland.se281.Round;
import nz.ac.auckland.se281.Main.Difficulty;

public class DifficultyFactory {

  public static DifficultyLevel chooseDifficultyLevel(Difficulty difficultyLevel, List<Round> rounds) {
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
