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
    @Test
    void validateSalaryConsistency_shouldReturnEmployeesWithTooLowSalary() {
        service.addEmployee(e1); // salary = default for PROGRAMISTA
        e1.setSalary(1000);      // zbyt niskie

        service.addEmployee(e2); // ma poprawną pensję

        var list = service.validateSalaryConsistency();

        assertEquals(1, list.size());
        assertTrue(list.contains(e1));
    }
    @Test
    void getCompanyStatistics_shouldReturnCorrectStats() {
        service.addEmployee(e1);  // FirmaA, salary PROGRAMISTA
        service.addEmployee(e2);  // FirmaA, salary MANAGER
        service.addEmployee(e3);  // FirmaB

        var stats = service.getCompanyStatistics();

        assertEquals(2, stats.get("FirmaA").employeeNumber);
        assertEquals((e1.getSalary() + e2.getSalary()) / 2, stats.get("FirmaA").avgSalary);
        assertEquals(e2, stats.get("FirmaA").personwithBiggestSalary);
    }

    @Test
    void giveRaise_shouldPromoteEmployeeWhenNewPositionHigher() {
        service.addEmployee(e3);  // STAŻYSTA

        service.giveRaise(e3, Position.PROGRAMISTA);

        assertEquals(Position.PROGRAMISTA, e3.getPosition());
        assertEquals(Position.PROGRAMISTA.getSalary(), e3.getSalary());
    }

    @Test
    void giveRaise_shouldNotDowngradeOrEqualPosition() {
        service.addEmployee(e1); // PROGRAMISTA

        service.giveRaise(e1, Position.STAZYSTA); // niższe stanowisko

        assertEquals(Position.PROGRAMISTA, e1.getPosition());
    }
    @Test
    void givePercentageRaise_shouldIncreaseSalaryWithinLimit() {
        service.addEmployee(e1); // PROGRAMISTA

        double old = e1.getSalary();
        service.givePercentageRaise(e1, 10); // +10%

        assertEquals(old * 1.10, e1.getSalary(), 0.0001);
    }

    @Test
    void givePercentageRaise_shouldNotExceedMaxSalary() {
        service.addEmployee(e1);

        // ustawiamy salary prawie max
        e1.setSalary(Position.PROGRAMISTA.getMaxSalary());

        service.givePercentageRaise(e1, 10); // powinno odmówić i nic nie zmienić

        assertEquals(Position.PROGRAMISTA.getMaxSalary(), e1.getSalary());
    }

    @Test
    void calculateAverageGrade_shouldReturnAverage() {
        e1.addGrade(5);
        e1.addGrade(3);
        e1.addGrade(4);

        double average = service.calculateAverageGrade(e1);

        assertEquals((5 + 3 + 4) / 3.0, average);
    }

    @Test
    void calculateAverageGrade_emptyList_shouldReturnZero() {
        double average = service.calculateAverageGrade(e1);

        assertEquals(0, average);
    }

    @Test
    void findBestEmployees_shouldExecuteWithoutCrashing() {
        e1.addGrade(5);
        e1.addGrade(4);
        e1.addGrade(5);

        service.addEmployee(e1);
        service.addEmployee(e2);

        service.findBestEmployees();
    }
    @Test
    void calculateInternship_shouldReturnNonNegativePeriod() {
        service.addEmployee(e1);

        var period = service.calculateInternship(e1);

        assertTrue(period.getYears() >= 0);
    }

    @Test
    void findEmployeesWithLongestInternships_shouldRunCorrectly() throws NoSuchFieldException, IllegalAccessException {
        // tworzymy pracownika ze sztucznie wczesną datą zatrudnienia
        Employee old = new Employee("Old", "Man", "old@f.pl", "X", Position.MANAGER);
        old.setSalary(8000);
        old.getListOfRevs().clear();
        old.setProjectGroup(null);

        // manipulujemy datą: 5 lat temu
        var field = Employee.class.getDeclaredField("hireDate");
        field.setAccessible(true);
        field.set(old, old.getHireDate().minusYears(5));

        service.addEmployee(old);
        service.addEmployee(e1);

        service.findEmployeesWithLongestInternships(); // pokrycie linii
    }

}
