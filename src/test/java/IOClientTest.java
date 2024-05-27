import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.app.fileHandling.FileInfo;
import org.app.fileHandling.IOClient;
import org.app.fileHandling.fileThreads.OpenThread;
import org.app.fileHandling.fileThreads.SaveThread;
import java.util.ArrayList;

public class IOClientTest {

    private IOClient<Object> ioClient;
    private FileInfo fileInfo;

    @BeforeEach
    public void setUp() {
        fileInfo = new FileInfo("DataFraApp/Database/test_file.bin");
        ioClient = new IOClient<>(fileInfo);
    }

    @Test
    public void testRunSaveThread() {
        ArrayList<Object> listToWrite = new ArrayList<>();
        listToWrite.add("Test Data");
        ioClient = new IOClient<>(fileInfo, listToWrite);
        assertDoesNotThrow(() -> ioClient.runSaveThread("Saving test data"));
    }

    @Test
    public void testRunOpenThread() {
        assertDoesNotThrow(() -> ioClient.runOpenThread("Loading test data"));
    }
}
