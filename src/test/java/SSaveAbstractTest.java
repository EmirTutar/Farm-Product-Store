import static org.junit.jupiter.api.Assertions.*;

import org.app.fileHandling.save.SaveAbstract;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class SSaveAbstractTest {

    private static class SaveAbstractDummy<T> extends SaveAbstract<T> {
        @Override
        public void write(String path, ArrayList<T> list) {
            // Dummy-Implementierung macht nichts.
        }
    }

    @Test
    public void testWrite() {
        SaveAbstractDummy<Object> dummy = new SaveAbstractDummy<>();
        assertDoesNotThrow(() -> dummy.write("dummy_path", new ArrayList<>()));
    }
}
