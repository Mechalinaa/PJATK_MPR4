package org.example.utils;

import org.example.models.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidationTest {

    @Test
    void isStringValid_shouldValidateStringsProperly() {
        // Arrange
        String valid = "Jan";
        String empty = "";
        String spaces = "   ";

        // Act & Assert
        assertTrue(Validation.isStringValid(valid));
        assertFalse(Validation.isStringValid(empty));
        assertFalse(Validation.isStringValid(spaces));
        assertFalse(Validation.isStringValid(null));
    }

    @Test
    void isEmailValid_shouldValidateEmailFormat() {
        // Arrange
        String valid = "jan@firma.pl";
        String invalid = "janfirma.pl";

        // Act & Assert
        assertTrue(Validation.isEmailValid(valid));
        assertFalse(Validation.isEmailValid(invalid));
        assertFalse(Validation.isEmailValid(null));
    }

    @Test
    void isPositionValid_shouldReturnFalseForNull() {
        // Arrange
        Position p = Position.MANAGER;

        // Act & Assert
        assertTrue(Validation.isPositionValid(p));
        assertFalse(Validation.isPositionValid(null));
    }

    @Test
    void isSalaryValid_shouldCheckForNonNegative() {
        // Arrange, Act & Assert
        assertTrue(Validation.isSalaryValid(0));
        assertTrue(Validation.isSalaryValid(5000));
        assertFalse(Validation.isSalaryValid(-10));
    }
}
