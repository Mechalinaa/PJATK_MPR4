package org.example.service;

import org.example.models.Employee;
import org.example.models.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

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
    void addEmployee_duplicateEmail() {
        service.addEmployee(e1);
        Employee duplicate = new Employee("Janek", "Kowal", "jan@firma.pl", "FirmaC", Position.STAZYSTA);

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> service.addEmployee(duplicate))
        );
    }

    @Test
    void searchByCompanyName_correctResults() {
        service.addEmployee(e1);
        service.addEmployee(e2);
        ArrayList<Employee> results = service.searchByCompanyName("FirmaA");

        assertAll(
                () -> assertEquals(2, results.size()),
                () -> assertTrue(results.contains(e1)),
                () -> assertTrue(results.contains(e2))
        );
    }

    @Test
    void searchByCompanyName_emptyList() {
        service.addEmployee(e1);
        ArrayList<Employee> results = service.searchByCompanyName("BrakFirmy");

        assertAll(
                () -> assertTrue(results.isEmpty())
        );
    }

    @Test
    void sortEmployeesBySurname() {
        service.addEmployee(e1);
        service.addEmployee(e2);
        service.addEmployee(e3);
        service.addEmployee(e4);
        var sorted = service.sortEmployeesBySurname();

        assertAll(
                () -> assertEquals("Kowalska", sorted.get(0).getSurname()),
                () -> assertEquals("Kowalski", sorted.get(1).getSurname()),
                () -> assertEquals("Nowak", sorted.get(2).getSurname()),
                () -> assertEquals("Zielinski", sorted.get(3).getSurname())
        );
    }

    @Test
    void groupByPosition() {
        service.addEmployee(e1);
        service.addEmployee(e2);
        service.addEmployee(e3);
        service.addEmployee(e4);
        HashMap<Position, ArrayList<Employee>> grouped = service.groupByPosition();

        assertAll(
                () -> assertEquals(5, grouped.size()),
                () -> assertEquals(2, grouped.get(Position.PROGRAMISTA).size()),
                () -> assertEquals(1, grouped.get(Position.MANAGER).size()),
                () -> assertEquals(1, grouped.get(Position.STAZYSTA).size())
        );
    }

    @Test
    void countEmployees() {
        service.addEmployee(e1);
        service.addEmployee(e2);
        service.addEmployee(e3);
        service.addEmployee(e4);
        var counts = service.countEmployees();

        assertAll(
                () -> assertEquals(2, counts.get(Position.PROGRAMISTA)),
                () -> assertEquals(1, counts.get(Position.MANAGER)),
                () -> assertEquals(1, counts.get(Position.STAZYSTA))
        );
    }

    @Test
    void salaryStatistic_average() {
        service.addEmployee(e1);
        service.addEmployee(e2);
        service.addEmployee(e3);
        double expected = (e1.getSalary() + e2.getSalary() + e3.getSalary()) / 3;

        assertAll(
                () -> assertEquals(expected, service.salaryStatistic(), 0.0001)
        );
    }

    @Test
    void salaryStatistic_oneEmployee() {
        service.addEmployee(e1);
        assertAll(
                () -> assertEquals(e1.getSalary(), service.salaryStatistic(), 0.0001)
        );
    }

    @Test
    void salaryStatistic_emptyList() {
        assertAll(
                () -> assertEquals(0, service.salaryStatistic(), 0.0001)
        );
    }

    @Test
    void bestPaid() {
        service.addEmployee(e1);
        service.addEmployee(e2);
        service.addEmployee(e3);

        assertAll(
                () -> assertEquals(e2, service.bestPaid())
        );
    }

    @Test
    void bestPaid_emptyList() {
        assertAll(
                () -> assertNull(service.bestPaid())
        );
    }
}
