package nz.ac.auckland.se281;

import static nz.ac.auckland.se281.Main.Command.*;
import static nz.ac.auckland.se281.MessageCli.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    MainTest.Task1.class, //
    MainTest.Task2.class, //
    MainTest.Task3.class, //
    MainTest.Task4.class, //
    MainTest.Task5.class, //
    MainTest.Task6.class, //
    MainTest.YourTests.class
})
public class MainTest {

  private static String getOutputByRound(int round, String output) {
    try {
      return output.split("Start Round")[round];

    } catch (Exception e) {
      throw new RuntimeException(
          "Something is wrong in your code, your should print something like this after each round"
              + MessageCli.START_ROUND.toString());
    }
  }

  private static int[] getPlay(int round, String player, String output) {
    int[] out = new int[2];

    try {

      String roundOut = output
          .split(START_ROUND.getMessage(String.valueOf(round)))[1]
          .split("Player " + player + ": fingers")[1];
      Pattern pattern = Pattern.compile("-?\\d+");
      Matcher matcher = pattern.matcher(roundOut);
      matcher.find();
      out[0] = Integer.parseInt(matcher.group());
      matcher.find();
      out[1] = Integer.parseInt(matcher.group());
    } catch (Exception e) {
      throw new RuntimeException(
          "Something is wrong in your code, your should print something like this after each round"
              + MessageCli.PRINT_INFO_HAND.toString());
    }

    return out;
  }

  public static class Task1 extends CliTest {

    public Task1() {
      super(Main.class);
    }

    @Test
    public void T1_G01_new_game() throws Exception {
      runCommands(NEW_GAME + " EASY 1", "Valerio");
      assertContains(WELCOME_PLAYER.getMessage("Valerio"));
      assertContains(END.getMessage());
    }

    @Test
    public void T1_G02_play_start_round() throws Exception {
      runCommands(
          NEW_GAME + " EASY 1",
          "Valerio",
          //
          PLAY,
          "1 2");
      assertContains(START_ROUND.getMessage("1"));
      assertDoesNotContain(START_ROUND.getMessage("0"));
      assertDoesNotContain(START_ROUND.getMessage("2"));
    }

    @Test
    public void T1_G03_play_ask_for_input() throws Exception {
      runCommands(
          NEW_GAME + " EASY 1",
          "Valerio",
          //
          PLAY,
          "1 2");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
    }

    @Test
    public void T1_G04_play_ask_for_input_wrong_sum() throws Exception {
      runCommands(
          NEW_GAME + " EASY 1",
          "Valerio",
          //
          PLAY,
          "1 0",
          "1 2");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      assertContains(INVALID_INPUT.getMessage());
    }

    @Test
    public void T1_G05_play_ask_for_input_wrong_fingers() throws Exception {
      runCommands(
          NEW_GAME + " EASY 1",
          "Valerio",
          //
          PLAY,
          "-5 2",
          "1 2");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      assertContains(INVALID_INPUT.getMessage());
    }

    @Test
    public void T1_G06_play_ask_for_input_ok() throws Exception {
      runCommands(
          NEW_GAME + " EASY 1",
          "Valerio",
          //
          PLAY,
          "1 2");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(2, res[1]);
    }

    @Test
    public void T1_G07_play_ask_for_input_ok2() throws Exception {
      runCommands(
          NEW_GAME + " EASY 1",
          "Valerio",
          //
          PLAY,
          "5 6");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(5, res[0]);
      assertEquals(6, res[1]);
    }

    @Test
    public void T1_G08_play_ask_for_input_ok_not_valid_two_rounds() throws Exception {
      runCommands(
          NEW_GAME + " EASY 2",
          "Valerio",
          //
          PLAY,
          "3 1",
          //
          PLAY,
          "5 10");
      assertContains(WELCOME_PLAYER.getMessage("Valerio"));

      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(3, res[0]);
      assertEquals(1, res[1]);
      res = MainTest.getPlay(2, "Valerio", getCaptureOut());
      assertEquals(5, res[0]);
      assertEquals(10, res[1]);
    }

    @Test
    public void T1_G09_play_ask_for_input_two_rounds_second_wrong() throws Exception {
      runCommands(
          NEW_GAME + " EASY 2",
          "Valerio",
          //
          PLAY,
          "3 1",
          //
          PLAY,
          "-34 10",
          "5 10");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(3, res[0]);
      assertEquals(1, res[1]);
      res = MainTest.getPlay(2, "Valerio", getCaptureOut());
      assertEquals(5, res[0]);
      assertEquals(10, res[1]);
      assertContains(INVALID_INPUT.getMessage());
    }

