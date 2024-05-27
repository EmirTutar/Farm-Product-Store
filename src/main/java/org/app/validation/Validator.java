package org.app.validation;

import org.app.validation.customExceptions.EmptyFieldException;
import org.app.validation.customExceptions.InvalidNumberFormat;
import org.app.validation.customExceptions.InvalidTextInputException;

import java.util.regex.Pattern;

public class Validator {

    public static Integer isValidID(String ID) throws InvalidNumberFormat {
        int id;
        try {
            id = Integer.parseInt(ID);
        } catch (NumberFormatException e) {
            throw new InvalidNumberFormat("Ungültige ID: " + ID);
        }
        if (id <= 0) {
            throw new InvalidTextInputException("Fehler: ID muss größer als Null sein");
        }

        return id;
    }

    public static Double validatePrice(String priceStr) throws InvalidTextInputException,
            EmptyFieldException, InvalidNumberFormat {
        if (priceStr == null || priceStr.isEmpty() || priceStr.isBlank()) {
            throw new EmptyFieldException("Fehler: Preis darf nicht leer sein!");
        }
        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            throw new InvalidNumberFormat("Ungültiger Preis: " + priceStr);
        }

        if (price <= 0) {
            throw new InvalidTextInputException("Fehler: Preis muss größer als Null sein!");
        }
        return price;
    }

    public static String validateName(String name) throws InvalidTextInputException, EmptyFieldException {
        if (name == null || name.isEmpty() || name.isBlank()) {
            throw new EmptyFieldException("Fehler: Kein Name angegeben!");
        }

        if (Character.isDigit(name.charAt(0))) {
            throw new InvalidTextInputException("Fehler: Produktname muss mit einem Großbuchstaben beginnen und darf mit zwei Ziffern enden. Der Name muss aus einem Wort ohne Leerzeichen bestehen!");
        }
        return name;
    }

    public static String validateCategory(String category) throws InvalidTextInputException,
            EmptyFieldException {
        if (category == null || category.isEmpty() || category.isBlank()) {
            throw new EmptyFieldException("Fehler: Bitte beide Kategorien auswählen!");
        }

        boolean newCategoryOK = Pattern.matches("^[A-ZÅØÆ]*[\\- a-zåøæ]*", category);
        if (!newCategoryOK) {
            throw new InvalidTextInputException("Fehler: Kategorie muss mit einem Großbuchstaben beginnen und darf Kleinbuchstaben enthalten, Zahlen sind ungültig!");
        }
        return category;
    }

    public static String validateSpecs(String specs) throws EmptyFieldException {
        if (specs == null || specs.isEmpty() || specs.isBlank()) {
            throw new EmptyFieldException("Fehler: Spezifikationstext darf nicht leer sein!");
        }
        return specs;
    }
}
