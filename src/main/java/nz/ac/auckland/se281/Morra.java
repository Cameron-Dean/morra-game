package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;
import nz.ac.auckland.se281.Main.Difficulty;
import nz.ac.auckland.se281.difficulties.DifficultyLevel;
import nz.ac.auckland.se281.difficulties.DifficultyFactory;

public class Morra {

  private String name;
  private List<Round> rounds;
  private DifficultyLevel currentDifficulty;
  private int pointsToWin;
  private int humanPoints;
  private int jarvisPoints;

  public Morra() {
    this.rounds = new ArrayList<>();
    this.pointsToWin = 0;
  }

  public void newGame(Difficulty difficulty, int pointsToWin, String[] options) {
    resetGame();
    this.name = options[0];
    this.currentDifficulty = DifficultyFactory.chooseDifficultyLevel(difficulty, rounds);
    this.pointsToWin = pointsToWin;

    MessageCli.WELCOME_PLAYER.printMessage(this.name);
  }

  public void play() {
    if (this.pointsToWin == 0) {
      MessageCli.GAME_NOT_STARTED.printMessage();
      return;
    }

    MessageCli.START_ROUND.printMessage(Integer.toString(rounds.size() + 1));
    MessageCli.ASK_INPUT.printMessage();

    // get play from user, then display
    int[] humanPlay = getValidInputs();
    rounds.add(new Round(humanPlay[0], humanPlay[1]));
    MessageCli.PRINT_INFO_HAND.printMessage(name, //
        Integer.toString(rounds.get(rounds.size() - 1).getFingers()), //
        Integer.toString(rounds.get(rounds.size() - 1).getSum()));

    // get play from AI, then display
    int[] jarvisPlay = currentDifficulty.getPlay();
    MessageCli.PRINT_INFO_HAND.printMessage("Jarvis", //
        Integer.toString(jarvisPlay[0]), //
        Integer.toString(jarvisPlay[1]));

    displayOutcome(humanPlay, jarvisPlay);

    if (humanPoints == pointsToWin || jarvisPoints == pointsToWin) {
      MessageCli.END_GAME.printMessage((humanPoints == pointsToWin) ? name : "Jarvis", Integer.toString(rounds.size()));
      resetGame();
    }
  }

  public void showStats() {
    if (this.pointsToWin == 0) {
      MessageCli.GAME_NOT_STARTED.printMessage();
      return;
    }

    MessageCli.PRINT_PLAYER_WINS.printMessage(name, //
        Integer.toString(humanPoints), //
        Integer.toString(pointsToWin - humanPoints));

    MessageCli.PRINT_PLAYER_WINS.printMessage("Jarvis", //
        Integer.toString(jarvisPoints), //
        Integer.toString(pointsToWin - jarvisPoints));
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

  private void displayOutcome(int[] humanPlay, int[] jarvisPlay) {
    int trueSum = humanPlay[0] + jarvisPlay[0];

    if (humanPlay[1] == trueSum && humanPlay[1] != jarvisPlay[1]) {
      MessageCli.PRINT_OUTCOME_ROUND.printMessage("HUMAN_WINS");
      this.humanPoints++;
    } else if (jarvisPlay[1] == trueSum && humanPlay[1] != jarvisPlay[1]) {
      MessageCli.PRINT_OUTCOME_ROUND.printMessage("AI_WINS");
      this.jarvisPoints++;
    } else {
      MessageCli.PRINT_OUTCOME_ROUND.printMessage("DRAW");
    }
  }

  private void resetGame() {
    this.rounds.clear();
    this.pointsToWin = 0;
    this.humanPoints = 0;
    this.jarvisPoints = 0;
  }
}
