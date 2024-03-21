package rockpaperscissors;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String users = scanner.nextLine();

            String computer = switch (new Random().nextInt(3)) {
                case 0 -> "scissors";
                case 1 -> "rock";
                default -> "paper";
            };

            checkWin(users, computer);
        }
    }

    private static void checkWin(String users, String computer) {
        if (users.equals(computer)) {
            System.out.printf("There is a draw (%s)", computer);
        } else if (users.equals("paper") && computer.equals("scissors") ||
                users.equals("scissors") && computer.equals("rock") ||
                users.equals("rock") && computer.equals("paper")) {
            System.out.printf("Sorry, but the computer chose %s", computer);
        } else {
            System.out.printf("Well done. The computer chose %s and failed", computer);
        }
    }
}
