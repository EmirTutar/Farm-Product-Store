import org.app.validation.ioExceptions.FileDontExistsException;
import org.app.validation.ioExceptions.InvalidExtensionException;
import org.app.validation.ioExceptions.InvalidTypeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VIOExceptionsTest {
        @Test
    public void testFileDontExistsException() {
        Exception exception = assertThrows(FileDontExistsException.class, () -> {
            throw new FileDontExistsException("File does not exist");
        });
        assertEquals("File does not exist", exception.getMessage());
    }

    @Test
    public void testInvalidExtensionException() {
        Exception exception = assertThrows(InvalidExtensionException.class, () -> {
            throw new InvalidExtensionException("Invalid file extension");
        });
        assertEquals("Invalid file extension", exception.getMessage());
    }

    @Test
    public void testInvalidTypeException() {
        Exception exception = assertThrows(InvalidTypeException.class, () -> {
            throw new InvalidTypeException("Invalid type provided");
        });
        assertEquals("Invalid type provided", exception.getMessage());
    }
}

