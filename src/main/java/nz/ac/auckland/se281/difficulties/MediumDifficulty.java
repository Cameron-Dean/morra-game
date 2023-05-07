package nz.ac.auckland.se281.difficulties;

import java.util.List;
import nz.ac.auckland.se281.Round;
import nz.ac.auckland.se281.strategies.AverageStrategy;
import nz.ac.auckland.se281.strategies.RandomStrategy;

public class MediumDifficulty implements DifficultyLevel {

  private List<Round> rounds;

  public MediumDifficulty(List<Round> rounds) {
    this.rounds = rounds;
  }

  @Override
  public int[] getPlay() {
    if (rounds.size() <= 3) {
      return new RandomStrategy().decideFingersAndSum();
    } else {
      return new AverageStrategy(rounds).decideFingersAndSum();
    }
  }
}
