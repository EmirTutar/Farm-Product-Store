package validations.customExceptions;

public class InvalidTextInputException extends Exception {
    public InvalidTextInputException(String msg){
        super(msg);
    }
}