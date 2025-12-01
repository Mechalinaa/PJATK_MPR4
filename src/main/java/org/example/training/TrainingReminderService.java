package org.example.training;

import lombok.AllArgsConstructor;
import org.example.training.interfaces.*;
import org.example.models.Employee;

import java.util.List;

@AllArgsConstructor
public class TrainingReminderService {
    private final CertificatesRepo repo;
    private final Sender sender;
    private final LogTool logger;

    public void sendReminders() {
        List<Employee> list = repo.findExpiringSoon();
        for (Employee e : list) {
            sender.send(e, "Szkolenie zaraz wygasa!");
        }
        logger.log("Wysłano przypomnienia: " + list.size());
    }
}
