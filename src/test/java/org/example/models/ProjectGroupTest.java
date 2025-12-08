package org.example.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectGroupTest {

    private ProjectGroup group;

    @BeforeEach
    void setUp() {
        group = new ProjectGroup();
    }

    private Employee createEmployee(String email, Position position) {
        return new Employee("Jan", "Kowalski", email, "ABC", position);
    }

    @Test
    void shouldAddEmployeeWhenHeHasNoGroup() {
        Employee emp = createEmployee("a@a.pl", Position.PROGRAMISTA);

        group.addMember(emp);

        assertAll(
                () -> assertEquals(1, group.getMembers().size()),
                () -> assertEquals(group, emp.getProjectGroup())
        );
    }

    @Test
    void shouldNotAddTwoEmployeesWithSamePosition() {
        Employee emp1 = createEmployee("a@a.pl", Position.PROGRAMISTA);
        Employee emp2 = createEmployee("b@b.pl", Position.PROGRAMISTA);

        group.addMember(emp1);
        group.addMember(emp2);

        assertAll(
                () -> assertEquals(1, group.getMembers().size()),
                () -> assertNull(emp2.getProjectGroup())
        );
    }

    @Test
    void shouldNotAddEmployeeWhenGroupIsFull() {
        group.addMember(createEmployee("1@a.pl", Position.PREZES));
        group.addMember(createEmployee("2@a.pl", Position.WICEPREZES));
        group.addMember(createEmployee("3@a.pl", Position.MANAGER));
        group.addMember(createEmployee("4@a.pl", Position.PROGRAMISTA));
        group.addMember(createEmployee("5@a.pl", Position.STAZYSTA));

        Employee extra = createEmployee("6@a.pl", Position.PROGRAMISTA);
        group.addMember(extra);

        assertAll(
                () -> assertEquals(5, group.getMembers().size()),
                () -> assertNull(extra.getProjectGroup())
        );
    }

    @Test
    void shouldMoveEmployeeFromPreviousGroupToNewOne() {
        ProjectGroup previousGroup = new ProjectGroup();
        Employee emp = createEmployee("x@x.pl", Position.MANAGER);

        previousGroup.addMember(emp);
        group.addMember(emp);

        assertAll(
                () -> assertEquals(0, previousGroup.getMembers().size()),
                () -> assertEquals(1, group.getMembers().size()),
                () -> assertEquals(group, emp.getProjectGroup())
        );
    }
}