    @Test
    public void T1_G10_play_ask_for_input_two_rounds_second_wrong() throws Exception {
      runCommands(
          NEW_GAME + " EASY 2",
          "Valerio",
          //
          PLAY,
          "10000 1",
          "1 4",
          //
          PLAY,
          "5 10");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      assertContains(INVALID_INPUT.getMessage());
      int[] res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(4, res[1]);
    }

  }

  public static class Task2 extends CliTest {

    public Task2() {
      super(Main.class);
    }

    @Test
    public void T2_G01_play_ask_for_input_Jarvis() throws Exception {
      runCommands(
          NEW_GAME + " EASY 1",
          "Valerio",
          //
          PLAY,
          "1 10");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(10, res[1]);
      res = MainTest.getPlay(1, "Jarvis", getCaptureOut());
      assertTrue(res[0] > 0);
      assertTrue(res[1] > 0);
    }

    @Test
    public void T2_G02_play_ask_for_input_Jarvis_random_seed() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " EASY 1",
          "Valerio",
          //
          PLAY,
          "1 3");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(3, res[1]);
      res = MainTest.getPlay(1, "Jarvis", getCaptureOut());
      int fingersJarvis = res[0];
      int sumJarvis = res[1];
      assertEquals(1, fingersJarvis);
      assertEquals(5, sumJarvis);
    }

    @Test
    public void T2_G03_play_ask_for_input_Jarvis_random_seed_two_rounds() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " EASY 2",
          "Valerio",
          //
          PLAY,
          "2 3"
          //
          ,
          PLAY,
          "1 10");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(2, "Valerio", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(10, res[1]);
      res = MainTest.getPlay(2, "Jarvis", getCaptureOut());
      int fingersJarvis = res[0];
      int sumJarvis = res[1];
      assertEquals(3, fingersJarvis);
      assertEquals(7, sumJarvis);
    }

    @Test
    public void T2_G04_play_ask_for_input_Jarvis_random_seed_human_wins() throws Exception {
      Utils.random = new java.util.Random(0);
      runCommands(
          NEW_GAME + " EASY 1",
          "Valerio",
          //
          PLAY,
          "5 6");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(1, "Jarvis", getCaptureOut());
      int fingersJarvis = res[0];
      int sumJarvis = res[1];
      assertEquals(1, fingersJarvis);
      assertEquals(5, sumJarvis);
      assertContains(PRINT_OUTCOME_ROUND.getMessage("HUMAN_WINS"));
    }

    @Test
    public void T2_G05_play_ask_for_input_Jarvis_random_seed_jarvis_wins() throws Exception {
      Utils.random = new java.util.Random(0);
      runCommands(
          NEW_GAME + " EASY 1",
          "Valerio",
          //
          PLAY,
          "4 8");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(1, "Jarvis", getCaptureOut());
      int fingersJarvis = res[0];
      int sumJarvis = res[1];
      assertEquals(1, fingersJarvis);
      assertEquals(5, sumJarvis);
      assertContains(PRINT_OUTCOME_ROUND.getMessage("AI_WINS"));
      assertDoesNotContain(PRINT_OUTCOME_ROUND.getMessage("HUMAN_WINS"));
    }

    @Test
    public void T2_G06_play_ask_for_input_Jarvis_random_seed_draw() throws Exception {
      Utils.random = new java.util.Random(0);
      runCommands(
          NEW_GAME + " EASY 1",
          "Valerio",
          //
          PLAY,
          "1 3");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(1, "Jarvis", getCaptureOut());
      int fingersJarvis = res[0];
      int sumJarvis = res[1];
      assertEquals(1, fingersJarvis);
      assertEquals(5, sumJarvis);
      assertContains(PRINT_OUTCOME_ROUND.getMessage("DRAW"));
    }

  }

  public static class Task3 extends CliTest {

    public Task3() {
      super(Main.class);
    }

    @Test
    public void T3_G01_play_ask_for_input_Jarvis() throws Exception {
      runCommands(
          NEW_GAME + " MEDIUM 1",
          "Valerio",
          //
          PLAY,
          "1 10");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(10, res[1]);
      res = MainTest.getPlay(1, "Jarvis", getCaptureOut());
      assertTrue(res[0] > 0);
      assertTrue(res[1] > 0);
    }

    @Test
    public void T3_G02_play_ask_for_input_Jarvis_random_seed() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " MEDIUM 1",
          "Valerio",
          //
          PLAY,
          "1 3");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(3, res[1]);
      res = MainTest.getPlay(1, "Jarvis", getCaptureOut());
      int fingersJarvis = res[0];
      int sumJarvis = res[1];
      assertEquals(1, fingersJarvis);
      assertEquals(5, sumJarvis);
    }

    @Test
    public void T3_G03_play_ask_for_input_Jarvis_random_seed_two_rounds() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " MEDIUM 2",
          "Valerio",
          //
          PLAY,
          "2 3"
          //
          ,
          PLAY,
          "1 10");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(2, "Valerio", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(10, res[1]);
      res = MainTest.getPlay(2, "Jarvis", getCaptureOut());
      int fingersJarvis = res[0];
      int sumJarvis = res[1];
      assertEquals(3, fingersJarvis);
      assertEquals(7, sumJarvis);
      assertTrue(MainTest.getOutputByRound(2, getOutput()).contains(PRINT_OUTCOME_ROUND.getMessage("DRAW")));
      assertFalse(MainTest.getOutputByRound(2, getOutput()).contains(PRINT_OUTCOME_ROUND.getMessage("AI_WINS")));
    }

    @Test
    public void T3_G04_play_ask_for_input_Jarvis_random_seed_three_rounds() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " MEDIUM 3",
          "Valerio",
          //
          PLAY,
          "2 3"
          //
          ,
          PLAY,
          "1 10" //
          ,
          PLAY,
          "2 3");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(2, "Valerio", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(10, res[1]);
      res = MainTest.getPlay(3, "Jarvis", getCaptureOut());
      int fingersJarvis = res[0];
      int sumJarvis = res[1];
      assertEquals(5, fingersJarvis);
      assertEquals(10, sumJarvis);
      assertTrue(MainTest.getOutputByRound(3, getOutput()).contains(PRINT_OUTCOME_ROUND.getMessage("DRAW")));

    }

    @Test
    public void T3_G05_play_ask_for_input_Jarvis_random_seed_four_rounds() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " MEDIUM 4",
          "Valerio",
          //
          PLAY,
          "2 3"
          //
          ,
          PLAY,
          "1 10"
          //
          ,
          PLAY,
          "2 3"
          //
          ,
          PLAY,
          "2 3");

      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(2, "Valerio", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(10, res[1]);
      res = MainTest.getPlay(4, "Jarvis", getCaptureOut());
      int fingersJarvis = res[0];
      int sumJarvis = res[1];
      // with four rounds, the average should start!
      // expected average is
      // 2 + 1 + 2 = 5 [of course Jarvis cannot cheat, he does not know that the
      // current finger is
      // 4]
      // 5/3 = 1.66
      // rounded is 2
      // because the fingers that Jarvis randomly choose is 5 the sum should be 7 = 5
      // + 2
      assertEquals(5, fingersJarvis);
      assertEquals(7, sumJarvis);
      assertTrue(MainTest.getOutputByRound(4, getOutput()).contains(PRINT_OUTCOME_ROUND.getMessage("AI_WINS")));

    }

    @Test
    public void T3_G06_play_ask_for_input_Jarvis_random_seed_five_rounds() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " MEDIUM 3",
          "Valerio",
          //
          PLAY,
          "2 3"
          //
          ,
          PLAY,
          "1 10"
          //
          ,
          PLAY,
          "2 3"
          //
          ,
          PLAY,
          "4 3"
          //
          ,
          PLAY,
          "5 7");

      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(2, "Valerio", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(10, res[1]);
      res = MainTest.getPlay(5, "Jarvis", getCaptureOut());
      int fingersJarvis = res[0];
      int sumJarvis = res[1];
      // with four rounds, the average should start!
      // expected average is
      // 2 + 1 + 2 + 4 = 9 [of course Jarvis cannot cheat, he does not know that the
      // current finger
      // is 5]
      // 9/4 = 2.25
      // rounded is 2
      // because the fingers that Jarvis randomly choose is 2 the sum should be 4 = 2
      // + 2
      assertEquals(2, fingersJarvis);
      assertEquals(4, sumJarvis);
      assertTrue(MainTest.getOutputByRound(5, getOutput()).contains(PRINT_OUTCOME_ROUND.getMessage("HUMAN_WINS")));

    }

  }

  public static class Task4 extends CliTest {

    public Task4() {
      super(Main.class);
    }

    @Test
    public void T4_G01_play_ask_for_input_Jarvis() throws Exception {
      runCommands(
          NEW_GAME + " HARD 1",
          "Valerio",
          //
          PLAY,
          "1 10");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(10, res[1]);
      res = MainTest.getPlay(1, "Jarvis", getCaptureOut());
      assertTrue(res[0] > 0);
      assertTrue(res[1] > 0);
    }

    @Test
    public void T4_G02_play_ask_for_input_Jarvis_random_seed() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " HARD 1",
          "Valerio",
          //
          PLAY,
          "4 3");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(4, res[0]);
      assertEquals(3, res[1]);
      res = MainTest.getPlay(1, "Jarvis", getCaptureOut());
      int fingersJarvis = res[0];
      int sumJarvis = res[1];
      assertEquals(1, fingersJarvis);
      assertEquals(5, sumJarvis);
      assertTrue(MainTest.getOutputByRound(1, getOutput()).contains(PRINT_OUTCOME_ROUND.getMessage("AI_WINS")));

    }

    @Test
    public void T4_G03_play_ask_for_input_Jarvis_random_seed_two_rounds() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " HARD 2",
          "Valerio",
          //
          PLAY,
          "2 3"
          //
          ,
          PLAY,
          "1 10");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(2, "Valerio", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(10, res[1]);
      res = MainTest.getPlay(2, "Jarvis", getCaptureOut());
      int fingersJarvis = res[0];
      int sumJarvis = res[1];
      assertEquals(3, fingersJarvis);
      assertEquals(7, sumJarvis);
      assertTrue(MainTest.getOutputByRound(2, getOutput()).contains(PRINT_OUTCOME_ROUND.getMessage("DRAW")));

    }

    @Test
    public void T4_G04_play_ask_for_input_Jarvis_random_seed_three_rounds() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " HARD 3",
          "Valerio",
          //
          PLAY,
          "2 3"
          //
          ,
          PLAY,
          "1 10" //
          ,
          PLAY,
          "5 3");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(2, "Valerio", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(10, res[1]);
      res = MainTest.getPlay(3, "Jarvis", getCaptureOut());
      int fingersJarvis = res[0];
      int sumJarvis = res[1];
      assertEquals(5, fingersJarvis);
      assertEquals(10, sumJarvis);
      assertTrue(MainTest.getOutputByRound(3, getOutput()).contains(PRINT_OUTCOME_ROUND.getMessage("AI_WINS")));

    }

    @Test
    public void T4_G05_play_ask_for_input_Jarvis_random_seed_four_rounds() throws Exception {
      Utils.random = new java.util.Random(2);
      runCommands(
          NEW_GAME + " HARD 3",
          "Valerio",
          //
          PLAY,
          "5 3"
          //
          ,
          PLAY,
          "1 10"
          //
          ,
          PLAY,
          "5 3"
          //
          ,
          PLAY,
          "4 3");

      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(2, "Valerio", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(10, res[1]);
      res = MainTest.getPlay(4, "Jarvis", getCaptureOut());
      int fingersJarvis = res[0];
      int sumJarvis = res[1];
      // the most common is 5 [of course Jarvis cannot cheat, he does not know that
      // the current finger
      // because the fingers that Jarvis randomly choose is 2 the sum should be 7 = 2
      // + 5
      assertEquals(2, fingersJarvis);
      assertEquals(7, sumJarvis);
    }

    @Test
    public void T4_G06_play_ask_for_input_Jarvis_random_seed_five_rounds() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " HARD 5",
          "Valerio",
          //
          PLAY,
          "1 3"
          //
          ,
          PLAY,
          "1 10"
          //
          ,
          PLAY,
          "2 3"
          //
          ,
          PLAY,
          "4 3"
          //
          ,
          PLAY,
          "2 4");

      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(2, "Valerio", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(10, res[1]);
      res = MainTest.getPlay(5, "Jarvis", getCaptureOut());
      int fingersJarvis = res[0];
      int sumJarvis = res[1];
      // the most common is 1 [of course Jarvis cannot cheat, he does not know that
      // the current finger
      // because the fingers that Jarvis randomly choose is 2 the sum should be 3 = 2
      // + 1
      assertEquals(2, fingersJarvis);
      assertEquals(3, sumJarvis);
      assertTrue(MainTest.getOutputByRound(5, getOutput()).contains(PRINT_OUTCOME_ROUND.getMessage("HUMAN_WINS")));

    }

  }

  public static class Task5 extends CliTest {

    public Task5() {
      super(Main.class);
    }

    @Test
    public void T5_G01_play_ask_for_input_Jarvis() throws Exception {
      runCommands(
          NEW_GAME + " MASTER 1",
          "Valerio",
          //
          PLAY,
          "1 10");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(10, res[1]);
      res = MainTest.getPlay(1, "Jarvis", getCaptureOut());
      assertTrue(res[0] > 0);
      assertTrue(res[1] > 0);
    }

    @Test
    public void T5_G02_play_ask_for_input_Jarvis_random_seed() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " MASTER 1",
          "Valerio",
          //
          PLAY,
          "4 3");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(1, "Valerio", getCaptureOut());
      assertEquals(4, res[0]);
      assertEquals(3, res[1]);
      res = MainTest.getPlay(1, "Jarvis", getCaptureOut());
      int fingersJarvis = res[0];
      int sumJarvis = res[1];
      assertEquals(1, fingersJarvis);
      assertEquals(5, sumJarvis);
      assertTrue(MainTest.getOutputByRound(1, getOutput()).contains(PRINT_OUTCOME_ROUND.getMessage("AI_WINS")));

    }

    @Test
    public void T5_G03_play_ask_for_input_Jarvis_random_seed_two_rounds() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " MASTER 2",
          "Valerio",
          //
          PLAY,
          "2 3"
          //
          ,
          PLAY,
          "1 10");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(2, "Valerio", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(10, res[1]);
      res = MainTest.getPlay(2, "Jarvis", getCaptureOut());
      int fingersJarvis = res[0];
      int sumJarvis = res[1];
      assertEquals(3, fingersJarvis);
      assertEquals(7, sumJarvis);
      assertTrue(MainTest.getOutputByRound(2, getOutput()).contains(PRINT_OUTCOME_ROUND.getMessage("DRAW")));

    }

    @Test
    public void T5_G04_play_ask_for_input_Jarvis_random_seed_three_rounds() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " MASTER 3",
          "Valerio",
          //
          PLAY,
          "2 3"
          //
          ,
          PLAY,
          "1 10" //
          ,
          PLAY,
          "5 3");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(2, "Valerio", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(10, res[1]);
      res = MainTest.getPlay(3, "Jarvis", getCaptureOut());
      int fingersJarvis = res[0];
      int sumJarvis = res[1];
      assertEquals(5, fingersJarvis);
      assertEquals(10, sumJarvis);
      assertTrue(MainTest.getOutputByRound(3, getOutput()).contains(PRINT_OUTCOME_ROUND.getMessage("AI_WINS")));
      assertFalse(MainTest.getOutputByRound(3, getOutput()).contains(PRINT_OUTCOME_ROUND.getMessage("HUMAN_WINS")));

    }

    @Test
    public void T5_G05_play_ask_for_input_Jarvis_random_seed_four_rounds() throws Exception {
      Utils.random = new java.util.Random(2);
      runCommands(
          NEW_GAME + " MASTER 3",
          "Valerio",
          //
          PLAY,
          "1 3"
          //
          ,
          PLAY,
          "5 10"
          //
          ,
          PLAY,
          "1 3"
          //
          ,
          PLAY,
          "4 6");

      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(2, "Valerio", getCaptureOut());
      assertEquals(5, res[0]);
      assertEquals(10, res[1]);
      res = MainTest.getPlay(4, "Jarvis", getCaptureOut());
      int fingersJarvis = res[0];
      int sumJarvis = res[1];
      // with four rounds, the average should start!
      // expected average is
      // 1 + 5 + 1 =7 [of course Jarvis cannot cheat, he does not know that the
      // current finger is 4]
      // 7/3 = 2.3
      // rounded is 2
      // because the fingers that Jarvis randomly choose is 2 the sum should be 4 = 2
      // + 2
      assertEquals(2, fingersJarvis);
      assertEquals(4, sumJarvis);
      assertTrue(MainTest.getOutputByRound(4, getOutput()).contains(PRINT_OUTCOME_ROUND.getMessage("HUMAN_WINS")));

    }

    @Test
    public void T5_G06_play_ask_for_input_Jarvis_random_seed_five_rounds() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " MASTER 5",
          "Valerio",
          //
          PLAY,
          "2 3"
          //
          ,
          PLAY,
          "5 10"
          //
          ,
          PLAY,
          "2 3"
          //
          ,
          PLAY,
          "2 3"
          //
          ,
          PLAY,
          "2 4");

      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      int[] res = MainTest.getPlay(2, "Valerio", getCaptureOut());
      assertEquals(5, res[0]);
      assertEquals(10, res[1]);
      res = MainTest.getPlay(5, "Jarvis", getCaptureOut());
      int fingersJarvis = res[0];
      int sumJarvis = res[1];
      // the most common is 2 [of course Jarvis cannot cheat, he does not know that
      // the current finger
      // because the fingers that Jarvis randomly choose is 2 the sum should be 4 = 2
      // + 2
      assertEquals(2, fingersJarvis);
      assertEquals(4, sumJarvis);
      assertTrue(MainTest.getOutputByRound(5, getOutput()).contains(PRINT_OUTCOME_ROUND.getMessage("DRAW")));

    }

  }

  public static class Task6 extends CliTest {

    public Task6() {
      super(Main.class);
    }

    @Test
    public void T6_G01_play_before_start() throws Exception {
      runCommands(
          PLAY);
      assertContains(MessageCli.GAME_NOT_STARTED.getMessage());
    }

    @Test
    public void T6_G02_start_game_twice() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " EASY 3",
          "Valerio",
          //
          PLAY,
          "4 3", NEW_GAME + " EASY 3",
          "Valerio",
          //
          PLAY,
          "4 3");
      assertContains(WELCOME_PLAYER.getMessage("Valerio"));
      assertContains(START_ROUND.getMessage("1"));
      assertDoesNotContain(START_ROUND.getMessage("2"));
      assertDoesNotContain(MessageCli.GAME_NOT_STARTED.getMessage());

    }

    @Test
    public void T6_G03_wins_one_round() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " EASY 1",
          "Valerio",
          //
          PLAY,
          "1 2");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      assertTrue(MainTest.getOutputByRound(1, getOutput()).contains(PRINT_OUTCOME_ROUND.getMessage("HUMAN_WINS")));
      assertContains(END_GAME.getMessage("Valerio", "1"));
    }

    @Test
    public void T6_G04_jarvis_wins_one_round() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " EASY 1",
          "Valerio",
          //
          PLAY,
          "4 3");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      assertTrue(MainTest.getOutputByRound(1, getOutput()).contains(PRINT_OUTCOME_ROUND.getMessage("AI_WINS")));
      assertDoesNotContain(END_GAME.getMessage("Valerio", "1"));
      assertContains(END_GAME.getMessage("Jarvis", "1"));
    }

    @Test
    public void T6_G05_human_wins_two_rounds() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " EASY 1",
          "Valerio",
          //
          PLAY,
          "3 3"
          //
          , PLAY, "1 4");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      assertTrue(MainTest.getOutputByRound(1, getOutput()).contains(PRINT_OUTCOME_ROUND.getMessage("DRAW")));
      assertDoesNotContain(END_GAME.getMessage("Valerio", "1"));
      assertDoesNotContain(END_GAME.getMessage("Jarvis", "1"));
      assertTrue(MainTest.getOutputByRound(2, getOutput()).contains(PRINT_OUTCOME_ROUND.getMessage("HUMAN_WINS")));
      assertContains(END_GAME.getMessage("Valerio", "2"));
      assertDoesNotContain(END_GAME.getMessage("Jarvis", "2"));

    }

    @Test
    public void T6_G06_human_wins_two_rounds() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " EASY 2",
          "Valerio",
          //
          PLAY,
          "1 2"
          //
          , PLAY, "1 4");
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      assertTrue(MainTest.getOutputByRound(1, getOutput()).contains(PRINT_OUTCOME_ROUND.getMessage("HUMAN_WINS")));
      assertDoesNotContain(END_GAME.getMessage("Valerio", "1"));
      assertDoesNotContain(END_GAME.getMessage("Jarvis", "1"));
      assertTrue(MainTest.getOutputByRound(2, getOutput()).contains(PRINT_OUTCOME_ROUND.getMessage("HUMAN_WINS")));
      assertContains(END_GAME.getMessage("Valerio", "2"));
      assertDoesNotContain(END_GAME.getMessage("Jarvis", "2"));

    }

    @Test
    public void T6_G07_show_stats_fail() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          SHOW_STATS);
      assertContains(GAME_NOT_STARTED.getMessage());
    }

    @Test
    public void T6_G08_show_stats_ok() throws Exception {
      Utils.random = new java.util.Random(1);
      runCommands(
          NEW_GAME + " EASY 2",
          "Valerio",
          //
          PLAY,
          "1 2", SHOW_STATS
          //
          , PLAY, "1 4", SHOW_STATS);
      assertContains(START_ROUND.getMessage("1"));
      assertContains(ASK_INPUT.getMessage());
      assertTrue(MainTest.getOutputByRound(1, getOutput()).contains(PRINT_OUTCOME_ROUND.getMessage("HUMAN_WINS")));
      assertDoesNotContain(END_GAME.getMessage("Valerio", "1"));
      assertDoesNotContain(END_GAME.getMessage("Jarvis", "1"));
      assertContains(PRINT_PLAYER_WINS.getMessage("Valerio", "1", "1"));
      assertContains(PRINT_PLAYER_WINS.getMessage("Jarvis", "0", "2"));
      assertDoesNotContain(PRINT_PLAYER_WINS.getMessage("Valerio", "0", "0"));
      // SECOND ROUND
      assertTrue(MainTest.getOutputByRound(2, getOutput()).contains(PRINT_OUTCOME_ROUND.getMessage("HUMAN_WINS")));
      assertContains(END_GAME.getMessage("Valerio", "2"));
      assertDoesNotContain(END_GAME.getMessage("Jarvis", "2"));
      // GAME ENDS
      assertContains(GAME_NOT_STARTED.getMessage());
    }

  }

  public static class YourTests extends CliTest {

    public YourTests() {
      super(Main.class);
    }

    @Test
    public void T1_M01_play_ask_for_input_two_rounds_both_wrong_1() throws Exception {
      runCommands(
          NEW_GAME + " EASY 2",
          "Thomas",
          //
          PLAY,
          "a b",
          "1 1",
          //
          PLAY,
          "5",
          "5 10");
      assertTrue(MainTest.getOutputByRound(1, getOutput()).contains("#1:"));
      assertTrue(MainTest.getOutputByRound(2, getOutput()).contains("#2:"));
      assertContains(ASK_INPUT.getMessage());
      assertContains(INVALID_INPUT.getMessage());
      int[] res = MainTest.getPlay(1, "Thomas", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(1, res[1]);
      res = MainTest.getPlay(2, "Thomas", getCaptureOut());
      assertEquals(5, res[0]);
      assertEquals(10, res[1]);
    }

    @Test
    public void T1_M02_play_ask_for_input_two_rounds_both_wrong_2() throws Exception {
      runCommands(
          NEW_GAME + " EASY 2",
          "Jack",
          //
          PLAY,
          "5 5 5",
          "0 1",
          "1 10",
          //
          PLAY,
          "1 0",
          "6 10",
          "5 11",
          "2  5",
          " 4 8",
          "5 1");
      assertTrue(MainTest.getOutputByRound(1, getOutput()).contains("#1:"));
      assertTrue(MainTest.getOutputByRound(2, getOutput()).contains("#2:"));
      assertContains(ASK_INPUT.getMessage());
      assertContains(INVALID_INPUT.getMessage());
      int[] res = MainTest.getPlay(1, "Jack", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(10, res[1]);
      res = MainTest.getPlay(2, "Jack", getCaptureOut());
      assertEquals(5, res[0]);
      assertEquals(1, res[1]);
    }

    @Test
    public void T1_M03_three_rounds_check_round_number_output() throws Exception {
      runCommands(
          NEW_GAME + " EASY 2",
          "Louis",
          //
          PLAY,
          "5 9 ",
          //
          PLAY,
          "3 7",
          //
          PLAY,
          "1 4");
      assertTrue(MainTest.getOutputByRound(1, getOutput()).contains("#1:"));
      assertTrue(MainTest.getOutputByRound(2, getOutput()).contains("#2:"));
      assertTrue(MainTest.getOutputByRound(3, getOutput()).contains("#3:"));
      assertContains(ASK_INPUT.getMessage());
      assertDoesNotContain(INVALID_INPUT.getMessage());
      int[] res = MainTest.getPlay(1, "Louis", getCaptureOut());
      assertEquals(5, res[0]);
      assertEquals(9, res[1]);
      res = MainTest.getPlay(2, "Louis", getCaptureOut());
      assertEquals(3, res[0]);
      assertEquals(7, res[1]);
      res = MainTest.getPlay(3, "Louis", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(4, res[1]);
    }

    @Test
    public void T2_M01_play_ask_for_input_Jarvis_random_seed_draw_two_rounds() throws Exception {
      Utils.random = new java.util.Random(281);
      runCommands(
          NEW_GAME + " EASY 1",
          "Ben",
          //
          PLAY,
          "1 3",
          //
          PLAY,
          "3 6");
      assertTrue(MainTest.getOutputByRound(1, getOutput()).contains("#1:"));
      assertContains(ASK_INPUT.getMessage());
      // ROUND 1
      int[] res = MainTest.getPlay(1, "Ben", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(3, res[1]);
      res = MainTest.getPlay(1, "Jarvis", getCaptureOut());
      int fingersJarvis = res[0];
      int sumJarvis = res[1];
      assertEquals(1, fingersJarvis);
      assertEquals(6, sumJarvis);
      assertTrue(MainTest.getOutputByRound(1, getOutput()).contains(PRINT_OUTCOME_ROUND.getMessage("DRAW")));
      // ROUND 2
      assertTrue(MainTest.getOutputByRound(2, getOutput()).contains("#2:"));
      res = MainTest.getPlay(2, "Ben", getCaptureOut());
      assertEquals(3, res[0]);
      assertEquals(6, res[1]);
      res = MainTest.getPlay(2, "Jarvis", getCaptureOut());
      fingersJarvis = res[0];
      sumJarvis = res[1];
      assertEquals(4, fingersJarvis);
      assertEquals(7, sumJarvis);
      assertTrue(MainTest.getOutputByRound(2, getOutput()).contains(PRINT_OUTCOME_ROUND.getMessage("AI_WINS")));
    }

    @Test
    public void T3_M01_play_five_rounds_first_game_new_game_six_rounds_second_game() throws Exception {
      Utils.random = new java.util.Random(281);
      runCommands(
          NEW_GAME + " MEDIUM 10",
          "Lachlan",
          // GAME 1
          PLAY,
          "1 3",
          //
          PLAY,
          "1 4",
          //
          PLAY,
          "1 2",
          //
          PLAY,
          "1 5",
          //
          PLAY,
          "1 6",
          // GAME 2
          NEW_GAME + " MEDIUM 10",
          "Zack",
          //
          PLAY,
          "5 6",
          //
          PLAY,
          "5 7",
          //
          PLAY,
          "5 8",
          //
          PLAY,
          "5 9",
          //
          PLAY,
          "5 10",
          //
          PLAY,
          "5 1");
      assertTrue(MainTest.getOutputByRound(1, getOutput()).contains("#1:"));
      assertContains(ASK_INPUT.getMessage());
      // GAME 1 ROUND 5
      int[] res = MainTest.getPlay(5, "Lachlan", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(6, res[1]);
      res = MainTest.getPlay(5, "Jarvis", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(2, res[1]);
      // GAME 2 ROUND 6
      res = MainTest.getPlay(6, "Zack", getCaptureOut());
      assertEquals(5, res[0]);
      assertEquals(1, res[1]);
      res = MainTest.getPlay(6, "Jarvis", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(6, res[1]); // if Jarvis doesn't forget after new game, res[1] will be <4>
    }

    @Test
    public void T4_M01_play_five_rounds_first_game_six_rounds_second_game() throws Exception {
      Utils.random = new java.util.Random(281);
      runCommands(
          NEW_GAME + " HARD 10",
          "Nicholas",
          // GAME 1
          PLAY,
          "1 3",
          //
          PLAY,
          "1 4",
          //
          PLAY,
          "1 2",
          //
          PLAY,
          "1 5",
          //
          PLAY,
          "1 6",
          // GAME 2
          NEW_GAME + " HARD 10",
          "Finn",
          //
          PLAY,
          "5 6",
          //
          PLAY,
          "5 7",
          //
          PLAY,
          "1 2",
          //
          PLAY,
          "2 3",
          //
          PLAY,
          "3 4",
          //
          PLAY,
          "4 5");
      assertTrue(MainTest.getOutputByRound(1, getOutput()).contains("#1:"));
      assertContains(ASK_INPUT.getMessage());
      // GAME 1 ROUND 5
      int[] res = MainTest.getPlay(5, "Nicholas", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(6, res[1]);
      res = MainTest.getPlay(5, "Jarvis", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(2, res[1]);
      // GAME 2 ROUND 6
      res = MainTest.getPlay(6, "Finn", getCaptureOut());
      assertEquals(4, res[0]);
      assertEquals(5, res[1]);
      res = MainTest.getPlay(6, "Jarvis", getCaptureOut());
      assertEquals(1, res[0]);
      assertEquals(6, res[1]); // if Jarvis doesn't forget after new game, res[1] will be <1>
    }
  }
}
