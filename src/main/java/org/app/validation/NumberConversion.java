package org.app.validation;

import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
 * Verwendet zur Konvertierung von Werten in Integer oder Double.
 * Diese wird verwendet, um Nummern und Preise in der TableView zu konvertieren und zu validieren.
 */
public class NumberConversion {
    public static class StringtoInteger extends IntegerStringConverter {
        @Override
        public Integer fromString(String value) {
            try {
                return super.fromString(value);
            } catch (NumberFormatException ignored) {
                Alerts.warning("Ungültiger Wert: " + value);
            }
            return null;
        }
    }

    public static class StringToDouble extends DoubleStringConverter {
        @Override
        public Double fromString(String value) {
            try {
                return super.fromString(value);
            } catch (NumberFormatException ignored) {
                Alerts.warning("Ungültiger Wert: " + value);
            }
            return null;
        }
    }
}
