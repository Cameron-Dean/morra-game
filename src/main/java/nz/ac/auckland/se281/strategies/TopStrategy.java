package nz.ac.auckland.se281.strategies;

import java.util.List;
import nz.ac.auckland.se281.Round;
import nz.ac.auckland.se281.Utils;

public class TopStrategy implements Strategy {

  private List<Round> rounds;

  public TopStrategy(List<Round> rounds) {
    this.rounds = rounds;
  }

  @Override
  public int[] decideFingersAndSum() {
    int[] fingersAndSum = new int[2];

    fingersAndSum[0] = Utils.getRandomNumber(1, 5);
    fingersAndSum[1] = fingersAndSum[0] + mostCommonNumFingers();

    return fingersAndSum;
  }

  private int mostCommonNumFingers() {
    int mostCommonFingers = rounds.get(0).getFingers();
    int mostCommonCount = 0;

    // loop through each round to get overall mode value in array
    for (int i = 0; i < rounds.size() - 1; i++) {
      int count = 0;

      // loop through round within round to get number of times for each value
      for (int j = 0; j < rounds.size() - 1; j++) {
        if (rounds.get(j).getFingers() == rounds.get(i).getFingers()) {
          count++;
        }

        if (count > mostCommonCount) {
          // new mode value found, update result
          mostCommonFingers = rounds.get(i).getFingers();
          mostCommonCount = count;
        }
      }
    }

    return mostCommonFingers;
  }
}
