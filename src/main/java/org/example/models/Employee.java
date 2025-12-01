package org.example.models;

import lombok.*;
import org.example.utils.Validation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode(of = "email")
public class Employee {
    private String name;
    private String surname;
    private String email; //unique id
    private String companyName;
    private Position position;
    private double salary;
    private List<Integer> listOfRevs;
    private LocalDate hireDate;
    @Setter
    private ProjectGroup projectGroup;

    public Employee(String name, String surname, String email, String companyName, Position position) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.companyName = companyName;
        this.position = position;
        hireDate = LocalDate.now();
        salary = position.getSalary();
        listOfRevs = new ArrayList<>();
    }

    public void setName(String name) {
        if (!Validation.isStringValid(name)) {
            throw new IllegalArgumentException("Puste imie");
        }
        this.name = name;
    }

    public void setSurname(String surname) {
        if (!Validation.isStringValid(surname)) {
            throw new IllegalArgumentException("Puste nazwisko");
        }
        this.surname = surname;
    }

    public void setEmail(String email) {
        if (!Validation.isStringValid(email)) {
            throw new IllegalArgumentException("Pusty email");
        }
        if (!Validation.isEmailValid(email)) {
            throw new IllegalArgumentException("Niepoprawny format maila - bez @");
        }
        this.email = email;
    }

    public void setCompanyName(String companyName) {
        if (!Validation.isStringValid(companyName)) {
            throw new IllegalArgumentException("Niepoprawna nazwa firmy");
        }
        this.companyName = companyName;
    }

    public void setPosition(Position position) {
        if (!Validation.isPositionValid(position)) {
            throw new IllegalArgumentException("Nie ma takiego stanowiska w firmie");
        }
        this.position = position;
        this.salary = position.getSalary();
    }

    public void setSalary(double salary) {
        if (!Validation.isSalaryValid(salary)) {
            throw new IllegalArgumentException("Pensja nie może być ujemna");
        }
        this.salary = salary;
    }
    public void addGrade(int grade){
        if(grade >=1 && grade<=5){
            getListOfRevs().add(grade);
        };

    }
}
