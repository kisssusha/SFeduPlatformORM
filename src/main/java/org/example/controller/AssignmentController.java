package org.example.controller;

import org.example.dto.AssignmentDto;
import org.example.dao.Assignment;
import org.example.dao.Submission;
import org.example.service.AssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignment")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping
    public ResponseEntity<Assignment> createAssignment(@RequestBody final AssignmentDto dto) {
        final Assignment assignment = assignmentService.createAssignment(dto.lessonId(), dto.title(), dto.description(), dto.dueDate(), dto.maxScore());
        return ResponseEntity.ok(assignment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignment(@PathVariable final Long id) {
        Assignment assignment = assignmentService.getAssignmentById(id);
        return ResponseEntity.ok(assignment);
    }

    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<Assignment>> getAssignmentsByLesson(@PathVariable final Long lessonId) {
        final List<Assignment> assignments = assignmentService.getAssignmentsByLesson(lessonId);
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Assignment>> getAssignmentsByCourse(@PathVariable final Long courseId) {
        final List<Assignment> assignments = assignmentService.getAssignmentsByCourse(courseId);
        return ResponseEntity.ok(assignments);
    }

    @PostMapping("/{assignmentId}/submit")
    public ResponseEntity<Submission> submitAssignment(
            @PathVariable final Long assignmentId,
            @RequestParam final Long studentId,
            @RequestParam final String content) {
        final Submission submission = assignmentService.submitAssignment(assignmentId, studentId, content);
        return ResponseEntity.ok(submission);
    }

    @PutMapping("/submissions/{submissionId}/grade")
    public ResponseEntity<Void> gradeSubmission(
            @PathVariable final Long submissionId,
            @RequestParam final Integer score,
            @RequestParam(required = false) final String feedback) {
        assignmentService.gradeSubmission(submissionId, score, feedback);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{assignmentId}/submissions")
    public ResponseEntity<List<Submission>> getSubmissionsByAssignment(@PathVariable final Long assignmentId) {
        final List<Submission> submissions = assignmentService.getSubmissionsByAssignment(assignmentId);
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/submissions/student/{studentId}")
    public ResponseEntity<List<Submission>> getSubmissionsByStudent(@PathVariable final Long studentId) {
        final List<Submission> submissions = assignmentService.getSubmissionsByStudent(studentId);
        return ResponseEntity.ok(submissions);
    }
}
