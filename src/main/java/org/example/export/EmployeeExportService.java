package org.example.export;

import java.util.List;

import lombok.AllArgsConstructor;
import org.example.models.Employee;
import org.example.export.interfaces.*;

@AllArgsConstructor
public class EmployeeExportService {
    private final EmpRepo repo;
    private final Formatter formatter;
    private final FileWriter writer;

    public void exportToFile(String fileName) {
        List<Employee> employees = repo.getAll();
        String content = formatter.format(employees);
        writer.save(fileName, content);
    }
}
