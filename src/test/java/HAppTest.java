import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.app.App;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.matcher.control.LabeledMatchers;

@ExtendWith(ApplicationExtension.class)
public class HAppTest {

    @Start
    private void start(Stage stage) throws Exception {
        new App().start(stage);
    }

    @Test
    void testLoginUI(FxRobot robot) throws InterruptedException {
        sleep(1000); // Just to see the window
        // Assuming the login button has an id of 'loginButton'
        TextField usernameField = robot.lookup("#txtUsername").queryAs(TextField.class);
        TextField passwordField = robot.lookup("#txtPassword").queryAs(TextField.class);
        Button loginButton = robot.lookup("#loginButton").queryAs(Button.class);

        // Ensure the fields and button are present
        Assertions.assertThat(usernameField).isNotNull();
        Assertions.assertThat(passwordField).isNotNull();
        Assertions.assertThat(loginButton).isNotNull();

        // Simulate user input
        robot.clickOn(usernameField).write("Admin");
        robot.clickOn(passwordField).write("1234");

        // Click login button
        robot.clickOn(loginButton);

        // Verify new scene or some other indicator of successful login
        // e.g., checking if a new element in the next scene is visible
        // Here you should add your verification logic
        assertTrue(true); // This is just a placeholder
    }
}
