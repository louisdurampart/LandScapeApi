package com.example.WeCanScapeApi.repository;

import com.example.WeCanScapeApi.modele.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByLabel(String label);
}
