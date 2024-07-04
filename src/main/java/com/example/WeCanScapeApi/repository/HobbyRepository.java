package com.example.WeCanScapeApi.repository;

import com.example.WeCanScapeApi.modele.Hobby;
import com.example.WeCanScapeApi.modele.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HobbyRepository extends JpaRepository<Hobby, Integer> {
    Optional<Hobby> findByLibelleAndCategory(String libelle, Category category);
}
