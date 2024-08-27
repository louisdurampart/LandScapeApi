package com.example.WeCanScapeApi.controler;

import com.example.WeCanScapeApi.modele.Category;
import com.example.WeCanScapeApi.repository.CategoryRepository;
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
@RequestMapping("/api/categories")
@Tag(name = "Category", description = "Gestion des catégories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Operation(summary = "Récupérer toutes les catégories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste de toutes les catégories récupérée avec succès")
    })
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Operation(summary = "Récupérer une catégorie par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Catégorie récupérée avec succès"),
            @ApiResponse(responseCode = "404", description = "Catégorie non trouvée")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable int id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return ResponseEntity.ok(category.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Créer une nouvelle catégorie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Catégorie créée avec succès")
    })
    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @Operation(summary = "Mettre à jour une catégorie existante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Catégorie mise à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Catégorie non trouvée")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable int id, @RequestBody Category categoryDetails) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            Category categoryToUpdate = category.get();
            categoryToUpdate.setLabel(categoryDetails.getLabel());
            Category updatedCategory = categoryRepository.save(categoryToUpdate);
            return ResponseEntity.ok(updatedCategory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Supprimer une catégorie par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Catégorie supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = "Catégorie non trouvée")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            categoryRepository.delete(category.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
