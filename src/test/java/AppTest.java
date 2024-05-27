import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javafx.stage.Stage;
import org.app.App;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class AppTest {

    private App appUnderTest;

    @Start
    private void start(Stage stage) {
        appUnderTest = new App();
        try {
            appUnderTest.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAppInitializesWithoutException() {
        assertDoesNotThrow(() -> new App().start(new Stage()));
    }
}
