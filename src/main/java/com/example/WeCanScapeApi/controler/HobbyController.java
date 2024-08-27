package com.example.WeCanScapeApi.controler;

import com.example.WeCanScapeApi.DTO.GetHobbyDTO;
import com.example.WeCanScapeApi.DTO.HobbyDTO;
import com.example.WeCanScapeApi.modele.Hobby;
import com.example.WeCanScapeApi.modele.ApiResult;
import com.example.WeCanScapeApi.modele.Category;
import com.example.WeCanScapeApi.repository.CategoryRepository;
import com.example.WeCanScapeApi.repository.HobbyRepository;
import com.example.WeCanScapeApi.service.HobbyService;
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
@RequestMapping("/api/hobbies")
@Tag(name = "Hobby", description = "Gestion des hobbies")
public class HobbyController {

    @Autowired
    private HobbyRepository hobbyRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private HobbyService hobbyService;

    @Operation(summary = "Récupérer tous les hobbies")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste de tous les hobbies récupérée avec succès")
    })
    @GetMapping
    public List<GetHobbyDTO> getAllHobbies() {
        return hobbyService.findAllHobbies();
    }

    @Operation(summary = "Récupérer un hobby par ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Hobby récupéré avec succès"),
        @ApiResponse(responseCode = "404", description = "Hobby non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Hobby> getHobbyById(@PathVariable Integer id) {
        Optional<Hobby> hobby = hobbyRepository.findById(id);
        if (hobby.isPresent()) {
            return ResponseEntity.ok(hobby.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Créer un nouveau hobby")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Hobby créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Échec de la création du hobby")
    })
    @PostMapping
    public ResponseEntity<ApiResult<HobbyDTO>> createHobby(@RequestBody HobbyDTO hobbyDTO) {
        try {
            Category category = categoryRepository.findById(hobbyDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            Hobby hobby = new Hobby();
            hobby.setLabel(hobbyDTO.getLabel());
            hobby.setCategory(category);

            hobbyRepository.save(hobby);
            return ResponseEntity.ok(new ApiResult<>(true, "Hobby créé avec succès.", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResult<>(false, "Échec de la création du hobby. " + e.getMessage(), null));
        }
    }

    @Operation(summary = "Mettre à jour un hobby existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Hobby mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Hobby non trouvé")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Hobby> updateHobby(@PathVariable Integer id, @RequestBody Hobby hobbyDetails) {
        Optional<Hobby> hobby = hobbyRepository.findById(id);
        if (hobby.isPresent()) {
            Hobby hobbyToUpdate = hobby.get();
            hobbyToUpdate.setLabel(hobbyDetails.getLabel());
            hobbyToUpdate.setCategory(hobbyDetails.getCategory());
            Hobby updatedHobby = hobbyRepository.save(hobbyToUpdate);
            return ResponseEntity.ok(updatedHobby);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Supprimer un hobby par ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Hobby supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Hobby non trouvé")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHobby(@PathVariable Integer id) {
        Optional<Hobby> hobby = hobbyRepository.findById(id);
        if (hobby.isPresent()) {
            hobbyRepository.delete(hobby.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
