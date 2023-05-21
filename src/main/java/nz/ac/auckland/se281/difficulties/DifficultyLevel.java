package nz.ac.auckland.se281.difficulties;

import nz.ac.auckland.se281.strategies.Strategy;

public abstract class DifficultyLevel {

  protected Strategy strategy;

  public abstract void setStrategy();

  public int[] getPlay() {
    setStrategy();
    return strategy.decideFingersAndSum();
  }
}
