package com.example.WeCanScapeApi.controler;

import com.example.WeCanScapeApi.modele.Right;
import com.example.WeCanScapeApi.repository.RightRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rights")
@Tag(name = "Rights", description = "Gestion des droits")
public class RightController {

    @Autowired
    private RightRepository rightRepository;

    @Operation(summary = "Récupérer tous les droits")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste de tous les droits récupérée avec succès")
    })
    @GetMapping
    public ResponseEntity<List<Right>> getAllRights() {
        List<Right> rights = rightRepository.findAll();
        return new ResponseEntity<>(rights, HttpStatus.OK);
    }

    @Operation(summary = "Récupérer un droit par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Droit récupéré avec succès"),
        @ApiResponse(responseCode = "404", description = "Droit non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Right> getRightById(@PathVariable Integer id) {
        Optional<Right> right = rightRepository.findById(id);
        return right.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Créer un nouveau droit")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Droit créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Échec de la création du droit")
    })
    @PostMapping
    public ResponseEntity<Right> createRight(@RequestBody Right right) {
        Right createdRight = rightRepository.save(right);
        return new ResponseEntity<>(createdRight, HttpStatus.CREATED);
    }

    @Operation(summary = "Mettre à jour un droit existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Droit mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Droit non trouvé")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Right> updateRight(@PathVariable Integer id, @RequestBody Right rightDetails) {
        Optional<Right> right = rightRepository.findById(id);
        if (right.isPresent()) {
            Right updatedRight = right.get();
            updatedRight.setLabel(rightDetails.getLabel());
            rightRepository.save(updatedRight);
            return new ResponseEntity<>(updatedRight, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Supprimer un droit")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Droit supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Droit non trouvé")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRight(@PathVariable Integer id) {
        Optional<Right> right = rightRepository.findById(id);
        if (right.isPresent()) {
            rightRepository.delete(right.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
