package org.example.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    private double prezes;
    private double wice;
    private double manager;
    private double programista;
    private double stazysta;

    @BeforeEach
    void setUp() {
        prezes = Position.PREZES.getSalary();
        wice = Position.WICEPREZES.getSalary();
        manager = Position.MANAGER.getSalary();
        programista = Position.PROGRAMISTA.getSalary();
        stazysta = Position.STAZYSTA.getSalary();
    }

    @Test
    void enumSalaries_shouldBePositive() {
        assertAll(
                () -> assertTrue(prezes > 0),
                () -> assertTrue(wice > 0),
                () -> assertTrue(manager > 0),
                () -> assertTrue(programista > 0),
                () -> assertTrue(stazysta > 0)
        );
    }

    @Test
    void enumSalaries_shouldBeOrdered() {
        assertAll(
                () -> assertTrue(prezes > wice),
                () -> assertTrue(wice > manager),
                () -> assertTrue(manager > programista),
                () -> assertTrue(programista > stazysta)
        );
    }

    @Test
    void allPositions_shouldExist() {
        Position[] values = Position.values();
        assertAll(
                () -> assertEquals(5, values.length),
                () -> assertNotNull(Position.valueOf("PREZES")),
                () -> assertNotNull(Position.valueOf("PROGRAMISTA"))
        );
    }
}
