package com.example.WeCanScapeApi.controler;

import com.example.WeCanScapeApi.modele.UserHobby;
import com.example.WeCanScapeApi.repository.UserHobbyRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-hobbies")
@Tag(name = "UserHobbies", description = "Gestion des hobbies des utilisateurs")
public class UserHobbyController {

    @Autowired
    private UserHobbyRepository userHobbyRepository;

    @Operation(summary = "Récupérer tous les hobbies des utilisateurs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste de tous les hobbies des utilisateurs récupérée avec succès")
    })
    @GetMapping
    public List<UserHobby> getAllUserHobbies() {
        return userHobbyRepository.findAll();
    }

    @Operation(summary = "Récupérer un hobby d'utilisateur par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hobby de l'utilisateur récupéré avec succès"),
            @ApiResponse(responseCode = "404", description = "Hobby de l'utilisateur non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserHobby> getUserHobbyById(@PathVariable Integer id) {
        return userHobbyRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Créer un nouveau hobby pour un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Hobby de l'utilisateur créé avec succès")
    })
    @PostMapping
    public ResponseEntity<UserHobby> createUserHobby(@RequestBody UserHobby userHobby) {
        UserHobby createdUserHobby = userHobbyRepository.save(userHobby);
        return ResponseEntity.status(201).body(createdUserHobby);
    }

    @Operation(summary = "Mettre à jour un hobby d'utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hobby de l'utilisateur mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Hobby de l'utilisateur non trouvé")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserHobby> updateUserHobby(@PathVariable Integer id, @RequestBody UserHobby userHobby) {
        if (userHobbyRepository.existsById(id)) {
            userHobby.setId(id);
            UserHobby updatedUserHobby = userHobbyRepository.save(userHobby);
            return ResponseEntity.ok(updatedUserHobby);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Supprimer un hobby d'utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Hobby de l'utilisateur supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Hobby de l'utilisateur non trouvé")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserHobby(@PathVariable Integer id) {
        if (userHobbyRepository.existsById(id)) {
            userHobbyRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
