package com.example.WeCanScapeApi.repository;

import com.example.WeCanScapeApi.modele.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Optional<Company> findByUserId(Integer userId);
}
