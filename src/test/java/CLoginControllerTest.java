import org.app.controllers.LoginController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class CLoginControllerTest {

    private TextField txtUsername;
    private TextField txtPassword;
    private LoginController controller;

    @Start
    private void start(Stage stage) {
        txtUsername = new TextField();
        txtPassword = new TextField();
        VBox root = new VBox(txtUsername, txtPassword);
        controller = new LoginController();
        // Hier müsste der Controller entsprechend initialisiert und die TextFields ihm zugewiesen werden
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    void testLoginFunctionality() {
        txtUsername.setText("Admin");
        txtPassword.setText("1234");
        // Hier sollten Sie die Methode aufrufen, die getestet werden soll, z.B. controller.loggInn
        // Prüfen Sie dann die Zustände, die nach der Aktion erwartet werden
    }
}
