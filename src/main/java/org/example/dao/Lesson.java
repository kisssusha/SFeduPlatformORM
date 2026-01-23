package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lesson")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private Module module;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String videoUrl;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Assignment> assignments = new ArrayList<>();

    public Lesson() {
    }

    public Lesson(final Module module, final String title, final String content) {
        this.module = module;
        this.title = title;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(final String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(final List<Assignment> assignments) {
        this.assignments = assignments;
    }
}
