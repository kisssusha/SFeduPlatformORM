package ru.example.eduplatform.generator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.example.eduplatform.entity.*;
import ru.example.eduplatform.entity.Module;
import ru.example.eduplatform.repository.*;
import ru.example.eduplatform.service.AssignmentService;
import ru.example.eduplatform.service.CourseService;
import ru.example.eduplatform.service.EnrollmentService;
import ru.example.eduplatform.service.QuizService;
import ru.example.eduplatform.entity.enums.QuestionType;
import ru.example.eduplatform.entity.enums.UserRole;

import java.time.LocalDate;
import java.util.List;

@Component
@org.springframework.context.annotation.Profile("!test")
public class DataInitializer implements CommandLineRunner {

    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final AssignmentService assignmentService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final QuizService quizService;
    private final TagRepository tagRepository;
    private final ProfileRepository profileRepository;
    private final CourseReviewRepository courseReviewRepository;
    private final LessonRepository lessonRepository;

    public DataInitializer(final CourseService courseService,
                           final EnrollmentService enrollmentService,
                           final AssignmentService assignmentService,
                           final QuizService quizService,
                           final UserRepository userRepository,
                           final CategoryRepository categoryRepository,
                           final TagRepository tagRepository,
                           final ProfileRepository profileRepository,
                           final CourseReviewRepository courseReviewRepository,
                           final LessonRepository lessonRepository) {
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.assignmentService = assignmentService;
        this.quizService = quizService;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
        this.profileRepository = profileRepository;
        this.courseReviewRepository = courseReviewRepository;
        this.lessonRepository = lessonRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initializeData();
    }

    private void initializeData() {

        // Создаем пользователей
        final User lecturer = userRepository.save(new User("Prof. Vasily Petrov", "vasya@edu.ru", UserRole.TEACHER));
        final User learner1 = userRepository.save(new User("Maria Ivanova", "mariia@mail.ru", UserRole.STUDENT));
        final User learner2 = userRepository.save(new User("Ivan Smirnov", "ivan.sm@gmail.com", UserRole.STUDENT));

        // Профили пользователей
        final Profile lectProfil = new Profile(lecturer, "Эксперт по веб-разработке", "img_avatar_lect.png");
        final Profile studProfil1 = new Profile(learner1, "Факультет ИТ", "img_avatar_stud1.png");
        profileRepository.saveAll(List.of(lectProfil, studProfil1));

        // Категории курсов
        final Category webDevCat = categoryRepository.save(new Category("Web разработка"));

        // Теги
        final Tag htmlTag = tagRepository.save(new Tag("HTML"));
        final Tag cssTag = tagRepository.save(new Tag("CSS"));
        final Tag jsTag = tagRepository.save(new Tag("JavaScript"));

        // Курсы
        final Course basicHtml = courseService.createCourse(
                "Введение в HTML",
                "Изучаем основы разметки веб-документов.",
                webDevCat.getId(),
                lecturer.getId()
        );
        basicHtml.setStartDate(LocalDate.now().minusMonths(2)); // старт два месяца назад

        final Course advancedJs = courseService.createCourse(
                "JavaScript продвинутый уровень",
                "Разбираемся с асинхронностью и API.",
                webDevCat.getId(),
                lecturer.getId()
        );
        advancedJs.setStartDate(LocalDate.now()); // начат сегодня

        // Добавляем теги к курсам
        courseService.addTagToCourse(basicHtml.getId(), htmlTag.getId());
        courseService.addTagToCourse(advancedJs.getId(), jsTag.getId());
        courseService.addTagToCourse(advancedJs.getId(), cssTag.getId());

        // Модули курса
        final Module modHtmlBase = courseService.addModuleToCourse(basicHtml.getId(), "Базовые элементы HTML", 1);
        final Module modJavascriptAsync = courseService.addModuleToCourse(advancedJs.getId(), "Асинхронность JS", 1);

        // Уроки
        final Lesson lessonTagsAndAttrs = new Lesson(modHtmlBase, "Теги и атрибуты", "Изучаем основные теги и их свойства");
        lessonTagsAndAttrs.setVideoUrl("https://video.example.com/html_tags.mp4");
        lessonRepository.save(lessonTagsAndAttrs);

        // Записываем студентов на курсы
        enrollmentService.enrollStudent(basicHtml.getId(), learner1.getId());
        enrollmentService.enrollStudent(basicHtml.getId(), learner2.getId());
        enrollmentService.enrollStudent(advancedJs.getId(), learner1.getId());

        // Задания
        final Assignment task1 = assignmentService.createAssignment(
                lessonTagsAndAttrs.getId(),
                "Создание простой страницы",
                "Напишите страницу с заголовком и параграфом текста.",
                LocalDate.now().plusWeeks(1),
                80
        );

        // Решение задания студентом
        final Submission sub1 = assignmentService.submitAssignment(task1.getId(), learner1.getId(), "<html><body><h1>Заголовок</h1><p>Это простая страница.</p></body></html>");
        assignmentService.gradeSubmission(sub1.getId(), 75, "Неплохо сделано, немного поправьте отступы.");

        // Тесты
        final Quiz quiz1 = quizService.createQuiz(modJavascriptAsync.getId(), "Проверочный тест по асинхронному JS", 20);

        // Вопрос теста
        final Question qstn1 = quizService.addQuestionToQuiz(quiz1.getId(), "Что такое callback-функция?", QuestionType.SINGLE_CHOICE);

        // Варианты ответов
        quizService.addAnswerOption(qstn1.getId(), "Функция обратного вызова", true);
        quizService.addAnswerOption(qstn1.getId(), "Метод сортировки массива", false);
        quizService.addAnswerOption(qstn1.getId(), "Структура данных", false);

        // Отзывы о курсах
        final CourseReview reviewBasicHtml = new CourseReview(basicHtml, learner1, 5, "Очень полезный курс для новичков!");
        courseReviewRepository.save(reviewBasicHtml);

        final CourseReview reviewAdvJs = new CourseReview(advancedJs, learner1, 4, "Интересный материал, но хотелось бы побольше примеров.");
        courseReviewRepository.save(reviewAdvJs);
    }
}