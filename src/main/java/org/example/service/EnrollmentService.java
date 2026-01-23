package ru.example.eduplatform.service;

import ru.example.eduplatform.entity.Enrollment;
import ru.example.eduplatform.entity.Course;

import java.util.List;

public interface EnrollmentService {
    Enrollment enrollStudent(Long courseId, Long studentId);

    void unenrollStudent(Long courseId, Long studentId);

    List<Enrollment> getStudentEnrollments(Long studentId);

    List<Enrollment> getCourseEnrollments(Long courseId);

    List<Course> getStudentCourses(Long studentId);

    boolean isStudentEnrolled(Long courseId, Long studentId);
}
