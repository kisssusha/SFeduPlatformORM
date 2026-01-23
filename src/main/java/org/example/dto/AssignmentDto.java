package org.example.dto;

import java.time.LocalDate;

public record AssignmentDto(Long lessonId, String title, String description, LocalDate dueDate, Integer maxScore) {
}
