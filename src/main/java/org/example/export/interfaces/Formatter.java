package org.example.export.interfaces;

import org.example.models.Employee;
import java.util.List;

public interface Formatter {
    String format(List<Employee> employees);
}
