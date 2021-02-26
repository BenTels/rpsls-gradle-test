package rpsls;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

public class EntitySelectionAppTest {
    private App objectUnderTest;

    @BeforeEach
    public void createTestObject () {
        objectUnderTest = new App();
    }

    @Test
    public void whenEntityRandomlySelected_thenSelectedFromAppEntities() {
        for (int i = 0; i < 100; i++) {
            App.GameEntity gameEntity = objectUnderTest.selectEntity();
            assertTrue(App.entities.contains(gameEntity));
        }
    }

    @ParameterizedTest
    @MethodSource("nameAndMatchingEntity")
    public void whenEntityRetrievedByName_thenCorrect(String name, App.GameEntity expected){
        assertEquals(expected, objectUnderTest.findEntity(name).get(), () -> "The entity found is not correct");
    }

    private static Stream<Arguments> nameAndMatchingEntity() {
        App.GameEntity scissors = App.entities.stream().filter(e->e.name().equals("Scissors")).findFirst().get();
        App.GameEntity paper = App.entities.stream().filter(e->e.name().equals("Paper")).findFirst().get();
        App.GameEntity rock = App.entities.stream().filter(e->e.name().equals("Rock")).findFirst().get();
        App.GameEntity lizard = App.entities.stream().filter(e->e.name().equals("Lizard")).findFirst().get();
        App.GameEntity spock = App.entities.stream().filter(e->e.name().equals("Spock")).findFirst().get();

        return Stream.of(
                Arguments.of("Scissors", scissors),
                Arguments.of("scissors", scissors),
                Arguments.of("SciSSors", scissors),
                Arguments.of("SCisSOrS", scissors),
                Arguments.of("Paper", paper),
                Arguments.of("paper", paper),
                Arguments.of("PAPEr", paper),
                Arguments.of("PapeR", paper),
                Arguments.of("Rock", rock),
                Arguments.of("rock", rock),
                Arguments.of("Lizard", lizard),
                Arguments.of("lizard", lizard),
                Arguments.of("LiZARd", lizard),
                Arguments.of("LIzarD", lizard),
                Arguments.of("Spock", spock),
                Arguments.of("spock", spock),
                Arguments.of("spOCK", spock)
        );
    }
}
