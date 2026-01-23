package org.example.service;

import org.example.dao.Assignment;
import org.example.dao.Submission;

import java.time.LocalDate;
import java.util.List;

public interface AssignmentService {

    Assignment createAssignment(Long lessonId, String title, String description, LocalDate dueDate, Integer maxScore);

    Assignment getAssignmentById(Long assignmentId);

    List<Assignment> getAssignmentsByLesson(Long lessonId);

    List<Assignment> getAssignmentsByCourse(Long courseId);

    Submission submitAssignment(Long assignmentId, Long studentId, String content);

    void gradeSubmission(Long submissionId, Integer score, String feedback);

    List<Submission> getSubmissionsByAssignment(Long assignmentId);

    List<Submission> getSubmissionsByStudent(Long studentId);
}
