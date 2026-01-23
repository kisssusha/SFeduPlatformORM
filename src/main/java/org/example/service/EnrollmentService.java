package org.example.service;

import org.example.dao.Course;
import org.example.dao.Enrollment;

import java.util.List;

public interface EnrollmentService {
    Enrollment enrollStudent(Long courseId, Long studentId);

    void unenrollStudent(Long courseId, Long studentId);

    List<Enrollment> getStudentEnrollments(Long studentId);

    List<Enrollment> getCourseEnrollments(Long courseId);

    List<Course> getStudentCourses(Long studentId);

    boolean isStudentEnrolled(Long courseId, Long studentId);
}
