package com.example.WeCanScapeApi.repository;

import com.example.WeCanScapeApi.modele.Right;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RightRepository extends JpaRepository<Right, Integer> {
	Optional<Right> findByLabel(String label);
}
