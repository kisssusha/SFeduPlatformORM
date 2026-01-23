package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.example.entity.enums.EnrollmentStatus;

import java.time.LocalDate;

@Entity
@Table(name = "enrollment")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User student;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column
    private LocalDate enrollDate;

    @Column
    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

    public Enrollment() {
    }

    public Enrollment(final User student, final Course course, final LocalDate enrollDate, final EnrollmentStatus status) {
        this.student = student;
        this.course = course;
        this.enrollDate = enrollDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(final User student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(final Course course) {
        this.course = course;
    }

    public LocalDate getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(final LocalDate enrollDate) {
        this.enrollDate = enrollDate;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(final EnrollmentStatus status) {
        this.status = status;
    }
}
