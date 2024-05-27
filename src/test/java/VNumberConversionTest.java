import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.app.validation.Alerts;
import org.app.validation.NumberConversion;

public class VNumberConversionTest {

    @Test
    public void testStringtoIntegerConversion() {
        NumberConversion.StringtoInteger converter = new NumberConversion.StringtoInteger();
        assertEquals(Integer.valueOf(123), converter.fromString("123"));
        assertNull(converter.fromString("abc")); // Erwartetes Ergebnis, da 'abc' keine gültige Zahl ist
    }

    @Test
    public void testStringToDoubleConversion() {
        NumberConversion.StringToDouble converter = new NumberConversion.StringToDouble();
        assertEquals(Double.valueOf(123.45), converter.fromString("123.45"));
        assertNull(converter.fromString("abc.d")); // Erwartetes Ergebnis, da 'abc.d' keine gültige Zahl ist
    }
}
