package ru.example.eduplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.eduplatform.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
