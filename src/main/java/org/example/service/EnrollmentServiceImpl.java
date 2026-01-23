package org.example.service;

import org.example.dao.Course;
import org.example.dao.Enrollment;
import org.example.dao.User;
import org.example.dao.enums.EnrollmentStatus;
import org.example.repository.CourseRepository;
import org.example.repository.EnrollmentRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public EnrollmentServiceImpl(final EnrollmentRepository enrollmentRepository, final UserRepository userRepository, final CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public Enrollment enrollStudent(final Long courseId, final Long studentId) {
        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new RuntimeException("The student is already enrolled in this course");
        }

        final User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        final Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        final Enrollment enrollment = new Enrollment(student, course, LocalDate.now(), EnrollmentStatus.ACTIVE);
        return enrollmentRepository.save(enrollment);
    }

    public void unenrollStudent(final Long courseId, final Long studentId) {
        final Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new RuntimeException("The record was not found"));
        enrollmentRepository.delete(enrollment);
    }

    public List<Enrollment> getStudentEnrollments(final Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    public List<Enrollment> getCourseEnrollments(final Long courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }

    public List<Course> getStudentCourses(final Long studentId) {
        final List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        return enrollments.stream()
                .map(Enrollment::getCourse)
                .toList();
    }

    public boolean isStudentEnrolled(final Long courseId, final Long studentId) {
        return enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId);
    }
}
