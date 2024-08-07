package com.example.WeCanScapeApi.repository;

import com.example.WeCanScapeApi.modele.Poi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoiRepository extends JpaRepository<Poi, Integer> {
    List<Poi> findByCompanyId(Integer companyId);
}
