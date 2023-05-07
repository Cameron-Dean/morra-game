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
    
    int[] inputs = checkValidRange();
    System.out.println(inputs[0] + " " + inputs[1]);
    rounds.add(new Round(inputs[0], inputs[1]));
  }

  public void showStats() {}
  
  private int[] getInputFromUser() {
    String input = Utils.scanner.nextLine();
    String[] inputs = input.split(" ");
    inputs = checkValidInts(inputs);
    int[] numbers = {Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1])};
    return numbers;
  }
  
  private String[] checkValidInts(String[] inputs) {
    // check if the input is two numbers that can be parsed as integers
    while (inputs.length != 2 || !Utils.isInteger(inputs[0]) || !Utils.isInteger(inputs[1])) {
      MessageCli.INVALID_INPUT.printMessage();
      MessageCli.ASK_INPUT.printMessage();
      String input = Utils.scanner.nextLine();
      inputs = input.split(" ");
    }
    
    return inputs;
  }
  
  private int[] checkValidRange() {
    int[] numbers = getInputFromUser();
    
    // check if the numbers are within the valid range
    while (numbers[0] < 1 || numbers[0] > 5 || numbers[1] < 1 || numbers[1] > 10) {
      MessageCli.INVALID_INPUT.printMessage();
      MessageCli.ASK_INPUT.printMessage();
      numbers = getInputFromUser();
    }
    
    return numbers;
  }
}
