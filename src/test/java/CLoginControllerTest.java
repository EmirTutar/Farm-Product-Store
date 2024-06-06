import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.app.controllers.LoginController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

@ExtendWith(ApplicationExtension.class)
public class CLoginControllerTest {

    private LoginController controller;

    @Start
    private void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/app/loginView.fxml")); // Pfad zur FXML-Datei anpassen
        Parent root = loader.load();
        controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    void testLoginWithValidCredentials(FxRobot robot) throws InterruptedException {
        robot.clickOn("#txtUsername").write("Admin");
        robot.clickOn("#txtPassword").write("1234");
        robot.clickOn("Log in");

        WaitForAsyncUtils.waitForFxEvents();

        // Annahme: Ein erfolgreicher Login führt zur Anzeige der AdminView
        verifyThat("#adminPane", isVisible());
        sleep(5000);
    }

    @Test
    void testLoginWithInvalidUsername(FxRobot robot) throws InterruptedException {
        robot.clickOn("#txtUsername").write("InvalidUser");
        robot.clickOn("#txtPassword").write("1234");
        robot.clickOn("Log in");

        WaitForAsyncUtils.waitForFxEvents();

        // Annahme: Ein fehlgeschlagener Login zeigt eine Warnung an
        verifyThat("Invalid username or password!\nUsername: Admin\nPassword: 1234", isVisible());
        sleep(5000);
        robot.clickOn("OK");
    }

    @Test
    void testLoginWithInvalidPassword(FxRobot robot) throws InterruptedException {
        robot.clickOn("#txtUsername").write("Admin");
        robot.clickOn("#txtPassword").write("wrongpassword");
        robot.clickOn("Log in");

        WaitForAsyncUtils.waitForFxEvents();

        // Annahme: Ein fehlgeschlagener Login zeigt eine Warnung an
        verifyThat("Invalid username or password!\nUsername: Admin\nPassword: 1234", isVisible());
        sleep(5000);
        robot.clickOn("OK");
    }

    @Test
    void testLoginWithEmptyFields(FxRobot robot) throws InterruptedException {
        robot.clickOn("Log in");

        WaitForAsyncUtils.waitForFxEvents();

        // Annahme: Ein fehlgeschlagener Login zeigt eine Warnung an
        verifyThat("Invalid username or password!\nUsername: Admin\nPassword: 1234", isVisible());
        sleep(5000);
        robot.clickOn("OK");
    }
}
