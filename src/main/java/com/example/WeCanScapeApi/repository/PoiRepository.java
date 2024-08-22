package com.example.WeCanScapeApi.repository;

import com.example.WeCanScapeApi.modele.Poi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoiRepository extends JpaRepository<Poi, Integer> {
    List<Poi> findByCompanyId(Integer companyId);

    @Query(value = "SELECT * FROM Poi p WHERE " +
            "(6371 * acos(cos(radians(:lat)) * cos(radians(CAST(p.lat AS double precision))) * " +
            "cos(radians(CAST(p.lon AS double precision)) - radians(:lon)) + " +
            "sin(radians(:lat)) * sin(radians(CAST(p.lat AS double precision))))) < :radius", nativeQuery = true)
    List<Poi> findPoisWithinRadius(@Param("lat") double latitude,
            @Param("lon") double longitude,
            @Param("radius") double radius);

}
