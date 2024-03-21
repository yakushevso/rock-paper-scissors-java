package rockpaperscissors;

import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                String users = sc.nextLine();

                if (users.equals("scissors") || users.equals("rock") || users.equals("paper")) {
                    String computer = switch (new Random().nextInt(3)) {
                        case 0 -> "scissors";
                        case 1 -> "rock";
                        default -> "paper";
                    };

                    checkWin(users, computer);
                    System.out.println();
                } else if (users.equals("!exit")) {
                    System.out.println("Bye!");
                    break;
                } else {
                    System.out.println("Invalid input");
                }
            }
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
