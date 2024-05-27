import static org.junit.jupiter.api.Assertions.*;

import org.app.validation.Alerts;
import org.junit.jupiter.api.Test;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.concurrent.Task;

public class VAlertsTest {

    @Test
    public void testWarning() {
        // Diese Methode zeigt einen Warnungsdialog, wir können nicht direkt testen, aber wir können sicherstellen, dass keine Exception geworfen wird
        assertDoesNotThrow(() -> Alerts.warning("Test Warning"));
    }

    @Test
    public void testSuccess() {
        // Ähnlich wie testWarning
        assertDoesNotThrow(() -> Alerts.success("Test Success"));
    }

    @Test
    public void testConfirm() {
        // Dies erfordert eine Benutzerinteraktion, daher können wir nur testen, ob die Methode aufgerufen werden kann
        assertDoesNotThrow(() -> Alerts.confirm("Confirm Test?"));
        // In einem tatsächlichen GUI-Test würden Sie das Ergebnis der Benutzerinteraktion prüfen
    }

    @Test
    public void testShowLoadingDialog() {
        Task<Void> dummyTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                return null;
            }
        };
        Alert loadingDialog = Alerts.showLoadingDialog(dummyTask, "Loading...");
        assertNotNull(loadingDialog);
    }
}
