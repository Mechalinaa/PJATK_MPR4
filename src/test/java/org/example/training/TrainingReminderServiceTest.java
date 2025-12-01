package org.example.training;

import org.example.models.Employee;
import org.example.models.Position;
import org.example.doubles.CertificatesRepoStub;
import org.example.doubles.LoggerDummy;
import org.example.doubles.SenderMock;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrainingReminderServiceTest {

    @Test
    void sendsReminderToEachEmployee() {
        // given
        Employee e1 = new Employee("Jan","A","jan@pl.pl","A",Position.PROGRAMISTA);
        Employee e2 = new Employee("Ola","B","ola@pl.pl","A",Position.STAZYSTA);

        CertificatesRepoStub repo = new CertificatesRepoStub(List.of(e1, e2));
        SenderMock senderMock = new SenderMock();
        LoggerDummy loggerDummy = new LoggerDummy();

        senderMock.expectedCalls = 2;

        TrainingReminderService service =
                new TrainingReminderService(repo, senderMock, loggerDummy);

        // when
        service.sendReminders();

        // then
        senderMock.verify();
    }

    @Test
    void noEmployees_noRemindersSent() {
        // given
        CertificatesRepoStub repo = new CertificatesRepoStub(List.of());
        SenderMock senderMock = new SenderMock();
        LoggerDummy dummy = new LoggerDummy();

        TrainingReminderService service =
                new TrainingReminderService(repo, senderMock, dummy);

        senderMock.expectedCalls = 0;

        // when
        service.sendReminders();

        // then
        senderMock.verify();
    }
}
