package ru.example.eduplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.eduplatform.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
