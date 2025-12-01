package org.example.models;

import lombok.*;

@Getter
@RequiredArgsConstructor
public enum Position {
    PREZES(25000, 50000),
    WICEPREZES(18000, 23000),
    MANAGER(12000, 16000),
    PROGRAMISTA(8000, 10000),
    STAZYSTA(3000, 5000);
    private final double salary;
    private final int maxSalary;

}