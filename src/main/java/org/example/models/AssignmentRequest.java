package org.example.models;

import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class AssignmentRequest {
    String taskId;
    Set<String> requiredSkills;
    int requiredHours;

}
