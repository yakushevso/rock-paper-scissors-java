import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.ArrayList;
import java.util.List;

public class Tests extends StageTest<String> {
    String[] cases = new String[]{"rock",
            "paper",
            "scissors"};
    int loses = 0;
    int wins = 0;
    int draws = 0;
    int startScore = 350;
    String userName = "Bob";

    public List<TestCase<String>> generate() {
        String[] validInputCases = {"rock\npaper\nscissors\npaper\nscissors\nrock\npaper\nscissors\n!exit",
                "scissors\nscissors\nscissors\n!exit"};
        String[] invalidInputCases = {"rock\npaper\npaper\nscissors\nblabla\n!exit",
                "rock\ninvalid\n!exit",
                "rock\nrock\nrock\nrock-n-roll\n!exit"};
        List<TestCase<String>> tests = new ArrayList<>();
        //Cases that checks multiple input
        for (String input : validInputCases) {
            TestCase<String> testCase = new TestCase<>();
            testCase.setCheckFunc(this::checkValidInputs);
            testCase.setAttach(String.valueOf(input.split("\n").length - 1));
            testCase.setInput(input);
            tests.add(testCase);
        }
        //Cases that check invalid input
        for (String input : invalidInputCases) {
            TestCase<String> testCase = new TestCase<>();
            testCase.setCheckFunc(this::checkInvalidInput);
            testCase.setInput(input);
            tests.add(testCase);
        }
        //Cases that check using random module
        String longInput = "rock\n".repeat(100) + "!exit";
        TestCase<String> testCase = new TestCase<>();
        testCase.setCheckFunc(this::checkResults);
        testCase.setAttach("rock");
        testCase.setInput(longInput);
        tests.add(testCase);
        return tests;

    }

    CheckResult checkInvalidInput(String reply, String attach) {
        if (!reply.toLowerCase().contains("invalid"))
            return CheckResult.wrong(
                    "Looks like your program doesn't handle invalid inputs correctly.\n" +
                            "You should print 'Invalid input' if the input can't be processed.");
        return CheckResult.correct();
    }

    CheckResult checkValidInputs(String reply, String attach) {
        int results = 0;
        int attachInt = Integer.parseInt(attach);
        for (String s : reply.toLowerCase().split("\n")) {
            if (s.contains("sorry"))
                results++;
            if (s.contains("draw"))
                results++;
            if (s.contains("well done"))
                results++;
        }
        if (results != attachInt) {
            return CheckResult.wrong(String.format(
                    "Not enough results of the games were printed!\n " +
                            "Tried to input %s actions and got %s results of the games.\n" +
                            "Perhaps your program did not run enough games. " +
                            "Is it set up correctly to loop until the user inputs ‘!exit’? \n" +
                            "Also, make sure you print the result  of the game " +
                            "in the correct format after each valid input!",
                    attach, results));
        }
        return CheckResult.correct();
    }

    CheckResult checkResults(String reply, String attach) {
        for (String line : reply.split("\n")) {
            String lowerLine = line.toLowerCase();
            if (lowerLine.contains("sorry"))
                loses++;
            else if (lowerLine.contains("draw"))
                draws++;
            else if (lowerLine.contains("well done"))
                wins++;

            if (lowerLine.contains("well done") && !lowerLine.contains("scissors"))
                return CheckResult.wrong(String.format(
                        "Wrong result of the game:\n> rock\n%s\nRock can only beat scissors!", line));
            else if (lowerLine.contains("draw") && !lowerLine.contains("rock"))
                return CheckResult.wrong(String.format(
                        "Wrong result of the game:\n> rock\n%s\n" +
                                "The game ends with a draw only when the user " +
                                "and the computer both choose the same option", line));
            else if (lowerLine.contains("sorry") && !lowerLine.contains("paper"))
                return CheckResult.wrong(String.format(
                        "Wrong result of the game:\n> rock\n%s\nOnly paper can beat rock!", line));

        }
        CheckResult wrongRandomize = CheckResult.wrong(String.format(
                "The results of the games: %s wins, %s draws and %s loses\n" +
                        "The game is too easy to win. Is the computer being too predictable? " +
                        "The number of wins, draws and loses should be approximately the same.\n" +
                        "Perhaps you don't use the random module to choose random option.\n" +
                        "Also, make sure you output the results of the games " +
                        "the same way as was stated in the examples!\n" +
                        "If you are sure that you use the random module, try to rerun the tests!\n",
                wins, draws, loses));

        if (loses < 20)
            return wrongRandomize;
        if (draws < 20)
            return wrongRandomize;
        if (wins < 20)
            return wrongRandomize;

        return CheckResult.correct();

    }

}