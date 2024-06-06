import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.app.validation.Alerts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.api.FxRobot;
import org.testfx.util.WaitForAsyncUtils;

@ExtendWith(ApplicationExtension.class)
public class VAlertsTest {

    @Start
    private void start(Stage stage) {
        // Set up a stage if needed
    }

    @BeforeEach
    public void setUp() {
        System.setProperty("test.env", "true"); // Set the test environment property
    }

    @Test
    public void testWarningAlert(FxRobot robot) {
        Platform.runLater(() -> Alerts.warning("This is a warning message"));
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat(".alert", isVisible());
        robot.clickOn("OK");
    }

    @Test
    public void testSuccessAlert(FxRobot robot) {
        Platform.runLater(() -> Alerts.success("This is a success message"));
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat(".alert", isVisible());
        robot.clickOn("OK");
    }

    @Test
    public void testConfirmAlert(FxRobot robot) {
        Platform.runLater(() -> {
            boolean result = Alerts.confirm("Do you confirm this?");
            assertTrue(result); // Assuming confirm always returns true in the test environment
        });
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat(".alert", isVisible());
        robot.clickOn("Yes");
    }

}
