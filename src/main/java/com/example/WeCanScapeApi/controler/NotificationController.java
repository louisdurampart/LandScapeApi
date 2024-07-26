package com.example.WeCanScapeApi.controler;

import com.example.WeCanScapeApi.modele.Notification;
import com.example.WeCanScapeApi.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Récupérer toutes les notifications
    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    // Récupérer les notifications par ID utilisateur
    @GetMapping("/user/{userId}")
    public List<Notification> getNotificationsByUserId(@PathVariable int userId) {
        return notificationService.getNotificationsByUserId(userId);
    }

    // Récupérer une notification par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable int id) {
        Optional<Notification> notification = notificationService.getNotificationById(id);
        if (notification.isPresent()) {
            return ResponseEntity.ok(notification.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Créer une nouvelle notification
    @PostMapping
    public Notification createNotification(@RequestBody Notification notification) {
        return notificationService.saveNotification(notification);
    }

    // Mettre à jour une notification existante
    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(
            @PathVariable int id, @RequestBody Notification notificationDetails) {
        Optional<Notification> notification = notificationService.getNotificationById(id);
        if (notification.isPresent()) {
            Notification updatedNotification = notification.get();
            // Mettre à jour uniquement le statut de lecture
            updatedNotification.setIsRead(notificationDetails.isIsRead());
            return ResponseEntity.ok(notificationService.saveNotification(updatedNotification));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    

    // Supprimer une notification
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable int id) {
        if (notificationService.deleteNotification(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
