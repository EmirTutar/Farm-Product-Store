import org.app.controllers.CategoryRegisterController;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.testfx.framework.junit5.ApplicationTest;

public class CCategoryRegisterControllerTest extends ApplicationTest {

    private CategoryRegisterController controller = new CategoryRegisterController();

    @Test
    public void testControllerInitialization() {
        assertNotNull(controller);
    }

    // Weitere Tests, abhängig von Methoden und Logik im Controller
}
