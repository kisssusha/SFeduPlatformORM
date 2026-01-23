package ru.example.eduplatform.service;

import ru.example.eduplatform.entity.AnswerOption;
import ru.example.eduplatform.entity.Question;
import ru.example.eduplatform.entity.Quiz;
import ru.example.eduplatform.entity.QuizSubmission;
import ru.example.eduplatform.entity.enums.QuestionType;

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
