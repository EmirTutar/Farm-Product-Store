package org.app.data.dataFormats;

import org.app.data.models.Product;
import org.app.validation.Validator;
import org.app.validation.customExceptions.EmptyFieldException;
import org.app.validation.customExceptions.InvalidNumberFormat;
import org.app.validation.customExceptions.InvalidTextInputException;
import org.app.validation.ioExceptions.InvalidTypeException;

/**
 * The method parseItem() of the class takes a text file as a string after it has been scanned
 * and processes each line of the text into objects of type Components or ConfigurationItems
 * depending on the length of the line.
 */
public class ParseItems {
    public static Product parseItem(String str) throws InvalidTypeException {
        String[] inputArray = str.split("" + ItemsFormat.DELIMITER);
        if (inputArray.length != 6) {
            throw new InvalidTypeException("Error Type: The program does not support your file due to a different number " +
                    "of attributes than expected by the program!");
        }
        Product object;
        try {
            Integer id = Validator.isValidID(inputArray[0]);
            String name = Validator.validateName(inputArray[1]);
            String category = Validator.validateCategory(inputArray[2]);
            String subcategory = Validator.validateCategory(inputArray[3]);
            String specs = Validator.validateSpecs(inputArray[4]);
            if (specs.contains("|")) {
                specs = specs.replace('|', ItemsFormat.DELIMITER.charAt(0));
            }
            double price = Validator.validatePrice(inputArray[5]);

            object = new Product(name, category, subcategory, specs, price);
            object.setProductID(id);
        } catch (EmptyFieldException | InvalidTextInputException | InvalidNumberFormat e) {
            throw new InvalidTypeException("Error: The data in the CSV file is not supported by the program!\n" +
                    "Check if all data is in the correct format and order.");
        }

        return object;
    }
}
