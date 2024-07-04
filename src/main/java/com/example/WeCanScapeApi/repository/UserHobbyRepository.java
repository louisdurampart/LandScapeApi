package com.example.WeCanScapeApi.repository;

import com.example.WeCanScapeApi.modele.UserHobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHobbyRepository extends JpaRepository<UserHobby, Integer> {
    // Aucune méthode supplémentaire n'est nécessaire ici
}
