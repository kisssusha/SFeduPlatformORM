package org.example.service;

import org.example.dao.Assignment;
import org.example.dao.Lesson;
import org.example.dao.Submission;
import org.example.dao.User;
import org.example.dao.enums.UserRole;
import org.example.repository.AssignmentRepository;
import org.example.repository.LessonRepository;
import org.example.repository.SubmissionRepository;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssignmentServiceImplTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private SubmissionRepository submissionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AssignmentServiceImpl assignmentServiceImpl;

    private Lesson lesson;
    private Assignment assignment;
    private User student;
    private Submission submission;

    @BeforeEach
    void setUp() {
        lesson = new Lesson();
        lesson.setId(1L);

        assignment = new Assignment(lesson, "Test Assignment", "Description",
                LocalDate.now().plusDays(7), 100);
        assignment.setId(1L);

        student = new User("Student", "student@test.com", UserRole.STUDENT);
        student.setId(1L);

        submission = new Submission(assignment, student, "Solution");
        submission.setId(1L);
    }

    @Test
    void shouldCreateAssignment() {
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        when(assignmentRepository.save(any(Assignment.class))).thenReturn(assignment);

        Assignment created = assignmentServiceImpl.createAssignment(1L, "Test", "Desc",
                LocalDate.now(), 100);

        assertThat(created).isNotNull();
        verify(assignmentRepository).save(any(Assignment.class));
    }

    @Test
    void shouldSubmitAssignment() {
        when(submissionRepository.existsByAssignmentIdAndStudentId(1L, 1L)).thenReturn(false);
        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(assignment));
        when(userRepository.findById(1L)).thenReturn(Optional.of(student));
        when(submissionRepository.save(any(Submission.class))).thenReturn(submission);

        Submission result = assignmentServiceImpl.submitAssignment(1L, 1L, "Solution");

        assertThat(result).isNotNull();
        verify(submissionRepository).save(any(Submission.class));
    }

    @Test
    void shouldThrowExceptionWhenSubmissionAlreadyExists() {
        when(submissionRepository.existsByAssignmentIdAndStudentId(1L, 1L)).thenReturn(true);

        assertThatThrownBy(() -> assignmentServiceImpl.submitAssignment(1L, 1L, "Solution"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("The student has already submitted this assignment");
    }

    @Test
    void shouldGradeSubmission() {
        when(submissionRepository.findById(1L)).thenReturn(Optional.of(submission));
        when(submissionRepository.save(any(Submission.class))).thenReturn(submission);

        assignmentServiceImpl.gradeSubmission(1L, 95, "Good work");

        verify(submissionRepository).save(submission);
        assertThat(submission.getScore()).isEqualTo(95);
        assertThat(submission.getFeedback()).isEqualTo("Good work");
    }
}

