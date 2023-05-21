package nz.ac.auckland.se281.difficulties;

import java.util.List;
import nz.ac.auckland.se281.Round;
import nz.ac.auckland.se281.strategies.AverageStrategy;
import nz.ac.auckland.se281.strategies.RandomStrategy;
import nz.ac.auckland.se281.strategies.Strategy;
import nz.ac.auckland.se281.strategies.TopStrategy;

public class MasterDifficulty implements DifficultyLevel {

  private List<Round> rounds;
  private Strategy strategy;

  public MasterDifficulty(List<Round> rounds) {
    this.rounds = rounds;
    setStrategy();
  }

  @Override
  public void setStrategy() {
    if (rounds.size() <= 3) {
      this.strategy = new RandomStrategy();
    } else if (rounds.size() % 2 == 0) {
      this.strategy = new AverageStrategy(rounds);
    } else {
      // every second round after round 3 should use top strategy
      this.strategy = new TopStrategy(rounds);
    }
  }

  @Override
  public int[] getPlay() {
    setStrategy();
    return strategy.decideFingersAndSum();
  }
}
