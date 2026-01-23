package org.example.service;

import org.example.dao.AnswerOption;
import org.example.dao.Question;
import org.example.dao.Quiz;
import org.example.dao.QuizSubmission;
import org.example.dao.enums.QuestionType;

import java.util.List;
import java.util.Map;

public interface QuizService {
    Quiz createQuiz(Long courseModuleId, String title, Integer timeLimit);

    Question addQuestionToQuiz(Long quizId, String text, QuestionType type);

    AnswerOption addAnswerOption(Long questionId, String text, boolean isCorrect);

    QuizSubmission takeQuiz(Long quizId, Long studentId, Map<Long, Long> answers);

    List<QuizSubmission> getQuizSubmissions(Long quizId);

    List<QuizSubmission> getStudentQuizSubmissions(Long studentId);

    Quiz getQuizByModuleId(Long courseModuleId);
}
