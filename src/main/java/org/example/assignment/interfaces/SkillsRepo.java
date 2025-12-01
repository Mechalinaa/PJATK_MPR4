package org.example.assignment.interfaces;

import org.example.models.Employee;
import java.util.Set;

public interface SkillsRepo {
    boolean hasSkills(Employee employee, Set<String> skills);
}
