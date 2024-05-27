import static org.junit.jupiter.api.Assertions.*;
import org.app.PathDialogBox;
import org.app.validation.ioExceptions.InvalidExtensionException;
import org.junit.jupiter.api.Test;

public class PathDialogBoxTest {

    @Test
    public void testExtensionCheckThrowsExceptionForNoExtension() {
        PathDialogBox dialogBox = new PathDialogBox();
        assertThrows(InvalidExtensionException.class, () -> dialogBox.extensionCheck("file"));
    }

    @Test
    public void testGetPathToSaveNotNull() {
        PathDialogBox dialogBox = new PathDialogBox();
        assertNotNull(dialogBox.getPathToSave());
    }
}
