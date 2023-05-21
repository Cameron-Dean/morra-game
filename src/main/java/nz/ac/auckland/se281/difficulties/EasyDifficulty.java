package nz.ac.auckland.se281.difficulties;

import nz.ac.auckland.se281.strategies.RandomStrategy;
import nz.ac.auckland.se281.strategies.Strategy;

public class EasyDifficulty implements DifficultyLevel {

  private Strategy strategy;

  public EasyDifficulty() {
    setStrategy();
  }

  @Override
  public void setStrategy() {
    this.strategy = new RandomStrategy();
  }

  @Override
  public int[] getPlay() {
    setStrategy();
    return strategy.decideFingersAndSum();
  }
}
