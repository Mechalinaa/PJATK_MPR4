package org.example.assignment;

import org.example.assignment.interfaces.AllocationSystem;
import org.example.assignment.interfaces.AvailabilityCalendar;
import org.example.models.AssignmentRequest;
import org.example.models.Employee;
import org.example.models.Position;
import org.example.doubles.SkillsRepoFake;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TaskAssignmentServiceTest {

    static class AllocationSpy implements AllocationSystem {
        public int calls = 0;
        public Employee lastAssigned;

        @Override
        public void allocate(String taskId, Employee employee) {
            calls++;
            lastAssigned = employee;
        }
    }

    static class CalendarStub implements AvailabilityCalendar {
        private final List<Employee> employees;

        CalendarStub(List<Employee> employees) {
            this.employees = employees;
        }

        @Override
        public List<Employee> getAvailableEmployees() {
            return employees;
        }
    }

    @Test
    void assignsFirstEmployeeWithRequiredSkills() {
        // given
        Employee e1 = new Employee("Jan", "A", "jan@x.pl","Firma", Position.PROGRAMISTA);
        Employee e2 = new Employee("Ola", "B", "ola@x.pl","Firma", Position.MANAGER);

        AvailabilityCalendar calendar = new CalendarStub(List.of(e1, e2));

        SkillsRepoFake skillsFake = new SkillsRepoFake();
        skillsFake.add(e2, Set.of("Java"));  // tylko Ola ma wymagane umiejętności

        AllocationSpy allocationSpy = new AllocationSpy();

        TaskAssignmentService service =
                new TaskAssignmentService(calendar, skillsFake, allocationSpy);

        AssignmentRequest req = new AssignmentRequest("T-1", Set.of("Java"), 4);

        // when
        Optional<Employee> result = service.assign(req);

        // then
        assertTrue(result.isPresent());
        assertEquals(e2, result.get());
        assertEquals(1, allocationSpy.calls);
        assertEquals(e2, allocationSpy.lastAssigned); //assertall fake i spy
    }
}
