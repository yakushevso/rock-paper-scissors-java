import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Tests extends StageTest<String> {
    String[] cases = new String[]{"rock",
            "paper",
            "scissors"};
    int loses = 0;
    int wins = 0;
    int draws = 0;

    CheckResult checkRandom(String reply, String attach) {
        CheckResult wrongRandomize = CheckResult.wrong(String.format(
                "The results of the games: %s wins, %s draws and %s loses\n" +
                        "Looks like you don't use the random module to choose a random option!\n" +
                        "The number of wins, draws and loses should be approximately the same.\n" +
                        "Make sure you output the results of the games the same way as in the examples!\n" +
                        "If you are sure that you use random module try to rerun the tests!\n", wins, draws, loses));
        if (loses < 30)
            return wrongRandomize;
        if (draws < 30)
            return wrongRandomize;
        if (wins < 30)
            return wrongRandomize;
        return CheckResult.correct();
    }

    public CheckResult check(String reply, String attach) {
        CheckResult wrongResult = CheckResult.wrong(String.format(
                "Seems like your answer (\"%s\") is either inconsistent " +
                        "with the rock-paper-scissors rules or the string is formatted incorrectly.  " +
                        "Check punctuation, spelling, and capitalization of your output. " +
                        "Also, make sure you are following the rules of the game.", reply.strip()));
        Dictionary<String, String> hits = new Hashtable<>();
        hits.put("rock", "scissors");
        hits.put("scissors", "paper");
        hits.put("paper", "rock");

        String computerOption = "not found";



        if ((reply.toLowerCase().contains("scissors") && (reply.toLowerCase().contains("paper") || reply.toLowerCase().contains("rock"))) ||
                (reply.toLowerCase().contains("paper") && reply.toLowerCase().contains("rock"))) {
            return CheckResult.wrong("Seems like your answer (\"" + reply.strip() + "\") contains more than one of possible" +
                    " options ['scissors', 'paper', 'rock']");
        }

        if (reply.toLowerCase().contains("scissors"))
            computerOption = "scissors";
        else if (reply.toLowerCase().contains("paper"))
            computerOption = "paper";
        else if (reply.toLowerCase().contains("rock"))
            computerOption = "rock";

        if (computerOption.contains("not found"))
            return wrongResult;

        if ((reply.toLowerCase().contains("well done") && (reply.toLowerCase().contains("draw") || reply.toLowerCase().contains("sorry"))) ||
                (reply.toLowerCase().contains("draw") && reply.toLowerCase().contains("sorry"))) {
            return CheckResult.wrong("Seems like your answer (\"" + reply.strip() + "\") contains more than one of possible" +
                    " results");
        }

        String result;
        String feedback;
        if (hits.get(attach).equals(computerOption)) {
            result = "well done";
            feedback = String.format("Well done. The computer chose %s and failed", hits.get(attach));
        } else if (attach.contains(computerOption)) {
            result = "draw";
            feedback = String.format("There is a draw (%s)", hits.get(attach));
        } else {
            result = "sorry";
            feedback = String.format("Sorry, but the computer chose %s", hits.get(attach));
        }

        if (!reply.toLowerCase().contains(result))
            return CheckResult.wrong(String.format("Incorrect result found in your answer (\"%s\") when player chose " +
                    "\"%s\" and computer chose \"%s\".\nExpected: \"%s\"", reply.strip(), attach, hits.get(attach),
                    feedback));

        if (reply.toLowerCase().contains("sorry"))
            loses += 1;
        else if (reply.toLowerCase().contains("draw"))
            draws += 1;
        else if (reply.toLowerCase().contains("well done"))
            wins += 1;
        else
            return wrongResult;
        return CheckResult.correct();

    }

    public List<TestCase<String>> generate() {
        List<String> inputs = new ArrayList<>();
        for (String option : cases) {
            for (int i = 0; i < 50; i++)
                inputs.add(option);
        }
        List<TestCase<String>> tests = new ArrayList<>();
        for (String input : inputs) {
            TestCase<String> testCase = new TestCase<>();
            testCase.setAttach(input);
            testCase.setInput(input);
            tests.add(testCase);
        }
        TestCase<String> testCase = new TestCase<>();
        testCase.setInput("rock");
        testCase.setAttach("rock");
        testCase.setCheckFunc(this::checkRandom);
        tests.add(testCase);
        return tests;
    }

}