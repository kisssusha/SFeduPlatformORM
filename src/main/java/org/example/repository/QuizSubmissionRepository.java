package ru.example.eduplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.eduplatform.entity.QuizSubmission;

import java.util.List;

@Repository
public interface QuizSubmissionRepository extends JpaRepository<QuizSubmission, Long> {
    List<QuizSubmission> findByQuizId(Long quizId);

    List<QuizSubmission> findByStudentId(Long studentId);

    boolean existsByQuizIdAndStudentId(Long quizId, Long studentId);
}
