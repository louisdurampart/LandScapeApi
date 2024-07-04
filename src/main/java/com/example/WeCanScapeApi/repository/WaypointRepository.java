package com.example.WeCanScapeApi.repository;

import com.example.WeCanScapeApi.modele.Waypoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaypointRepository extends JpaRepository<Waypoint, Integer> {
}
