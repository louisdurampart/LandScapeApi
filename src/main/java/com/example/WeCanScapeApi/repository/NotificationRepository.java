package com.example.WeCanScapeApi.repository;

import com.example.WeCanScapeApi.modele.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUserId(int userId);
    List<Notification> findByIsRead(Boolean isRead);
}
