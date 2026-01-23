package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.example.entity.enums.UserRole;

import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Email
    @Column(unique = true)
    private String email;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Course> coursesTaught;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Enrollment> enrollments;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Profile profile;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Submission> submissions;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<QuizSubmission> quizSubmissions;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<CourseReview> reviews;

    public User() {
    }

    public User(final String name, final String email, final UserRole role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(final UserRole role) {
        this.role = role;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(final Profile profile) {
        this.profile = profile;
    }

    public List<Course> getCoursesTaught() {
        return coursesTaught;
    }

    public void setCoursesTaught(final List<Course> taughtCourses) {
        this.coursesTaught = taughtCourses;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(final List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(final List<Submission> submissions) {
        this.submissions = submissions;
    }

    public List<QuizSubmission> getQuizSubmissions() {
        return quizSubmissions;
    }

    public void setQuizSubmissions(final List<QuizSubmission> quizSubmissions) {
        this.quizSubmissions = quizSubmissions;
    }

    public List<CourseReview> getReviews() {
        return reviews;
    }

    public void setReviews(final List<CourseReview> reviews) {
        this.reviews = reviews;
    }
}
