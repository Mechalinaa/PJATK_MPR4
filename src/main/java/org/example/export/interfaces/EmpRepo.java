package org.example.export.interfaces;

import org.example.models.Employee;
import java.util.List;

public interface EmpRepo {
    List<Employee> getAll();
}
