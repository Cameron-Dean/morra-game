package nz.ac.auckland.se281.difficulties;

import java.util.List;
import nz.ac.auckland.se281.Round;
import nz.ac.auckland.se281.strategies.AverageStrategy;
import nz.ac.auckland.se281.strategies.RandomStrategy;
import nz.ac.auckland.se281.strategies.TopStrategy;

public class MasterDifficulty implements DifficultyLevel {

  private List<Round> rounds;

  public MasterDifficulty(List<Round> rounds) {
    this.rounds = rounds;
  }

  @Override
  public int[] getPlay() {
    if (rounds.size() <= 3) {
      return new RandomStrategy().decideFingersAndSum();
    } else if (rounds.size() % 2 == 0) {
      return new AverageStrategy(rounds).decideFingersAndSum();
    } else {
      return new TopStrategy(rounds).decideFingersAndSum();
    }
  }

}
