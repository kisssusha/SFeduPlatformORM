package org.example.repository;

import org.example.dao.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    @Query("""
                select q
                from Quiz q
                where q.courseModule.id = :moduleId
            """)
    Optional<Quiz> findByModuleId(@Param("moduleId") Long moduleId);

}
