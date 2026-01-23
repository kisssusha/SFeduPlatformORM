package ru.example.eduplatform.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.eduplatform.entity.Enrollment;
import ru.example.eduplatform.entity.User;
import ru.example.eduplatform.entity.enums.EnrollmentStatus;
import ru.example.eduplatform.entity.Course;
import ru.example.eduplatform.repository.CourseRepository;
import ru.example.eduplatform.repository.EnrollmentRepository;
import ru.example.eduplatform.repository.UserRepository;

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
            throw new RuntimeException("Студент уже записан на этот курс");
        }

        final User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Студент не найден"));
        final Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Курс не найден"));

        final Enrollment enrollment = new Enrollment(student, course, LocalDate.now(), EnrollmentStatus.ACTIVE);
        return enrollmentRepository.save(enrollment);
    }

    public void unenrollStudent(final Long courseId, final Long studentId) {
        final Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new RuntimeException("Запись не найдена"));
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
