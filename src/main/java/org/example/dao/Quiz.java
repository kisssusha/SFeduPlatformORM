package org.example.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private Module module;

    @Column
    private String title;

    @Column
    private Integer timeLimit;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY)
    private List<QuizSubmission> submissions = new ArrayList<>();

    public Quiz() {
    }

    public Quiz(final Module module, final String title) {
        this.module = module;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Module getCourseModule() {
        return module;
    }

    public void setCourseModule(final Module module) {
        this.module = module;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(final Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(final List<Question> questions) {
        this.questions = questions;
    }

    public List<QuizSubmission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(final List<QuizSubmission> submissions) {
        this.submissions = submissions;
    }
}
