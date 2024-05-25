package org.app.data.dataFormats;

import org.app.data.models.Product;
import org.app.validation.Alerts;
import org.app.validation.customExceptions.EmptyFieldException;
import org.app.validation.ioExceptions.InvalidTypeException;

import java.util.ArrayList;

/**
 * All data written to a CSV file
 * goes through this class to get its text format.
 * Two of the methods are generic because we print out two types to a CSV file:
 * Components and ConfigurationItems.
 */

public class ItemsFormat {
    public static final String DELIMITER = ",";

    public static <T> String objectFormat(T object) throws InvalidTypeException {
        if (object instanceof Product) {
            if (((Product) object).getSpecification().contains(DELIMITER)) {
                ((Product) object).setSpecification(((Product) object)
                        .getSpecification().replace(',', '|'));
            }

            if (((Product) object).getSubCategory().equals("Select again")) {
                throw new InvalidTypeException("Error: The product with id: " + ((Product) object).getProductID()
                        + " is missing a sub-category! Try to correct it in the table.");
            }

            return ((Product) object).csvFormat(DELIMITER);
        }
        throw new InvalidTypeException("The program does not support this object type!");

    }

    public static <T> String itemsText(ArrayList<T> items) throws InvalidTypeException {
        StringBuilder itemText = new StringBuilder();
        for (T item : items) {
            itemText.append(objectFormat(item));
            itemText.append("\n");
        }
        return itemText.toString();
    }
}
