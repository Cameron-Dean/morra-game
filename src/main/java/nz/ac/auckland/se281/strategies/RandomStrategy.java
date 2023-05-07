package nz.ac.auckland.se281.strategies;

import nz.ac.auckland.se281.Utils;

public class RandomStrategy implements Strategy {

  @Override
  public int[] decideFingersAndSum() {
    int[] fingersAndSum = new int[2];

    fingersAndSum[0] = Utils.getRandomNumber(1, 5);
    fingersAndSum[1] = Utils.getRandomNumber(fingersAndSum[0] + 1, fingersAndSum[0] + 5);

    return fingersAndSum;
  }
}
