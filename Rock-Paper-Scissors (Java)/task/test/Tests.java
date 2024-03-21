import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.ArrayList;
import java.util.List;

public class Tests extends StageTest<String> {
    String[] cases = new String[]{"rock", "paper", "scissors"};

    public List<TestCase<String>> generate() {
        List<TestCase<String>> tests = new ArrayList<>();
        for (String input : cases) {
            TestCase<String> testCase = new TestCase<>();
            testCase.setAttach(input + "\n" + getWin(input));
            testCase.setInput(input);
            tests.add(testCase);
        }
        return tests;
    }

    public CheckResult check(String reply, String attach) {
        String[] atts = attach.split("\n");
        String correctOutput = String.format("Sorry, but the computer chose %s", atts[1].strip());
        if (!correctOutput.toLowerCase().replaceAll("[^a-z0-9]+", "").equals(
                reply.strip().toLowerCase().replaceAll("[^a-z0-9]+", ""))) {
            return CheckResult.wrong(String.format("Your answer on \"%s\" was \"%s\". " +
                    "This is a wrong output. The correct output is \"%s\"", atts[0], reply.strip(), correctOutput));
        }
        return CheckResult.correct();

    }

    String getWin(String input) {
        for (int i = 0; i < cases.length; i++)
            if (input.equals(cases[i])) {
                if (i + 1 < cases.length) return cases[i + 1];
                else return cases[0];
            }
        return "";
    }
}