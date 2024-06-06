import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.app.validation.NumberConversion.StringtoInteger;
import org.app.validation.NumberConversion.StringToDouble;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.api.FxRobot;
import org.testfx.util.WaitForAsyncUtils;

@ExtendWith(ApplicationExtension.class)
public class VNumberConversionTest {

    private StringtoInteger stringToIntegerConverter;
    private StringToDouble stringToDoubleConverter;

    @Start
    private void start(Stage stage) {
        // Initialisieren Sie JavaFX-Komponenten, falls erforderlich
    }

    @BeforeEach
    public void setUp() {
        stringToIntegerConverter = new StringtoInteger();
        stringToDoubleConverter = new StringToDouble();
    }

    @Test
    public void testStringToIntegerValid() {
        Integer result = stringToIntegerConverter.fromString("123");
        assertEquals(123, result);
    }

    @Test
    public void testStringToIntegerInvalid(FxRobot robot) {
        Platform.runLater(() -> {
            Integer result = stringToIntegerConverter.fromString("abc");
            assertNull(result);
        });
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("OK"); // Klicken Sie auf den OK-Button im Alert
    }

    @Test
    public void testStringToDoubleValid() {
        Double result = stringToDoubleConverter.fromString("123.45");
        assertEquals(123.45, result);
    }

    @Test
    public void testStringToDoubleInvalid(FxRobot robot) {
        Platform.runLater(() -> {
            Double result = stringToDoubleConverter.fromString("abc");
            assertNull(result);
        });
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("OK"); // Klicken Sie auf den OK-Button im Alert
    }
}
