package org.example.export;

import org.example.export.interfaces.EmpRepo;
import org.example.export.interfaces.Formatter;
import org.example.models.Employee;
import org.example.models.Position;
import org.example.doubles.FileWriterSpy;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeExportServiceTest {

    static class RepoFake implements EmpRepo {
        private final List<Employee> list;

        RepoFake(List<Employee> list) {
            this.list = list;
        }

        @Override
        public List<Employee> getAll() {
            return list;
        }
    }

    static class FormatterStub implements Formatter {
        private final String text;

        FormatterStub(String text) {
            this.text = text;
        }

        @Override
        public String format(List<Employee> employees) {
            return text;
        }
    }

    @Test
    void savesFileWithFormattedContent() {
        // given
        Employee emp = new Employee("Jan","A","jan@pl.pl","A", Position.PROGRAMISTA);

        RepoFake repoFake = new RepoFake(List.of(emp));
        FormatterStub formatterStub = new FormatterStub("TEST_FORMAT");
        FileWriterSpy writerSpy = new FileWriterSpy();

        EmployeeExportService service =
                new EmployeeExportService(repoFake, formatterStub, writerSpy);

        // when
        service.exportToFile("plik.txt");

        // then
        assertAll(
                () -> assertEquals(1, writerSpy.calls),
                () -> assertEquals("plik.txt", writerSpy.lastName),
                () -> assertEquals("TEST_FORMAT", writerSpy.lastContent)
        );

    }
}
