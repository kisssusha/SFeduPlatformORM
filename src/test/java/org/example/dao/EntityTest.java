package org.example.dao;

import org.assertj.core.api.Assertions;
import org.example.dao.enums.EnrollmentStatus;
import org.example.dao.enums.QuestionType;
import org.example.dao.enums.UserRole;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class EntityTest {

    @Test
    void shouldVerifyEnrollmentCreation() {
        User user1 = new User("User One", "user1@test.local", UserRole.STUDENT);
        Course course1 = new Course();
        Enrollment enrollment1 = new Enrollment(
                user1,
                course1,
                LocalDate.of(2024, 3, 10),
                EnrollmentStatus.ACTIVE
        );

        assertThat(enrollment1.getStudent()).isEqualTo(user1);
        assertThat(enrollment1.getCourse()).isEqualTo(course1);
        Assertions.assertThat(enrollment1.getStatus()).isEqualTo(EnrollmentStatus.ACTIVE);
    }

    @Test
    void shouldVerifyQuestionCreation() {
        Quiz quiz1 = new Quiz();
        Question question1 = new Question(quiz1, "Question text", QuestionType.SINGLE_CHOICE);

        assertThat(question1.getQuiz()).isEqualTo(quiz1);
        assertThat(question1.getText()).isEqualTo("Question text");
        assertThat(question1.getType()).isEqualTo(QuestionType.SINGLE_CHOICE);
    }

    @Test
    void shouldVerifyModuleCreation() {
        Course course1 = new Course();
        Module module1 = new Module(course1, "Module title", 2);

        assertThat(module1.getCourse()).isEqualTo(course1);
        assertThat(module1.getTitle()).isEqualTo("Module title");
        assertThat(module1.getOrderIndex()).isEqualTo(2);
    }

    @Test
    void shouldVerifyTagCreation() {
        Tag tag1 = new Tag("Tag name");

        assertThat(tag1.getName()).isEqualTo("Tag name");
    }

    @Test
    void shouldVerifyUserProfileCreation() {
        User user1 = new User("User Two", "user2@test.local", UserRole.TEACHER);
        Profile profile1 = new Profile(user1, "Profile bio", "avatar.png");

        assertThat(profile1.getUser()).isEqualTo(user1);
        assertThat(profile1.getBio()).isEqualTo("Profile bio");
        assertThat(profile1.getAvatarUrl()).isEqualTo("avatar.png");
    }

    @Test
    void shouldVerifyAnswerOptionCreation() {
        Question question1 = new Question();
        AnswerOption option1 = new AnswerOption(question1, "Option text", true);

        assertThat(option1.getQuestion()).isEqualTo(question1);
        assertThat(option1.getText()).isEqualTo("Option text");
        assertThat(option1.isCorrect()).isTrue();
    }

    @Test
    void shouldVerifyCourseReviewCreation() {
        Course course1 = new Course();
        User user1 = new User();
        CourseReview review1 = new CourseReview(course1, user1, 4, "Review text");

        assertThat(review1.getCourse()).isEqualTo(course1);
        assertThat(review1.getStudent()).isEqualTo(user1);
        assertThat(review1.getRating()).isEqualTo(4);
        assertThat(review1.getComment()).isEqualTo("Review text");
    }

    @Test
    void shouldVerifyQuizCreation() {
        Module module1 = new Module();
        Quiz quiz1 = new Quiz(module1, "Quiz title");

        assertThat(quiz1.getCourseModule()).isEqualTo(module1);
        assertThat(quiz1.getTitle()).isEqualTo("Quiz title");
    }

    @Test
    void shouldVerifyAssignmentCreation() {
        Lesson lesson1 = new Lesson();
        Assignment assignment1 = new Assignment(
                lesson1,
                "Assignment title",
                "Assignment description",
                LocalDate.of(2024, 3, 20),
                50
        );

        assertThat(assignment1.getLesson()).isEqualTo(lesson1);
        assertThat(assignment1.getTitle()).isEqualTo("Assignment title");
        assertThat(assignment1.getMaxScore()).isEqualTo(50);
    }

    @Test
    void shouldVerifyCategoryCreation() {
        Category category1 = new Category("Category name");

        assertThat(category1.getName()).isEqualTo("Category name");
    }

    @Test
    void shouldVerifySubmissionCreation() {
        Assignment assignment1 = new Assignment();
        User user1 = new User();
        Submission submission1 = new Submission(assignment1, user1, "Submission content");

        assertThat(submission1.getAssignment()).isEqualTo(assignment1);
        assertThat(submission1.getStudent()).isEqualTo(user1);
        assertThat(submission1.getContent()).isEqualTo("Submission content");
    }

    @Test
    void shouldVerifyUserCreation() {
        User user1 = new User("User Three", "user3@test.local", UserRole.TEACHER);

        assertThat(user1.getName()).isEqualTo("User Three");
        assertThat(user1.getEmail()).isEqualTo("user3@test.local");
        assertThat(user1.getRole()).isEqualTo(UserRole.TEACHER);
    }

    @Test
    void shouldVerifyLessonCreation() {
        Module module1 = new Module();
        Lesson lesson1 = new Lesson(module1, "Lesson title", "Lesson content");

        assertThat(lesson1.getCourseModule()).isEqualTo(module1);
        assertThat(lesson1.getTitle()).isEqualTo("Lesson title");
        assertThat(lesson1.getContent()).isEqualTo("Lesson content");
    }

    @Test
    void shouldVerifyQuizSubmissionCreation() {
        Quiz quiz1 = new Quiz();
        User user1 = new User();
        QuizSubmission submission1 = new QuizSubmission(quiz1, user1, 78);

        assertThat(submission1.getQuiz()).isEqualTo(quiz1);
        assertThat(submission1.getStudent()).isEqualTo(user1);
        assertThat(submission1.getScore()).isEqualTo(78);
    }

    @Test
    void shouldVerifyCourseCreation() {
        User user1 = new User("User Four", "user4@test.local", UserRole.TEACHER);
        Category category1 = new Category("Category name");
        Course course1 = new Course(
                "Course title",
                "Course description",
                category1,
                user1
        );

        assertThat(course1.getTitle()).isEqualTo("Course title");
        assertThat(course1.getDescription()).isEqualTo("Course description");
        assertThat(course1.getCategory()).isEqualTo(category1);
        assertThat(course1.getTeacher()).isEqualTo(user1);
    }
}
