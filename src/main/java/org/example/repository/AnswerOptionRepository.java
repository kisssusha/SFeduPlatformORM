package ru.example.eduplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.eduplatform.entity.AnswerOption;

@Repository
public interface AnswerOptionRepository extends JpaRepository<AnswerOption, Long> {
}
