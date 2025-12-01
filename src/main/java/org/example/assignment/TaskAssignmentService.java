package org.example.assignment;

import lombok.AllArgsConstructor;
import org.example.assignment.interfaces.*;
import org.example.models.AssignmentRequest;
import org.example.models.Employee;

import java.util.Optional;

@AllArgsConstructor
public class TaskAssignmentService {
    private final AvailabilityCalendar calendar;
    private final SkillsRepo skillsRepo;
    private final AllocationSystem allocationSystem;

    public Optional<Employee> assign(AssignmentRequest req) {
        return calendar.getAvailableEmployees().stream()
                .filter(e -> skillsRepo.hasSkills(e, req.requiredSkills))
                .findFirst()
                .map(e -> {
                    allocationSystem.allocate(req.taskId, e);
                    return e;
                });
    }
}
