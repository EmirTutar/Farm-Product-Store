import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.app.validation.customExceptions.*;

public class VCustomExceptionsTest {

    @Test
    public void testEmptyFieldException() {
        Exception exception = assertThrows(EmptyFieldException.class, () -> {
            throw new EmptyFieldException("Field cannot be empty");
        });
        assertEquals("Field cannot be empty", exception.getMessage());
    }

    @Test
    public void testInvalidNumberFormat() {
        Exception exception = assertThrows(InvalidNumberFormat.class, () -> {
            throw new InvalidNumberFormat("Invalid number format");
        });
        assertEquals("Invalid number format", exception.getMessage());
    }

    @Test
    public void testInvalidTextInputException() {
        Exception exception = assertThrows(InvalidTextInputException.class, () -> {
            throw new InvalidTextInputException("Invalid text input");
        });
        assertEquals("Invalid text input", exception.getMessage());
    }

}