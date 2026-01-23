package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "assignment")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private LocalDate dueDate;

    @Column
    private Integer maxScore;

    @OneToMany(mappedBy = "assignment", fetch = FetchType.LAZY)
    private List<Submission> submissions = new ArrayList<>();

    public Assignment() {
    }

    public Assignment(final Lesson lesson, final String title, final String description, final LocalDate dueDate, final Integer maxScore) {
        this.lesson = lesson;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.maxScore = maxScore;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(final Lesson lesson) {
        this.lesson = lesson;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(final LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(final Integer maxScore) {
        this.maxScore = maxScore;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(final List<Submission> submissions) {
        this.submissions = submissions;
    }
}
