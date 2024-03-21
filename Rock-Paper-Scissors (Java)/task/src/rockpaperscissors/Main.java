package rockpaperscissors;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            String users = sc.nextLine();

            String computer = switch (users) {
                case "paper" -> "scissors";
                case "scissors" -> "rock";
                default -> "paper";
            };

            System.out.println("Sorry, but the computer chose " + computer);
        }
    }
}
