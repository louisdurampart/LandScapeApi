package com.example.WeCanScapeApi.controler;

import com.example.WeCanScapeApi.modele.Notification;
import com.example.WeCanScapeApi.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
@Tag(name = "Notification", description = "Gestion des notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Operation(summary = "Récupérer toutes les notifications")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste de toutes les notifications récupérée avec succès")
    })
    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @Operation(summary = "Récupérer les notifications par ID utilisateur")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notifications récupérées avec succès"),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @GetMapping("/user/{userId}")
    public List<Notification> getNotificationsByUserId(@PathVariable int userId) {
        return notificationService.getNotificationsByUserId(userId);
    }

    @Operation(summary = "Récupérer une notification par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notification récupérée avec succès"),
        @ApiResponse(responseCode = "404", description = "Notification non trouvée")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable int id) {
        Optional<Notification> notification = notificationService.getNotificationById(id);
        if (notification.isPresent()) {
            return ResponseEntity.ok(notification.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Créer une nouvelle notification")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Notification créée avec succès"),
        @ApiResponse(responseCode = "400", description = "Échec de la création de la notification")
    })
    @PostMapping
    public Notification createNotification(@RequestBody Notification notification) {
        return notificationService.saveNotification(notification);
    }

    @Operation(summary = "Mettre à jour une notification existante")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notification mise à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Notification non trouvée")
    })
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

    @Operation(summary = "Supprimer une notification")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Notification supprimée avec succès"),
        @ApiResponse(responseCode = "404", description = "Notification non trouvée")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable int id) {
        if (notificationService.deleteNotification(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
