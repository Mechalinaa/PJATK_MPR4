package org.example.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    private double prezesSalary;
    private double wiceprezesSalary;
    private double managerSalary;
    private double programistaSalary;
    private double stazystaSalary;

    @BeforeEach
    void setUp() {
        prezesSalary = Position.PREZES.getSalary();
        wiceprezesSalary = Position.WICEPREZES.getSalary();
        managerSalary = Position.MANAGER.getSalary();
        programistaSalary = Position.PROGRAMISTA.getSalary();
        stazystaSalary = Position.STAZYSTA.getSalary();
    }

    @Test
    void enumSalaries_shouldBePositive() {
        // Act & Assert
        assertTrue(prezesSalary > 0, "Pensja prezesa powinna być dodatnia");
        assertTrue(wiceprezesSalary > 0, "Pensja wiceprezesa powinna być dodatnia");
        assertTrue(managerSalary > 0, "Pensja managera powinna być dodatnia");
        assertTrue(programistaSalary > 0, "Pensja programisty powinna być dodatnia");
        assertTrue(stazystaSalary > 0, "Pensja stażysty powinna być dodatnia");
    }

    @Test
    void enumSalaries_shouldBeOrderedFromHighestToLowest() {
        // Act & Assert
        assertTrue(prezesSalary > wiceprezesSalary, "Prezes powinien zarabiać więcej niż wiceprezes");
        assertTrue(wiceprezesSalary > managerSalary, "Wiceprezes powinien zarabiać więcej niż manager");
        assertTrue(managerSalary > programistaSalary, "Manager powinien zarabiać więcej niż programista");
        assertTrue(programistaSalary > stazystaSalary, "Programista powinien zarabiać więcej niż stażysta");
    }

    @Test
    void allPositions_shouldExistAndBeAccessible() {
        // Act
        Position[] allPositions = Position.values();

        // Assert
        assertEquals(5, allPositions.length, "Enum Position powinien mieć 5 wartości");
        assertNotNull(Position.valueOf("PREZES"), "Enum powinien zawierać PREZES");
        assertNotNull(Position.valueOf("PROGRAMISTA"), "Enum powinien zawierać PROGRAMISTA");
    }
}
