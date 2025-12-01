package org.example.doubles;

import org.example.assignment.interfaces.SkillsRepo;
import org.example.models.Employee;
import java.util.*;

public class SkillsRepoFake implements SkillsRepo {

    private final Map<Employee, Set<String>> map = new HashMap<>();

    public void add(Employee e, Set<String> skills) {
        map.put(e, skills);
    }

    @Override
    public boolean hasSkills(Employee employee, Set<String> skills) {
        return map.containsKey(employee) && map.get(employee).containsAll(skills);
    }
}
