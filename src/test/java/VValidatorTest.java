import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.app.validation.Validator;
import org.app.validation.customExceptions.*;

public class VValidatorTest {

    @Test
    public void testIsValidID() {
        assertDoesNotThrow(() -> Validator.isValidID("123"));
        assertThrows(InvalidNumberFormat.class, () -> Validator.isValidID("abc"));
        assertThrows(InvalidTextInputException.class, () -> Validator.isValidID("-1"));
    }

    @Test
    public void testValidatePrice() {
        assertDoesNotThrow(() -> Validator.validatePrice("123.45"));
        assertThrows(InvalidNumberFormat.class, () -> Validator.validatePrice("abc"));
        assertThrows(EmptyFieldException.class, () -> Validator.validatePrice(""));
        assertThrows(InvalidTextInputException.class, () -> Validator.validatePrice("-10"));
    }

    @Test
    public void testValidateName() {
        assertDoesNotThrow(() -> Validator.validateName("Name"));
        assertThrows(EmptyFieldException.class, () -> Validator.validateName(""));
        assertThrows(InvalidTextInputException.class, () -> Validator.validateName("2Name"));
    }

    @Test
    public void testValidateCategory() {
        assertDoesNotThrow(() -> Validator.validateCategory("Category"));
        assertThrows(EmptyFieldException.class, () -> Validator.validateCategory(""));
        assertThrows(InvalidTextInputException.class, () -> Validator.validateCategory("123"));
    }

    @Test
    public void testValidateSpecs() {
        assertDoesNotThrow(() -> Validator.validateSpecs("Specs"));
        assertThrows(EmptyFieldException.class, () -> Validator.validateSpecs(""));
    }
}
