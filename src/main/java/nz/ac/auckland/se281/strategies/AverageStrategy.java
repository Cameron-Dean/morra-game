package nz.ac.auckland.se281.strategies;

import java.util.List;
import nz.ac.auckland.se281.Round;
import nz.ac.auckland.se281.Utils;

public class AverageStrategy implements Strategy {

  private List<Round> rounds;

  public AverageStrategy(List<Round> rounds) {
    this.rounds = rounds;
  }

  @Override
  public int[] decideFingersAndSum() {
    int[] fingersAndSum = new int[2];

    fingersAndSum[0] = Utils.getRandomNumber(1, 5);

    // calculate sum using average human fingers
    int total = 0;
    for (int i = 0; i < rounds.size() - 1; i++) {
      total += rounds.get(i).getFingers();
    }
    fingersAndSum[1] = fingersAndSum[0] + (int) Math.round((double) total / (rounds.size() - 1));

    return fingersAndSum;
  }
}
