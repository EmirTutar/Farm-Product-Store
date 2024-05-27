import static org.junit.jupiter.api.Assertions.*;
import org.app.User;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void testUserCreation() {
        User user = new User("Admin", "1234");
        assertEquals("Admin", user.getUserName());
        assertEquals("1234", user.getPassword());
    }
}
