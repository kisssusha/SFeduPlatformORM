package ru.example.eduplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.eduplatform.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
