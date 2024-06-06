import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.app.fileHandling.FileInfo;

public class FHFileInfoTest {

    @Test
    public void testGetFullPath() {
        FileInfo fileInfo = new FileInfo("DataFraApp/Database/test_file.csv");
        assertEquals("DataFraApp/Database/test_file.csv", fileInfo.getFullPath());
    }

    @Test
    public void testGetExtension() {
        FileInfo fileInfo = new FileInfo("DataFraApp/Database/test_file.csv");
        assertEquals(".csv", fileInfo.getExtension());
    }

    @Test
    public void testGetFileName() {
        FileInfo fileInfo = new FileInfo("DataFraApp/Database/test_file.csv");
        assertEquals("Database/test_file.csv", fileInfo.getFileName());
    }
}
