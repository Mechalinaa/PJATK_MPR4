package org.example.utils;

import org.example.models.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {

    @Test
    void isStringValid() {
        assertAll(
                () -> assertTrue(Validation.isStringValid("Jan")),
                () -> assertFalse(Validation.isStringValid("")),
                () -> assertFalse(Validation.isStringValid("   ")),
                () -> assertFalse(Validation.isStringValid(null))
        );
    }

    @Test
    void isEmailValid() {
        assertAll(
                () -> assertTrue(Validation.isEmailValid("jan@firma.pl")),
                () -> assertFalse(Validation.isEmailValid("janfirma.pl")),
                () -> assertFalse(Validation.isEmailValid(null))
        );
    }

    @Test
    void isPositionValid() {
        assertAll(
                () -> assertTrue(Validation.isPositionValid(Position.MANAGER)),
                () -> assertFalse(Validation.isPositionValid(null))
        );
    }

    @Test
    void isSalaryValid() {
        assertAll(
                () -> assertTrue(Validation.isSalaryValid(0)),
                () -> assertTrue(Validation.isSalaryValid(5000)),
                () -> assertFalse(Validation.isSalaryValid(-10))
        );
    }
}
