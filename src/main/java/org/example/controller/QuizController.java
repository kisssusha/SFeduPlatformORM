package ru.example.eduplatform.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.eduplatform.entity.QuizSubmission;
import ru.example.eduplatform.service.QuizService;
import ru.example.eduplatform.service.QuizServiceImpl;
import ru.example.eduplatform.controller.dto.QuizDto;
import ru.example.eduplatform.entity.AnswerOption;
import ru.example.eduplatform.entity.Question;
import ru.example.eduplatform.entity.Quiz;
import ru.example.eduplatform.entity.enums.QuestionType;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(final QuizServiceImpl quizServiceImpl) {
        this.quizService = quizServiceImpl;
    }

    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@RequestBody final QuizDto quizDto) {
        final Quiz quiz = quizService.createQuiz(quizDto.courseModuleId(), quizDto.title(), quizDto.timeLimit());
        return ResponseEntity.ok(quiz);
    }

    @PostMapping("/{quizId}/questions")
    public ResponseEntity<Question> addQuestion(
            @PathVariable final Long quizId,
            @RequestParam final String text,
            @RequestParam final QuestionType type) {
        final Question question = quizService.addQuestionToQuiz(quizId, text, type);
        return ResponseEntity.ok(question);
    }

    @PostMapping("/questions/{questionId}/options")
    public ResponseEntity<AnswerOption> addAnswerOption(
            @PathVariable final Long questionId,
            @RequestParam final String text,
            @RequestParam final boolean isCorrect) {
        final AnswerOption option = quizService.addAnswerOption(questionId, text, isCorrect);
        return ResponseEntity.ok(option);
    }

    @PostMapping("/{quizId}/take")
    public ResponseEntity<QuizSubmission> takeQuiz(
            @PathVariable final Long quizId,
            @RequestParam final Long studentId,
            @RequestBody final Map<Long, Long> answers) {
        final QuizSubmission submission = quizService.takeQuiz(quizId, studentId, answers);
        return ResponseEntity.ok(submission);
    }

    @GetMapping("/module/{courseModuleId}")
    public ResponseEntity<Quiz> getQuizByModule(@PathVariable final Long courseModuleId) {
        final Quiz quiz = quizService.getQuizByModuleId(courseModuleId);
        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/{quizId}/submissions")
    public ResponseEntity<List<QuizSubmission>> getQuizSubmissions(@PathVariable final Long quizId) {
        final List<QuizSubmission> submissions = quizService.getQuizSubmissions(quizId);
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/submissions/student/{studentId}")
    public ResponseEntity<List<QuizSubmission>> getStudentQuizSubmissions(@PathVariable final Long studentId) {
        final List<QuizSubmission> submissions = quizService.getStudentQuizSubmissions(studentId);
        return ResponseEntity.ok(submissions);
    }
}
