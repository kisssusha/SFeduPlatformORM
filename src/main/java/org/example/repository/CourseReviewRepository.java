package ru.example.eduplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.eduplatform.entity.CourseReview;

@Repository
public interface CourseReviewRepository extends JpaRepository<CourseReview, Long> {
}
