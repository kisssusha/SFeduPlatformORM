package ru.example.eduplatform.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.example.eduplatform.entity.Course;
import ru.example.eduplatform.entity.Enrollment;
import ru.example.eduplatform.entity.User;
import ru.example.eduplatform.entity.enums.EnrollmentStatus;
import ru.example.eduplatform.entity.enums.UserRole;
import ru.example.eduplatform.repository.CourseRepository;
import ru.example.eduplatform.repository.EnrollmentRepository;
import ru.example.eduplatform.repository.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceImplTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private EnrollmentServiceImpl enrollmentServiceImpl;

    private User student;
    private Course course;
    private Enrollment enrollment;

    @BeforeEach
    void setUp() {
        student = new User("Student", "student@test.com", UserRole.STUDENT);
        student.setId(1L);

        course = new Course();
        course.setId(1L);
        course.setTitle("Test Course");

        enrollment = new Enrollment(student, course, LocalDate.now(), EnrollmentStatus.ACTIVE);
        enrollment.setId(1L);
    }

    @Test
    void shouldEnrollStudent() {
        when(enrollmentRepository.existsByStudentIdAndCourseId(1L, 1L)).thenReturn(false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);

        Enrollment result = enrollmentServiceImpl.enrollStudent(1L, 1L);

        assertThat(result).isNotNull();
        assertThat(result.getStudent()).isEqualTo(student);
        assertThat(result.getCourse()).isEqualTo(course);
        verify(enrollmentRepository).save(any(Enrollment.class));
    }

    @Test
    void shouldThrowExceptionWhenAlreadyEnrolled() {
        when(enrollmentRepository.existsByStudentIdAndCourseId(1L, 1L)).thenReturn(true);

        assertThatThrownBy(() -> enrollmentServiceImpl.enrollStudent(1L, 1L))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Студент уже записан на этот курс");

        verify(enrollmentRepository, never()).save(any());
    }

    @Test
    void shouldUnenrollStudent() {
        when(enrollmentRepository.findByStudentIdAndCourseId(1L, 1L))
            .thenReturn(Optional.of(enrollment));

        enrollmentServiceImpl.unenrollStudent(1L, 1L);

        verify(enrollmentRepository).delete(enrollment);
    }

    @Test
    void shouldCheckIfStudentEnrolled() {
        when(enrollmentRepository.existsByStudentIdAndCourseId(1L, 1L)).thenReturn(true);

        boolean enrolled = enrollmentServiceImpl.isStudentEnrolled(1L, 1L);

        assertThat(enrolled).isTrue();
    }
}

