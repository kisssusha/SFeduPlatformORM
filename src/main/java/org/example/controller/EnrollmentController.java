package ru.example.eduplatform.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.eduplatform.entity.Enrollment;
import ru.example.eduplatform.service.EnrollmentService;
import ru.example.eduplatform.controller.dto.EnrollmentDto;
import ru.example.eduplatform.entity.Course;

import java.util.List;

@RestController
@RequestMapping("/api/enrollment")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(final EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public ResponseEntity<Enrollment> enrollStudent(
            @RequestBody final EnrollmentDto enrollmentDto) {
        final Enrollment enrollment = enrollmentService.enrollStudent(enrollmentDto.courseId(), enrollmentDto.studentId());
        return ResponseEntity.ok(enrollment);
    }

    @DeleteMapping
    public ResponseEntity<Void> unenrollStudent(
            @RequestParam final Long courseId,
            @RequestParam final Long studentId) {
        enrollmentService.unenrollStudent(courseId, studentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Enrollment>> getStudentEnrollments(@PathVariable final Long studentId) {
        final List<Enrollment> enrollments = enrollmentService.getStudentEnrollments(studentId);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Enrollment>> getCourseEnrollments(@PathVariable final Long courseId) {
        final List<Enrollment> enrollments = enrollmentService.getCourseEnrollments(courseId);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/student/{studentId}/courses")
    public ResponseEntity<List<Course>> getStudentCourses(@PathVariable final Long studentId) {
        final List<Course> courses = enrollmentService.getStudentCourses(studentId);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> isStudentEnrolled(
            @RequestParam final Long courseId,
            @RequestParam final Long studentId) {
        final boolean enrolled = enrollmentService.isStudentEnrolled(courseId, studentId);
        return ResponseEntity.ok(enrolled);
    }
}
