package com.example.WeCanScapeApi.repository;

import com.example.WeCanScapeApi.modele.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
