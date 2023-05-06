package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;

import nz.ac.auckland.se281.Main.Difficulty;

public class Morra {
  
  private String name;
  private List<Round> rounds;

  public Morra() {
    this.rounds = new ArrayList<>();
  }

  public void newGame(Difficulty difficulty, int pointsToWin, String[] options) {
    this.name = options[0];
    MessageCli.WELCOME_PLAYER.printMessage(this.name);
  }

  public void play() {
    MessageCli.START_ROUND.printMessage(Integer.toString(rounds.size() + 1));
    MessageCli.ASK_INPUT.printMessage();
  }

  public void showStats() {}
}
