package com.example.WeCanScapeApi.repository;

import com.example.WeCanScapeApi.modele.Commerce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommerceRepository extends JpaRepository<Commerce, Integer> {
    List<Commerce> findByEntrepriseId(Integer entrepriseId);
}
