package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.example.entity.enums.QuestionType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @Column
    private String text;

    @Column
    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AnswerOption> options = new ArrayList<>();

    public Question() {
    }

    public Question(final Quiz quiz, final String text, final QuestionType type) {
        this.quiz = quiz;
        this.text = text;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(final Quiz quiz) {
        this.quiz = quiz;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(final QuestionType type) {
        this.type = type;
    }

    public List<AnswerOption> getOptions() {
        return options;
    }

    public void setOptions(final List<AnswerOption> options) {
        this.options = options;
    }
}
