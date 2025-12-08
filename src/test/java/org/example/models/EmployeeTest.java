package org.example.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    private Employee emp1;
    private Employee emp2;
    private Employee emp3;

    @BeforeEach
    void setUp() {
        emp1 = new Employee("Jan", "Kowalski", "jan@firma.pl", "FirmaA", Position.PROGRAMISTA);
        emp2 = new Employee("Jan", "Kowalski", "jan@firma.pl", "FirmaA", Position.PROGRAMISTA);
        emp3 = new Employee("Anna", "Nowak", "anna@firma.pl", "FirmaB", Position.MANAGER);
    }

    @Test
    void equalsAndHashCode_basedOnEmail() {
        assertAll(
                () -> assertEquals(emp1, emp2),
                () -> assertEquals(emp1.hashCode(), emp2.hashCode()),
                () -> assertNotEquals(emp1, emp3)
        );
    }

    @Test
    void constructor_setsSalaryFromPosition() {
        Position position = Position.STAZYSTA;
        Employee e = new Employee("Marek", "Nowy", "marek@f.pl", "FirmaX", position);

        assertAll(
                () -> assertEquals(position.getSalary(), e.getSalary())
        );
    }

    @Test
    void setters_acceptValidValues() {
        Employee e = new Employee("A", "B", "a@b.pl", "Firma", Position.MANAGER);

        assertAll(
                () -> e.setName("Marek"),
                () -> e.setSurname("Lewandowski"),
                () -> e.setEmail("nowy@mail.com"),
                () -> e.setCompanyName("NowaFirma"),
                () -> e.setPosition(Position.PROGRAMISTA),
                () -> e.setSalary(9999)
        );
    }

    @Test
    void setters_shouldThrowOnInvalidInputs() {
        Employee e = new Employee("Adam", "Kowal", "adam@f.pl", "FirmaZ", Position.PROGRAMISTA);

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> e.setName("")),
                () -> assertThrows(IllegalArgumentException.class, () -> e.setSurname("   ")),
                () -> assertThrows(IllegalArgumentException.class, () -> e.setEmail("invalidEmail")),
                () -> assertThrows(IllegalArgumentException.class, () -> e.setCompanyName("")),
                () -> assertThrows(IllegalArgumentException.class, () -> e.setPosition(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> e.setSalary(-1))
        );
    }
}
