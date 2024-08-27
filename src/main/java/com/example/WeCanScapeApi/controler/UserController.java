package com.example.WeCanScapeApi.controler;

import com.example.WeCanScapeApi.modele.ApiResult;
import com.example.WeCanScapeApi.modele.Hobby;
import com.example.WeCanScapeApi.modele.User;
import com.example.WeCanScapeApi.modele.UserHobby;
import com.example.WeCanScapeApi.service.UserService;
import com.example.WeCanScapeApi.repository.UserRepository;
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
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Gestion des utilisateurs")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Récupérer tous les utilisateurs")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste de tous les utilisateurs récupérée avec succès")
    })
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Operation(summary = "Récupérer un utilisateur par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur récupéré avec succès"),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @GetMapping("/{uId}")
    public ResponseEntity<User> getUserById(@PathVariable String uId) {
        return userRepository.findByUId(uId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Récupérer les hobbies d'un utilisateur")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des hobbies récupérée avec succès"),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @GetMapping("/{id}/hobbies")
    public ResponseEntity<List<Hobby>> getUserHobbies(@PathVariable Integer id) {
        List<Hobby> hobbies = userService.getHobbiesByUserId(id);
        return ResponseEntity.ok(hobbies);
    }

    @Operation(summary = "Créer un nouvel utilisateur")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès")
    })
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userRepository.save(user);
        return ResponseEntity.status(201).body(createdUser);
    }

    @Operation(summary = "Ajouter un hobby à un utilisateur")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Hobby ajouté avec succès"),
        @ApiResponse(responseCode = "400", description = "Échec de l'ajout du hobby")
    })
    @PostMapping("/hobbies/{userId}/{hobbyId}")
    public ResponseEntity<ApiResult<String>> addHobbyToUser(@PathVariable Integer userId,
            @PathVariable Integer hobbyId) {
        List<Hobby> currentHobbies = userService.getHobbiesByUserId(userId);
        if (currentHobbies.size() >= 3) {
            return ResponseEntity.badRequest()
                    .body(new ApiResult<>(false, "Vous ne pouvez pas ajouter plus de 3 hobbies.", null));
        }

        boolean hobbyExists = currentHobbies.stream().anyMatch(uh -> uh.getId().equals(hobbyId));
        if (hobbyExists) {
            return ResponseEntity.badRequest()
                    .body(new ApiResult<>(false, "Vous possédez déjà ce hobby.", null));
        }

        Optional<UserHobby> userHobby = userService.addHobbyToUser(userId, hobbyId);
        if (userHobby.isPresent()) {
            return ResponseEntity.ok(new ApiResult<>(true, "Hobby associé avec succès.", null));
        } else {
            return ResponseEntity.badRequest()
                    .body(new ApiResult<>(false, "Échec de l'association du hobby avec l'utilisateur.", null));
        }
    }

    @Operation(summary = "Supprimer un hobby d'un utilisateur")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Hobby supprimé avec succès"),
        @ApiResponse(responseCode = "400", description = "Échec de la suppression du hobby")
    })
    @DeleteMapping("/hobbies/{userId}/{hobbyId}")
    public ResponseEntity<ApiResult<String>> deleteUserHobby(@PathVariable Integer userId,
            @PathVariable Integer hobbyId) {
        boolean deleted = userService.deleteUserHobby(userId, hobbyId);
        if (deleted) {
            return ResponseEntity.ok(new ApiResult<>(true, "Hobby supprimé.", null));
        } else {
            return ResponseEntity.badRequest()
                    .body(new ApiResult<>(false, "Échec de la suppression de la liaison utilisateur-hobby.", null));
        }
    }

    @Operation(summary = "Mettre à jour un utilisateur")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Supprimer un utilisateur")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Utilisateur supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
