/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package rpsls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class App {

    private record GameEntity(String name, int index) {
    }


    private enum Outcome {
        WIN, LOSE, DRAW
    }

    private static final Set<GameEntity> entities = Set.of(
            new GameEntity("Scissors", 0),
            new GameEntity("Paper", 1),
            new GameEntity("Rock", 2),
            new GameEntity("Lizard", 3),
            new GameEntity("Spock", 4)
    );

    private static Random random = new Random();

    private static GameEntity selectEntity() {
        int i = random.nextInt(entities.size());
        return entities.parallelStream().filter(e -> e.index() == i).findFirst().get();
    }

    private static GameEntity findEntity(String name) {
        return entities.parallelStream().filter(e -> e.name().toLowerCase().equals(name.toLowerCase())).findFirst().orElse(null);
    }

    private static Outcome compare(GameEntity userSelection, GameEntity ourSelection) {
        if (userSelection == ourSelection) {
            return Outcome.DRAW;
        }
        if (userSelection.index() < ourSelection.index()) {
            int diff = ourSelection.index() - userSelection.index();
            return diff % 2 == 1 ? Outcome.WIN : Outcome.LOSE;
        } else {
            int diff = userSelection.index() - ourSelection.index();
            return diff % 2 == 0 ? Outcome.WIN : Outcome.LOSE;
        }
    }

    public static void main(String[] args) {
        try (var inputReader = new BufferedReader(new InputStreamReader(System.in))) {
            GameEntity userChoice = entities.iterator().next();

            while (userChoice != null) {
                GameEntity ourChoice = selectEntity();
                System.out.printf("Pick an option (%s): ", entities.stream().map(e -> e.name()).collect(Collectors.joining(",")));
                var userOption = inputReader.readLine();
                System.out.println();
                System.out.println("*" + userOption);
                userChoice = findEntity(userOption);
                if (userChoice != null) {
                    System.out.print("We chose %s; you chose %s.... ".formatted(ourChoice.name(), userChoice.name()));
                    Outcome outcome = compare(userChoice, ourChoice);
                    System.out.println(
                            switch (outcome) {
                                case DRAW -> "It a DRAW!!";
                                default -> "You " + outcome.name();
                            }
                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
