package org.example.doubles;

import org.example.training.interfaces.Sender;
import org.example.models.Employee;

public class SenderMock implements Sender {

    public int expectedCalls;
    public int actualCalls = 0;

    @Override
    public void send(Employee employee, String message) {
        actualCalls++;
    }

    public void verify() {
        if (actualCalls != expectedCalls) {
            throw new AssertionError("Expected " + expectedCalls +
                    " but got " + actualCalls);
        }
    }
}
