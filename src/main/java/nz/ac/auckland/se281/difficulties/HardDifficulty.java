package nz.ac.auckland.se281.difficulties;

import java.util.List;
import nz.ac.auckland.se281.Round;
import nz.ac.auckland.se281.strategies.RandomStrategy;
import nz.ac.auckland.se281.strategies.TopStrategy;

public class HardDifficulty extends DifficultyLevel {

  private List<Round> rounds;

  public HardDifficulty(List<Round> rounds) {
    this.rounds = rounds;
  }

  @Override
  public void setStrategy() {
    if (rounds.size() <= 3) {
      super.strategy = new RandomStrategy();
    } else {
      super.strategy = new TopStrategy(rounds);
    }
  }
}
