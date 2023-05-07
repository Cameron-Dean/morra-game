package nz.ac.auckland.se281.difficulties;

import nz.ac.auckland.se281.strategies.RandomStrategy;

public class EasyDifficulty implements DifficultyLevel {

  @Override
  public int[] getPlay() {
    return new RandomStrategy().decideFingersAndSum();
  }
}
