package com.example.WeCanScapeApi.repository;

import com.example.WeCanScapeApi.modele.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {
    // Vous pouvez ajouter des méthodes de requête personnalisées ici si nécessaire
}
