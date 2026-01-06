import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {


    @ParameterizedTest
    @NullSource
    @DisplayName("When null passed to constructor then throw exception with valid error message")
    void whenNullPassedToConstructorThenThrowExceptionWithValidErrorMessage(List horses) {
        String expectedErrorMessage = "Horses cannot be null.";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(horses));

        assertEquals(expectedErrorMessage, exception.getMessage());

    }

    @Test
    @DisplayName("When empty list is passed to constructor then throw exception with valid error message")
    void whenEmptyListIsPassedToConstructorThenThrowExceptionWithValidErrorMessage() {
        List<Horse> horses = Collections.emptyList();
        String expectedErrorMessage = "Horses cannot be empty.";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(horses)
        );

        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("When hippodrome is created then get horses returns the list of horses")
    void whenHippodromeIsCreatedThenGetHorsesReturnsTheListOfHorses() {
        List<Horse> horses = createHorses(30);
        Hippodrome hippodrome = new Hippodrome(horses);

        assertIterableEquals(horses, hippodrome.getHorses());
    }


    @Test
    @DisplayName("When hippodrome moves then all horses should move")
    void whenHippodromeMovesThenAllHorsesShouldMove() {
        List<Horse> horses = createMockedHorses(50);

        Hippodrome hippodrome = new Hippodrome(horses);

        hippodrome.move();

        for (Horse horse : horses) {
            Mockito.verify(horse).move();
        }
    }

    @Test
    @DisplayName("When winner is requested then the horse with the highest distance should be returned")
    void whenWinnerIsRequestedThenTheHorseWithTheHighestDistanceShouldBeReturned() {
        int numberOfHorses = 50;
        List<Horse> horses = createHorses(numberOfHorses);
        Hippodrome hippodrome = new Hippodrome(horses);

        Horse winner = hippodrome.getWinner();

        assertEquals(horses.get(numberOfHorses - 1), winner);

    }


    private List<Horse> createHorses(int count) {
        List<Horse> horses = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Horse horse = new Horse(
                    "Horse " + i,
                    10.0,
                    i * 10.0);
            horses.add(horse);
        }
        return horses;
    }


    private List<Horse> createMockedHorses(int count) {
        List<Horse> horses = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Horse horse = Mockito.mock(Horse.class);
            horses.add(horse);
        }
        return horses;
    }

}