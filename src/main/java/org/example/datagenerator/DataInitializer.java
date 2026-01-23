package org.example.datagenerator;

import org.example.dao.*;
import org.example.dao.Module;
import org.example.dao.enums.QuestionType;
import org.example.dao.enums.UserRole;
import org.example.repository.*;
import org.example.service.AssignmentService;
import org.example.service.CourseService;
import org.example.service.EnrollmentService;
import org.example.service.QuizService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@org.springframework.context.annotation.Profile("!test")
public class DataInitializer implements CommandLineRunner {

    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final AssignmentService assignmentService;
    private final QuizService quizService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final ProfileRepository profileRepository;
    private final CourseReviewRepository courseReviewRepository;
    private final LessonRepository lessonRepository;

    public DataInitializer(
            final CourseService courseService,
            final EnrollmentService enrollmentService,
            final AssignmentService assignmentService,
            final QuizService quizService,
            final UserRepository userRepository,
            final CategoryRepository categoryRepository,
            final TagRepository tagRepository,
            final ProfileRepository profileRepository,
            final CourseReviewRepository courseReviewRepository,
            final LessonRepository lessonRepository
    ) {
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
    public void run(String... args) {
        initializeData();
    }

    private void initializeData() {

        final User userTeacher = userRepository.save(
                new User("User Teacher", "teacher@test.local", UserRole.TEACHER)
        );
        final User userStudent1 = userRepository.save(
                new User("User Student One", "student1@test.local", UserRole.STUDENT)
        );
        final User userStudent2 = userRepository.save(
                new User("User Student Two", "student2@test.local", UserRole.STUDENT)
        );

        final Profile profileTeacher = new Profile(userTeacher, "Profile description", "avatar_teacher.png");
        final Profile profileStudent1 = new Profile(userStudent1, "Profile description", "avatar_student1.png");
        profileRepository.saveAll(List.of(profileTeacher, profileStudent1));

        final Category category1 = categoryRepository.save(new Category("Category A"));

        final Tag tag1 = tagRepository.save(new Tag("Tag A"));
        final Tag tag2 = tagRepository.save(new Tag("Tag B"));
        final Tag tag3 = tagRepository.save(new Tag("Tag C"));

        final Course course1 = courseService.createCourse(
                "Course Title A",
                "Course Description A",
                category1.getId(),
                userTeacher.getId()
        );
        course1.setStartDate(LocalDate.now().minusMonths(2));

        final Course course2 = courseService.createCourse(
                "Course Title B",
                "Course Description B",
                category1.getId(),
                userTeacher.getId()
        );
        course2.setStartDate(LocalDate.now());

        courseService.addTagToCourse(course1.getId(), tag1.getId());
        courseService.addTagToCourse(course2.getId(), tag2.getId());
        courseService.addTagToCourse(course2.getId(), tag3.getId());

        final Module module1 = courseService.addModuleToCourse(course1.getId(), "Module Title A", 1);
        final Module module2 = courseService.addModuleToCourse(course2.getId(), "Module Title B", 1);

        final Lesson lesson1 = new Lesson(
                module1,
                "Lesson Title A",
                "Lesson Content A"
        );
        lesson1.setVideoUrl("https://video.example.com/video_a.mp4");
        lessonRepository.save(lesson1);

        enrollmentService.enrollStudent(course1.getId(), userStudent1.getId());
        enrollmentService.enrollStudent(course1.getId(), userStudent2.getId());
        enrollmentService.enrollStudent(course2.getId(), userStudent1.getId());

        final Assignment assignment1 = assignmentService.createAssignment(
                lesson1.getId(),
                "Assignment Title A",
                "Assignment Description A",
                LocalDate.now().plusWeeks(1),
                80
        );

        final Submission submission1 = assignmentService.submitAssignment(
                assignment1.getId(),
                userStudent1.getId(),
                "<html><body><p>Sample content</p></body></html>"
        );
        assignmentService.gradeSubmission(submission1.getId(), 75, "Feedback text");

        final Quiz quiz1 = quizService.createQuiz(
                module2.getId(),
                "Quiz Title A",
                20
        );

        final Question question1 = quizService.addQuestionToQuiz(
                quiz1.getId(),
                "Question text",
                QuestionType.SINGLE_CHOICE
        );

        quizService.addAnswerOption(question1.getId(), "Option A", true);
        quizService.addAnswerOption(question1.getId(), "Option B", false);
        quizService.addAnswerOption(question1.getId(), "Option C", false);

        final CourseReview review1 = new CourseReview(course1, userStudent1, 5, "Review text A");
        courseReviewRepository.save(review1);

        final CourseReview review2 = new CourseReview(course2, userStudent1, 4, "Review text B");
        courseReviewRepository.save(review2);
    }
}
