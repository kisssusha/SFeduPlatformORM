package ru.example.eduplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.eduplatform.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
