import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

import javafx.stage.Stage;
import org.app.Load;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoadTest {

    @BeforeEach
    void setup() {
        // Setup resources if any
    }

    @Test
    void testWindowLoadsFXMLCorrectly() {
        Stage mockStage = mock(Stage.class);
        assertDoesNotThrow(() -> Load.window("loginView.fxml", "Login", mockStage));
    }

    @Test
    void testExitBehavior() {
        Stage mockStage = mock(Stage.class);
        assertDoesNotThrow(() -> Load.exit(mockStage));
    }
}
