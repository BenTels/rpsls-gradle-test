/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package rpsls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class App {

    record GameEntity(String name, int index) {
    }


    enum Outcome {
        WIN, LOSE, DRAW
    }

    protected static final Set<GameEntity> entities = Set.of(
            new GameEntity("Scissors", 0),
            new GameEntity("Paper", 1),
            new GameEntity("Rock", 2),
            new GameEntity("Lizard", 3),
            new GameEntity("Spock", 4)
    );

    protected GameEntity selectEntity() {
        int i = ThreadLocalRandom.current().nextInt(entities.size());
        return entities.parallelStream().filter(e -> e.index() == i).findFirst().get();
    }

    protected Optional<GameEntity> findEntity(String name) {
        return entities.parallelStream().filter(e -> e.name().equalsIgnoreCase(name)).findFirst();
    }

    protected Outcome determineTheOutcome(GameEntity userSelection, GameEntity ourSelection) {
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

    private void playTheGameInteractively() {
        try (var inputReader = new BufferedReader(new InputStreamReader(System.in))) {
            boolean stillPlaying = true;

            while (stillPlaying) {
                GameEntity ourChoice = selectEntity();
                System.out.printf("Pick an option (%s): ", entities.stream().map(GameEntity::name).collect(Collectors.joining(",")));
                var userOption = inputReader.readLine();
                System.out.println();
                var userChoice = findEntity(userOption);
                if (userChoice.isPresent()) {
                    doARound(ourChoice, userChoice);
                } else {
                    stillPlaying = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doARound(GameEntity ourChoice, Optional<GameEntity> userChoice) {
        GameEntity userSelection = userChoice.get();
        System.out.print("We chose %s; you chose %s.... ".formatted(ourChoice.name(), userSelection.name()));
        Outcome outcome = determineTheOutcome(userSelection, ourChoice);
        System.out.println(
                switch (outcome) {
                    case DRAW -> "It's a DRAW!!";
                    default -> "You " + outcome.name();
                }
        );
    }

    public static void main(String[] args) {
        new App().playTheGameInteractively();
    }

}
