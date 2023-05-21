package nz.ac.auckland.se281.difficulties;

import java.util.List;
import nz.ac.auckland.se281.Round;
import nz.ac.auckland.se281.strategies.AverageStrategy;
import nz.ac.auckland.se281.strategies.RandomStrategy;
import nz.ac.auckland.se281.strategies.Strategy;

public class MediumDifficulty implements DifficultyLevel {

  private List<Round> rounds;
  private Strategy strategy;

  public MediumDifficulty(List<Round> rounds) {
    this.rounds = rounds;
    setStrategy();
  }

  @Override
  public void setStrategy() {
    if (rounds.size() <= 3) {
      this.strategy = new RandomStrategy();
    } else {
      this.strategy = new AverageStrategy(rounds);
    }
  }

  @Override
  public int[] getPlay() {
    setStrategy();
    return strategy.decideFingersAndSum();
  }
}
