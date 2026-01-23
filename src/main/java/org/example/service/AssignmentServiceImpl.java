package ru.example.eduplatform.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.eduplatform.entity.Submission;
import ru.example.eduplatform.entity.User;
import ru.example.eduplatform.entity.Assignment;
import ru.example.eduplatform.entity.Lesson;
import ru.example.eduplatform.repository.AssignmentRepository;
import ru.example.eduplatform.repository.LessonRepository;
import ru.example.eduplatform.repository.SubmissionRepository;
import ru.example.eduplatform.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;

    private final LessonRepository lessonRepository;

    private final SubmissionRepository submissionRepository;

    private final UserRepository userRepository;

    public AssignmentServiceImpl(final AssignmentRepository assignmentRepository,
                                 final LessonRepository lessonRepository,
                                 final SubmissionRepository submissionRepository,
                                 final UserRepository userRepository) {
        this.assignmentRepository = assignmentRepository;
        this.lessonRepository = lessonRepository;
        this.submissionRepository = submissionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Assignment createAssignment(final Long lessonId, final String title, final String description,
                                       final LocalDate dueDate, final Integer maxScore) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Урок не найден"));

        Assignment assignment = new Assignment(lesson, title, description, dueDate, maxScore);
        return assignmentRepository.save(assignment);
    }

    public Assignment getAssignmentById(final Long assignmentId) {
        return assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Задание не найдено"));
    }

    public List<Assignment> getAssignmentsByLesson(final Long lessonId) {
        return assignmentRepository.findByLessonId(lessonId);
    }

    public List<Assignment> getAssignmentsByCourse(final Long courseId) {
        return assignmentRepository.findByCourseId(courseId);
    }

    @Transactional
    public Submission submitAssignment(final Long assignmentId, final Long studentId, final String content) {
        if (submissionRepository.existsByAssignmentIdAndStudentId(assignmentId, studentId)) {
            throw new RuntimeException("Студент уже отправил это задание");
        }

        Assignment assignment = getAssignmentById(assignmentId);
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Студент не найден"));

        Submission submission = new Submission(assignment, student, content);
        return submissionRepository.save(submission);
    }

    @Transactional
    public void gradeSubmission(final Long submissionId, final Integer score, final String feedback) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Решение не найдено"));
        submission.setScore(score);
        submission.setFeedback(feedback);
        submissionRepository.save(submission);
    }

    public List<Submission> getSubmissionsByAssignment(final Long assignmentId) {
        return submissionRepository.findByAssignmentId(assignmentId);
    }

    public List<Submission> getSubmissionsByStudent(final Long studentId) {
        return submissionRepository.findByStudentId(studentId);
    }
}
