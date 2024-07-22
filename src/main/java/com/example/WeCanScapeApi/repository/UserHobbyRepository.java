package com.example.WeCanScapeApi.repository;

import com.example.WeCanScapeApi.modele.UserHobby;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHobbyRepository extends JpaRepository<UserHobby, Integer> {
	List<UserHobby> findByUserId(Integer userId);
	Optional<UserHobby> findByUserIdAndHobbyId(Integer userId, Integer hobbyId);
}
