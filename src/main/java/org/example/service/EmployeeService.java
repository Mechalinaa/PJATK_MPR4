package org.example.service;

import org.example.models.CompanyStatistics;
import org.example.models.Employee;
import org.example.models.Position;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class EmployeeService {
    ArrayList<Employee> employeeList = new ArrayList<>();

    public void addEmployee(Employee employee) {
        for (Employee emp : employeeList) {
            if (emp.getEmail().equals(employee.getEmail())) {
                throw new IllegalArgumentException("Pracownik z tym emailem juz istnieje");
            }
        }
        employeeList.add(employee);
    }

    public void showEmployees() {
        for (Employee emp : employeeList) {
            System.out.println(emp + "\n");
        }
    }

    public ArrayList<Employee> searchByCompanyName(String companyName) {
        ArrayList<Employee> companyNameList = new ArrayList<>();
        for (Employee emp : employeeList) {
            if (emp.getCompanyName().equals(companyName)) {
                companyNameList.add(emp);
            }
        }
        return companyNameList;
    }


    public ArrayList<Employee> sortEmployeesBySurname() {
        ArrayList<Employee> sortedList = new ArrayList<>(employeeList);
        Comparator<Employee> surnameComparator = new Comparator<Employee>() {
            @Override
            public int compare(Employee emp1, Employee emp2) {
                return emp1.getSurname().compareToIgnoreCase(emp2.getSurname());
            }
        };
        sortedList.sort(surnameComparator);
        return sortedList;
    }

    public HashMap<Position, ArrayList<Employee>> groupByPosition() {
        HashMap<Position, ArrayList<Employee>> groupedByPosition = new HashMap<>();
        for (Position pos : Position.values()) {
            groupedByPosition.put(pos, new ArrayList<>());
        }

        for (Employee emp : employeeList) {
            Position position = emp.getPosition();
            groupedByPosition.get(position).add(emp);

        }
        return groupedByPosition;
    }

    public HashMap<Position, Integer> countEmployees() {
        HashMap<Position, Integer> countedEmployees = new HashMap<>();
        for (Position pos : Position.values()) {
            countedEmployees.put(pos, 0);
        }
        for (Employee emp : employeeList) {
            Position position = emp.getPosition();
            countedEmployees.put(position, countedEmployees.get(position) + 1);
        }
        return countedEmployees;

    }

    public double salaryStatistic() {
        if (employeeList.isEmpty()) {
            return 0;
        }

        double allSalaries = 0;
        for (Employee emp : employeeList) {
            allSalaries += emp.getSalary();
        }

        return allSalaries / employeeList.size();
    }


    public Employee bestPaid() {
        if (employeeList.isEmpty()) {
            return null;
        }
        Employee bestPaid = employeeList.get(0);
        for (Employee emp : employeeList) {
            if (bestPaid.getSalary() < emp.getSalary()) {
                bestPaid = emp;
            }
        }
        return bestPaid;
    }

    public ArrayList<Employee> validateSalaryConsistency() {
        ArrayList<Employee> list = new ArrayList<>();

        for (Employee emp : employeeList) {
            if (emp.getSalary() < emp.getPosition().getSalary()) {
                list.add(emp);
            }
        }
        return list;
    }

    public Map<String, CompanyStatistics> getCompanyStatistics() {

        Map<String, ArrayList<Employee>> groups = new HashMap<>();

        // grupowanie
        for (Employee emp : employeeList) {
            groups.computeIfAbsent(emp.getCompanyName(), f -> new ArrayList<>()).add(emp);
        }

        Map<String, CompanyStatistics> stats = new HashMap<>();

        for (Map.Entry<String, ArrayList<Employee>> entry : groups.entrySet()) {
            String companyName = entry.getKey();
            ArrayList<Employee> employees = entry.getValue();

            int number = employees.size();

            double sum = 0;
            Employee bestPaid = employees.get(0);

            for (Employee emp : employees) {
                sum += emp.getSalary();
                if (emp.getSalary() > bestPaid.getSalary()) {
                    bestPaid = emp;
                }
            }

            double avg = sum / number;

            stats.put(companyName, new CompanyStatistics(number, avg, bestPaid));
        }
        return stats;
    }


    public void giveRaise(Employee employee, Position position) {
        if (employee.getPosition().getSalary() < position.getSalary()) {
            employee.setPosition(position);
            employee.setSalary(position.getSalary());
        } else System.out.println("Można jedynie podwyższyć stanowisko pracownika.");

    }

    public void givePercentageRaise(Employee employee, double percentage) {
        double newPaycheck = employee.getSalary() + (percentage / 100) * employee.getSalary();
        if (newPaycheck <= employee.getPosition().getMaxSalary()) {
            employee.setSalary(newPaycheck);
        } else System.out.println("Nie można nadać podwyżki bez zmiany stanowiska pracownika");
    }

    public double calculateAverageGrade(Employee employee) {
        int revsNumber = 0;
        int revsTotal = 0;
        for (int rev : employee.getListOfRevs()) {
            revsTotal += rev;
            revsNumber++;
        }
        if (revsNumber != 0) {
            return (double) revsTotal / revsNumber;
        } else return 0;
    }

    public void findBestEmployees() {
        ArrayList<Employee> listOfBestRevs = new ArrayList<>();
        for (Employee emp : employeeList) {
            if (calculateAverageGrade(emp) >= 4.5) {
                listOfBestRevs.add(emp);
            }
        }
    }

    public Period calculateInternship(Employee employee) {
        return Period.between(employee.getHireDate(), LocalDate.now());
    }

    public void findEmployeesWithLongestInternships() {
        ArrayList<Employee> longestInternship = new ArrayList<>();
        if (employeeList.isEmpty()) return;

        LocalDate earliestHireDate = employeeList.get(0).getHireDate();

        for (Employee emp : employeeList) {
            if (emp.getHireDate().isBefore(earliestHireDate)) {
                earliestHireDate = emp.getHireDate();
                longestInternship.clear();
                longestInternship.add(emp);
            } else if (emp.getHireDate().equals(earliestHireDate)) {
                longestInternship.add(emp);
            }
        }
    }
}

