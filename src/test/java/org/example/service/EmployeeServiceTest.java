package org.example.service;

import org.example.models.Employee;
import org.example.models.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceTest {

    private EmployeeService service;
    private Employee e1, e2, e3, e4;

    @BeforeEach
    void setUp() {
        service = new EmployeeService();
        e1 = new Employee("Jan", "Kowalski", "jan@firma.pl", "FirmaA", Position.PROGRAMISTA);
        e2 = new Employee("Anna", "Nowak", "anna@firma.pl", "FirmaA", Position.MANAGER);
        e3 = new Employee("Piotr", "Zielinski", "piotr@firma.pl", "FirmaB", Position.STAZYSTA);
        e4 = new Employee("Ela", "Kowalska", "ela@firma.pl", "FirmaB", Position.PROGRAMISTA);
    }

    @Test
    void addEmployee_duplicateEmail_shouldThrowException() {
        // Arrange
        service.addEmployee(e1);
        Employee duplicate = new Employee("Janek", "Kowal", "jan@firma.pl", "FirmaC", Position.STAZYSTA);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> service.addEmployee(duplicate),
                "Powinien rzucić wyjątek przy dodaniu pracownika z tym samym emailem");
    }

    @Test
    void searchByCompanyName_shouldReturnCorrectEmployees() {
        // Arrange
        service.addEmployee(e1);
        service.addEmployee(e2);

        // Act
        ArrayList<Employee> results = service.searchByCompanyName("FirmaA");

        // Assert
        assertEquals(2, results.size());
        assertTrue(results.contains(e1));
        assertTrue(results.contains(e2));
    }

    @Test
    void searchByCompanyName_returnsEmptyList() {
        service.addEmployee(e1);
        ArrayList<Employee> list = service.searchByCompanyName("BrakFirmy");

        assertTrue(list.isEmpty());
    }

    @Test
    void sortEmployeesBySurname_shouldReturnSortedList() {
        // Arrange
        service.addEmployee(e1);
        service.addEmployee(e2);
        service.addEmployee(e3);
        service.addEmployee(e4);

        // Act
        ArrayList<Employee> sorted = service.sortEmployeesBySurname();

        // Assert
        assertEquals("Kowalska", sorted.get(0).getSurname());
        assertEquals("Kowalski", sorted.get(1).getSurname());
        assertEquals("Nowak", sorted.get(2).getSurname());
        assertEquals("Zielinski", sorted.get(3).getSurname());
    }

    @Test
    void groupByPosition_shouldGroupCorrectly() {
        // Arrange
        service.addEmployee(e1);
        service.addEmployee(e2);
        service.addEmployee(e3);
        service.addEmployee(e4);

        // Act
        HashMap<Position, ArrayList<Employee>> grouped = service.groupByPosition();

        // Assert
        assertEquals(5, grouped.size()); // wszystkie stanowiska muszą być obecne
        assertEquals(2, grouped.get(Position.PROGRAMISTA).size());
        assertEquals(1, grouped.get(Position.MANAGER).size());
        assertEquals(1, grouped.get(Position.STAZYSTA).size());
        assertTrue(grouped.get(Position.PROGRAMISTA).contains(e1));
        assertTrue(grouped.get(Position.PROGRAMISTA).contains(e4));

    }

    @Test
    void countEmployees_shouldReturnCorrectCounts() {
        // Arrange
        service.addEmployee(e1);
        service.addEmployee(e2);
        service.addEmployee(e3);
        service.addEmployee(e4);

        // Act
        HashMap<Position, Integer> counts = service.countEmployees();

        // Assert
        assertEquals(2, counts.get(Position.PROGRAMISTA));
        assertEquals(1, counts.get(Position.MANAGER));
        assertEquals(1, counts.get(Position.STAZYSTA));
    }

    @Test
    void salaryStatistic_shouldReturnAverage() {
        // Arrange
        service.addEmployee(e1);
        service.addEmployee(e2);
        service.addEmployee(e3);
        double expected = (e1.getSalary() + e2.getSalary() + e3.getSalary()) / 3.0;

        // Act
        double actual = service.salaryStatistic();

        // Assert
        assertEquals(expected, actual, 0.0001);
    }

    @Test
    void salaryStatistic_oneEmployee_shouldReturnSalaryOfThatEmployee() {
        // Arrange
        service.addEmployee(e1);

        // Act
        double result = service.salaryStatistic();

        // Assert
        assertEquals(e1.getSalary(), result, 0.0001, "Średnia pensja dla jednej osoby powinna być równa jej pensji");
    }

    @Test
    void salaryStatistic_emptyList_shouldReturnZero() {
        // Act
        double result = service.salaryStatistic();

        // Assert
        assertEquals(0, result, 0.0001, "Dla pustej listy średnia pensja powinna być 0");
    }


    @Test
    void bestPaid_shouldReturnEmployeeWithHighestSalary() {
        // Arrange
        service.addEmployee(e1);
        service.addEmployee(e2);
        service.addEmployee(e3);

        // Act
        Employee result = service.bestPaid();

        // Assert
        assertEquals(e2, result, "Manager powinien być najlepiej opłacony");
    }

    @Test
    void bestPaid_emptyList_shouldReturnNull() {
        // Act
        Employee result = service.bestPaid();

        // Assert
        assertNull(result, "Dla pustej listy powinien zwrócić null");
    }
}
