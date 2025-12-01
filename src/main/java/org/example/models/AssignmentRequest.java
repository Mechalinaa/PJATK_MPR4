package org.example.models;

import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class AssignmentRequest {
    public String taskId;
    public Set<String> requiredSkills;
    public int requiredHours;

}
