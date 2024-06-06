import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

import javafx.stage.Stage;
import org.app.Load;
import org.junit.jupiter.api.Test;

public class HLoadTest {

    @Test
    void testExitBehavior() {
        Stage mockStage = mock(Stage.class);
        assertDoesNotThrow(() -> Load.exit(mockStage));
    }
}
