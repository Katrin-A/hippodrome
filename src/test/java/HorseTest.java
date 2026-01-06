import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Nested;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class HorseTest {

    private static final String VALID_NAME = "Spirit";
    private static final double VALID_SPEED = 10.0;
    private static final double VALID_DISTANCE = 5.0;
    private static final double DEFAULT_DISTANCE = 0.0;

    @ParameterizedTest
    @NullSource
    @DisplayName("When name is null then should throw exception with correct message")
    void whenNameIsNullThenShouldThrowException(String input) {
        String expectedErrorMessage = "Name cannot be null.";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(input, VALID_SPEED));

        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {" ", "   ", "\t"})
    @DisplayName("When name is empty then should throw exception with correct message")
    void whenNameIsEmptyThenShouldThrowExceptionWithCorrectMessage(String input) {
        String expectedErrorMessage = "Name cannot be blank.";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(input, VALID_SPEED));

        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("When speed is negative value then should throw exception with correct message")
    void whenSpeedIsNegativeValueThenShouldThrowExceptionWithCorrectMessage() {
        String expectedErrorMessage = "Speed cannot be negative.";
        double negativeSpeed = -0.1;

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(VALID_NAME, negativeSpeed)
        );

        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("When distance is negative value then should throw exception with correct message")
    void whenDistanceIsNegativeValueThenShouldThrowExceptionWithCorrectMessage() {
        String expectedErrorMessage = "Distance cannot be negative.";
        double negativeDistance = -0.1;

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(VALID_NAME, VALID_SPEED, negativeDistance)
        );

        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Nested
    class WhenHorseIsCreated{

        Horse horse;

        @BeforeEach
        void setUp(){

            horse = new Horse(VALID_NAME, VALID_SPEED, VALID_DISTANCE);
        }
        
        @Test
        @DisplayName("When horse is created then get name returns constructor name")
        void whenHorseIsCreatedThenGetNameReturnsConstructorName() {
            assertEquals(VALID_NAME, horse.getName());
        }

        @Test
        @DisplayName("When horse is created then get speed returns constructor speed")
        void whenHorseIsCreatedThenGetSpeedReturnsConstructorSpeed() {
            assertEquals(VALID_SPEED, horse.getSpeed());
        }

        @Test
        @DisplayName("When distance is not provided then should return zero distance")
        void whenDistanceIsNotProvidedThenShouldReturnZeroDistance() {
            Horse testHorse = new Horse(VALID_NAME, VALID_SPEED);
            assertEquals(DEFAULT_DISTANCE, testHorse.getDistance());
        }

        @Test
        @DisplayName("When horse is created then get distance returns constructor distance")
        void whenHorseIsCreatedThenGetDistanceReturnsConstructorDistance() {
            assertEquals(VALID_DISTANCE, horse.getDistance());
        }

        @ParameterizedTest
        @ValueSource(doubles = {0.2, 0.5, 0.9} )
        @DisplayName("When horse moves then distance is calculated correctly")
        void whenHorseMovesThenDistanceIsCalculatedCorrectly(double input) {
            double initialDistance = horse.getDistance();
            try( MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)){

                horseMockedStatic.when(() -> Horse.getRandomDouble(0.2,0.9)).thenReturn(input);
                double expectedDistance = initialDistance + VALID_SPEED * input;
                horse.move();

                assertEquals(expectedDistance, horse.getDistance());

               horseMockedStatic.verify(
                       () -> Horse.getRandomDouble(0.2,0.9),
                       Mockito.times(1));
            }
        }

    }




}