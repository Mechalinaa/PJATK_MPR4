package org.example.doubles;

import org.example.training.interfaces.CertificatesRepo;
import org.example.models.Employee;
import java.util.List;

public class CertificatesRepoStub implements CertificatesRepo {

    private final List<Employee> data;

    public CertificatesRepoStub(List<Employee> data) {
        this.data = data;
    }

    @Override
    public List<Employee> findExpiringSoon() {
        return data;
    }
}
