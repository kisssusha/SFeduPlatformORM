package org.example.service;

import org.example.dao.*;
import org.example.dao.Module;
import org.example.dao.enums.QuestionType;
import org.example.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AnswerOptionRepository answerOptionRepository;
    private final QuizSubmissionRepository quizSubmissionRepository;
    private final UserRepository userRepository;
    private final ModuleRepository moduleRepository;

    public QuizServiceImpl(final QuizRepository quizRepository,
                           final QuestionRepository questionRepository,
                           final AnswerOptionRepository answerOptionRepository,
                           final QuizSubmissionRepository quizSubmissionRepository,
                           final UserRepository userRepository,
                           final ModuleRepository moduleRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.answerOptionRepository = answerOptionRepository;
        this.quizSubmissionRepository = quizSubmissionRepository;
        this.userRepository = userRepository;
        this.moduleRepository = moduleRepository;
    }

    @Transactional
    public Quiz createQuiz(final Long courseModuleId, final String title, final Integer timeLimit) {
        Module module = moduleRepository.findById(courseModuleId)
                .orElseThrow(() -> new RuntimeException("The module was not found"));

        Quiz quiz = new Quiz(module, title);
        quiz.setTimeLimit(timeLimit);
        return quizRepository.save(quiz);
    }

    @Transactional
    public Question addQuestionToQuiz(final Long quizId, final String text, final QuestionType type) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("The test was not found"));

        Question question = new Question(quiz, text, type);
        return questionRepository.save(question);
    }

    @Transactional
    public AnswerOption addAnswerOption(final Long questionId, final String text, final boolean isCorrect) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        AnswerOption option = new AnswerOption(question, text, isCorrect);
        return answerOptionRepository.save(option);
    }

    @Transactional
    public QuizSubmission takeQuiz(final Long quizId, final Long studentId, final Map<Long, Long> answers) {
        if (quizSubmissionRepository.existsByQuizIdAndStudentId(quizId, studentId)) {
            throw new RuntimeException("The student has already passed this test");
        }

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("The test was not found"));
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        int correctAnswers = 0;
        for (Question question : quiz.getQuestions()) {
            Long selectedOptionId = answers.get(question.getId());
            if (selectedOptionId != null) {
                AnswerOption selectedOption = answerOptionRepository.findById(selectedOptionId)
                        .orElseThrow(() -> new RuntimeException("The answer option was not found."));
                if (selectedOption.isCorrect()) {
                    correctAnswers++;
                }
            }
        }

        int score = !quiz.getQuestions().isEmpty() ?
                (correctAnswers * 100) / quiz.getQuestions().size() : 0;

        QuizSubmission submission = new QuizSubmission(quiz, student, score);
        return quizSubmissionRepository.save(submission);
    }

    public List<QuizSubmission> getQuizSubmissions(final Long quizId) {
        return quizSubmissionRepository.findByQuizId(quizId);
    }

    public List<QuizSubmission> getStudentQuizSubmissions(final Long studentId) {
        return quizSubmissionRepository.findByStudentId(studentId);
    }

    public Quiz getQuizByModuleId(final Long courseModuleId) {
        return quizRepository.findByModuleId(courseModuleId)
                .orElseThrow(() -> new RuntimeException("No test was found for this module."));
    }
}
