package org.example.training.interfaces;

import org.example.models.Employee;
import java.util.List;

public interface CertificatesRepo {
    List<Employee> findExpiringSoon();
}
