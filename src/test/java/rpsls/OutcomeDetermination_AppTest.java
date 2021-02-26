package rpsls;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutcomeDetermination_AppTest {
    private App objectUnderTest;

    @BeforeEach
    public void createTestObject () {
        objectUnderTest = new App();
    }

    @ParameterizedTest
    @MethodSource("selectionsAndOutcome")
    public void whenTwoEntitiesPittedAgainstEachother_thenOutcomeCorrect(App.GameEntity left, App.GameEntity right, App.Outcome expected) {
        assertEquals(expected, objectUnderTest.determineTheOutcome(left, right));
    }

    private static Stream<Arguments> selectionsAndOutcome() {
        App.GameEntity scissors = App.entities.stream().filter(e->e.name().equals("Scissors")).findFirst().get();
        App.GameEntity paper = App.entities.stream().filter(e->e.name().equals("Paper")).findFirst().get();
        App.GameEntity rock = App.entities.stream().filter(e->e.name().equals("Rock")).findFirst().get();
        App.GameEntity lizard = App.entities.stream().filter(e->e.name().equals("Lizard")).findFirst().get();
        App.GameEntity spock = App.entities.stream().filter(e->e.name().equals("Spock")).findFirst().get();

        return Stream.of(
                Arguments.of(scissors, paper, App.Outcome.WIN),
                Arguments.of(scissors, rock, App.Outcome.LOSE),
                Arguments.of(scissors, lizard, App.Outcome.WIN),
                Arguments.of(scissors, spock, App.Outcome.LOSE),
                Arguments.of(scissors, scissors, App.Outcome.DRAW),

                Arguments.of(paper, paper, App.Outcome.DRAW),
                Arguments.of(paper, rock, App.Outcome.WIN),
                Arguments.of(paper, lizard, App.Outcome.LOSE),
                Arguments.of(paper, spock, App.Outcome.WIN),
                Arguments.of(paper, scissors, App.Outcome.LOSE),

                Arguments.of(rock, paper, App.Outcome.LOSE),
                Arguments.of(rock, rock, App.Outcome.DRAW),
                Arguments.of(rock, lizard, App.Outcome.WIN),
                Arguments.of(rock, spock, App.Outcome.LOSE),
                Arguments.of(rock, scissors, App.Outcome.WIN),

                Arguments.of(lizard, paper, App.Outcome.WIN),
                Arguments.of(lizard, rock, App.Outcome.LOSE),
                Arguments.of(lizard, lizard, App.Outcome.DRAW),
                Arguments.of(lizard, spock, App.Outcome.WIN),
                Arguments.of(lizard, scissors, App.Outcome.LOSE),

                Arguments.of(spock, paper, App.Outcome.LOSE),
                Arguments.of(spock, rock, App.Outcome.WIN),
                Arguments.of(spock, lizard, App.Outcome.LOSE),
                Arguments.of(spock, spock, App.Outcome.DRAW),
                Arguments.of(spock, scissors, App.Outcome.WIN)
        );
    }
}
