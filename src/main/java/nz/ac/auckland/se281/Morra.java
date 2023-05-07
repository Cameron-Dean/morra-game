package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;
import nz.ac.auckland.se281.Main.Difficulty;

public class Morra {

  private String name;
  private List<Round> rounds;
  private Difficulty currentDifficulty;

  public Morra() {
    this.rounds = new ArrayList<>();
  }

  public void newGame(Difficulty difficulty, int pointsToWin, String[] options) {
    this.currentDifficulty = difficulty;
    this.rounds.clear();
    this.name = options[0];
    MessageCli.WELCOME_PLAYER.printMessage(this.name);
  }

  public void play() {
    MessageCli.START_ROUND.printMessage(Integer.toString(rounds.size() + 1));
    MessageCli.ASK_INPUT.printMessage();

    // get play from user, then display
    int[] inputs = getValidInputs();
    rounds.add(new Round(inputs[0], inputs[1]));
    MessageCli.PRINT_INFO_HAND.printMessage(name, //
        Integer.toString(rounds.get(rounds.size() - 1).getFingers()), //
        Integer.toString(rounds.get(rounds.size() - 1).getSum()));

    // TODO: get play from AI, then display

  }

  public void showStats() {
    // TODO: complete this method
  }

  private int[] getInputFromUser() {
    String input = Utils.scanner.nextLine();
    String[] inputs = input.split(" ");
    inputs = checkValidInts(inputs);
    int[] numbers = { Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1]) };
    return numbers;
  }

  private String[] checkValidInts(String[] inputs) {
    // ensure the input is two numbers that can be parsed as integers
    while (inputs.length != 2 || !Utils.isInteger(inputs[0]) || !Utils.isInteger(inputs[1])) {
      MessageCli.INVALID_INPUT.printMessage();
      MessageCli.ASK_INPUT.printMessage();
      String input = Utils.scanner.nextLine();
      inputs = input.split(" ");
    }

    return inputs;
  }

  private int[] getValidInputs() {
    int[] numbers = getInputFromUser();

    // ensure the numbers are within the valid range
    while (numbers[0] < 1 || numbers[0] > 5 || numbers[1] < 1 || numbers[1] > 10) {
      MessageCli.INVALID_INPUT.printMessage();
      MessageCli.ASK_INPUT.printMessage();
      numbers = getInputFromUser();
    }

    return numbers;
  }
}
