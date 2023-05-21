package nz.ac.auckland.se281.difficulties;

import java.util.List;
import nz.ac.auckland.se281.Round;
import nz.ac.auckland.se281.strategies.AverageStrategy;
import nz.ac.auckland.se281.strategies.RandomStrategy;
import nz.ac.auckland.se281.strategies.TopStrategy;

public class MasterDifficulty extends DifficultyLevel {

  private List<Round> rounds;

  public MasterDifficulty(List<Round> rounds) {
    this.rounds = rounds;
  }

  @Override
  public void setStrategy() {
    if (rounds.size() <= 3) {
      super.strategy = new RandomStrategy();
    } else if (rounds.size() % 2 == 0) {
      super.strategy = new AverageStrategy(rounds);
    } else {
      // every second round after round 3 should use top strategy
      super.strategy = new TopStrategy(rounds);
    }
  }
}
