package org.example.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.example.entity.enums.EnrollmentStatus;
import org.example.entity.enums.QuestionType;
import org.example.entity.enums.UserRole;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class EntityTest {

    @Test
    void shouldVerifyEnrollmentCreation() {
        User learner = new User("Emily Johnson", "emily@study.org", UserRole.STUDENT);
        Course programmingCourse = new Course();
        Enrollment courseEnrollment = new Enrollment(learner, programmingCourse,
                LocalDate.of(2024, 3, 10), EnrollmentStatus.ACTIVE);

        assertThat(courseEnrollment.getStudent()).isEqualTo(learner);
        assertThat(courseEnrollment.getCourse()).isEqualTo(programmingCourse);
        Assertions.assertThat(courseEnrollment.getStatus()).isEqualTo(EnrollmentStatus.ACTIVE);
    }

    @Test
    void shouldVerifyQuestionCreation() {
        Quiz knowledgeTest = new Quiz();
        Question testQuestion = new Question(knowledgeTest, "Explain polymorphism", QuestionType.SINGLE_CHOICE);

        assertThat(testQuestion.getQuiz()).isEqualTo(knowledgeTest);
        assertThat(testQuestion.getText()).isEqualTo("Explain polymorphism");
        assertThat(testQuestion.getType()).isEqualTo(QuestionType.SINGLE_CHOICE);
    }

    @Test
    void shouldVerifyModuleCreation() {
        Course webDevelopment = new Course();
        Module frontendModule = new Module(webDevelopment, "Frontend Technologies", 2);

        assertThat(frontendModule.getCourse()).isEqualTo(webDevelopment);
        assertThat(frontendModule.getTitle()).isEqualTo("Frontend Technologies");
        assertThat(frontendModule.getOrderIndex()).isEqualTo(2);
    }

    @Test
    void shouldVerifyTagCreation() {
        Tag programmingTag = new Tag("Python");

        assertThat(programmingTag.getName()).isEqualTo("Python");
    }

    @Test
    void shouldVerifyUserProfileCreation() {
        User account = new User("Michael Brown", "michael@mail.com", UserRole.TEACHER);
        Profile userProfile = new Profile(account, "Experienced developer", "photo.png");

        assertThat(userProfile.getUser()).isEqualTo(account);
        assertThat(userProfile.getBio()).isEqualTo("Experienced developer");
        assertThat(userProfile.getAvatarUrl()).isEqualTo("photo.png");
    }

    @Test
    void shouldVerifyAnswerOptionCreation() {
        Question examQuestion = new Question();
        AnswerOption correctAnswer = new AnswerOption(examQuestion, "Correct answer", true);

        assertThat(correctAnswer.getQuestion()).isEqualTo(examQuestion);
        assertThat(correctAnswer.getText()).isEqualTo("Correct answer");
        assertThat(correctAnswer.isCorrect()).isTrue();
    }

    @Test
    void shouldVerifyCourseReviewCreation() {
        Course dataScience = new Course();
        User reviewer = new User();
        CourseReview feedback = new CourseReview(dataScience, reviewer, 4, "Well structured content");

        assertThat(feedback.getCourse()).isEqualTo(dataScience);
        assertThat(feedback.getStudent()).isEqualTo(reviewer);
        assertThat(feedback.getRating()).isEqualTo(4);
        assertThat(feedback.getComment()).isEqualTo("Well structured content");
    }

    @Test
    void shouldVerifyQuizCreation() {
        Module algorithmsModule = new Module();
        Quiz moduleQuiz = new Quiz(algorithmsModule, "Algorithms Test");

        assertThat(moduleQuiz.getCourseModule()).isEqualTo(algorithmsModule);
        assertThat(moduleQuiz.getTitle()).isEqualTo("Algorithms Test");
    }

    @Test
    void shouldVerifyAssignmentCreation() {
        Lesson programmingLesson = new Lesson();
        Assignment codingTask = new Assignment(programmingLesson, "Implement sorting",
                "Write bubble sort algorithm",
                LocalDate.of(2024, 3, 20), 50);

        assertThat(codingTask.getLesson()).isEqualTo(programmingLesson);
        assertThat(codingTask.getTitle()).isEqualTo("Implement sorting");
        assertThat(codingTask.getMaxScore()).isEqualTo(50);
    }

    @Test
    void shouldVerifyCategoryCreation() {
        Category development = new Category("Web Development");

        assertThat(development.getName()).isEqualTo("Web Development");
    }

    @Test
    void shouldVerifySubmissionCreation() {
        Assignment homework = new Assignment();
        User participant = new User();
        Submission solution = new Submission(homework, participant, "My implementation");

        assertThat(solution.getAssignment()).isEqualTo(homework);
        assertThat(solution.getStudent()).isEqualTo(participant);
        assertThat(solution.getContent()).isEqualTo("My implementation");
    }

    @Test
    void shouldVerifyUserCreation() {
        User instructor = new User("Dr. Wilson", "wilson@university.edu", UserRole.TEACHER);

        assertThat(instructor.getName()).isEqualTo("Dr. Wilson");
        assertThat(instructor.getEmail()).isEqualTo("wilson@university.edu");
        assertThat(instructor.getRole()).isEqualTo(UserRole.TEACHER);
    }

    @Test
    void shouldVerifyLessonCreation() {
        Module databaseModule = new Module();
        Lesson sqlLesson = new Lesson(databaseModule, "SQL Basics", "Database fundamentals");

        assertThat(sqlLesson.getCourseModule()).isEqualTo(databaseModule);
        assertThat(sqlLesson.getTitle()).isEqualTo("SQL Basics");
        assertThat(sqlLesson.getContent()).isEqualTo("Database fundamentals");
    }

    @Test
    void shouldVerifyQuizSubmissionCreation() {
        Quiz finalExam = new Quiz();
        User examinee = new User();
        QuizSubmission examResult = new QuizSubmission(finalExam, examinee, 78);

        assertThat(examResult.getQuiz()).isEqualTo(finalExam);
        assertThat(examResult.getStudent()).isEqualTo(examinee);
        assertThat(examResult.getScore()).isEqualTo(78);
    }

    @Test
    void shouldVerifyCourseCreation() {
        User professor = new User("Prof. Davis", "davis@college.org", UserRole.TEACHER);
        Category computerScience = new Category("Computer Science");
        Course algorithmsCourse = new Course("Algorithm Design",
                "Advanced algorithm techniques",
                computerScience, professor);

        assertThat(algorithmsCourse.getTitle()).isEqualTo("Algorithm Design");
        assertThat(algorithmsCourse.getDescription()).isEqualTo("Advanced algorithm techniques");
        assertThat(algorithmsCourse.getCategory()).isEqualTo(computerScience);
        assertThat(algorithmsCourse.getTeacher()).isEqualTo(professor);
    }
}