package nz.ac.auckland.se281.difficulties;

import nz.ac.auckland.se281.strategies.RandomStrategy;

public class EasyDifficulty extends DifficultyLevel {

  @Override
  public void setStrategy() {
    super.strategy = new RandomStrategy();
  }
}
