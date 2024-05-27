import org.app.controllers.AdminController;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.testfx.framework.junit5.ApplicationTest;

public class CAdminControllerTest extends ApplicationTest {

    private AdminController controller = new AdminController();

    @Test
    public void testControllerInitialization() {
        assertNotNull(controller);
    }

    // Weitere Tests, abhängig von Methoden und Logik im Controller
}
