package com.example.WeCanScapeApi.controler;

import com.example.WeCanScapeApi.modele.History;
import com.example.WeCanScapeApi.repository.HistoryRepository;
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
@RequestMapping("/api/histories")
@Tag(name = "History", description = "Gestion des historiques")
public class HistoryController {

    @Autowired
    private HistoryRepository historyRepository;

    @Operation(summary = "Récupérer tous les historiques")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste de tous les historiques récupérée avec succès")
    })
    @GetMapping
    public List<History> getAllHistories() {
        return historyRepository.findAll();
    }

    @Operation(summary = "Récupérer un historique par ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Historique récupéré avec succès"),
        @ApiResponse(responseCode = "404", description = "Historique non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<History> getHistoryById(@PathVariable int id) {
        Optional<History> history = historyRepository.findById(id);
        if (history.isPresent()) {
            return ResponseEntity.ok(history.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Créer un nouvel historique")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Historique créé avec succès")
    })
    @PostMapping
    public History createHistory(@RequestBody History history) {
        return historyRepository.save(history);
    }

    @Operation(summary = "Mettre à jour un historique existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Historique mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Historique non trouvé")
    })
    @PutMapping("/{id}")
    public ResponseEntity<History> updateHistory(@PathVariable int id, @RequestBody History historyDetails) {
        Optional<History> history = historyRepository.findById(id);
        if (history.isPresent()) {
            History historyToUpdate = history.get();
            historyToUpdate.setUser(historyDetails.getUser());
            historyToUpdate.setDate(historyDetails.getDate());
            historyToUpdate.setLaStart(historyDetails.getLaStart());
            historyToUpdate.setLaEnd(historyDetails.getLaEnd());
            historyToUpdate.setLoStart(historyDetails.getLoStart());
            historyToUpdate.setLoEnd(historyDetails.getLoEnd());
            historyToUpdate.setWaypoints(historyDetails.getWaypoints());
            History updatedHistory = historyRepository.save(historyToUpdate);
            return ResponseEntity.ok(updatedHistory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Supprimer un historique par ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Historique supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Historique non trouvé")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistory(@PathVariable int id) {
        Optional<History> history = historyRepository.findById(id);
        if (history.isPresent()) {
            historyRepository.delete(history.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
