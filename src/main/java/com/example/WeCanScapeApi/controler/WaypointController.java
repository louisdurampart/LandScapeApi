package com.example.WeCanScapeApi.controler;

import com.example.WeCanScapeApi.modele.Waypoint;
import com.example.WeCanScapeApi.repository.WaypointRepository;
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
@RequestMapping("/api/waypoints")
@Tag(name = "Waypoints", description = "Gestion des waypoints")
public class WaypointController {

    @Autowired
    private WaypointRepository waypointRepository;

    @Operation(summary = "Récupérer tous les waypoints")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste de tous les waypoints récupérée avec succès")
    })
    @GetMapping
    public ResponseEntity<List<Waypoint>> getAllWaypoints() {
        List<Waypoint> waypoints = waypointRepository.findAll();
        return ResponseEntity.ok(waypoints);
    }

    @Operation(summary = "Récupérer un waypoint par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Waypoint récupéré avec succès"),
            @ApiResponse(responseCode = "404", description = "Waypoint non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Waypoint> getWaypointById(@PathVariable Integer id) {
        Optional<Waypoint> waypoint = waypointRepository.findById(id);
        return waypoint.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Créer un nouveau waypoint")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Waypoint créé avec succès")
    })
    @PostMapping
    public ResponseEntity<Waypoint> createWaypoint(@RequestBody Waypoint waypoint) {
        Waypoint createdWaypoint = waypointRepository.save(waypoint);
        return ResponseEntity.status(201).body(createdWaypoint);
    }

    @Operation(summary = "Mettre à jour un waypoint")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Waypoint mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Waypoint non trouvé")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Waypoint> updateWaypoint(@PathVariable Integer id, @RequestBody Waypoint waypointDetails) {
        Optional<Waypoint> waypoint = waypointRepository.findById(id);
        if (waypoint.isPresent()) {
            Waypoint updatedWaypoint = waypoint.get();
            updatedWaypoint.setNumberWaypoint(waypointDetails.getNumberWaypoint());
            updatedWaypoint.setLa(waypointDetails.getLa());
            updatedWaypoint.setLo(waypointDetails.getLo());
            updatedWaypoint.setHistory(waypointDetails.getHistory());
            waypointRepository.save(updatedWaypoint);
            return ResponseEntity.ok(updatedWaypoint);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Supprimer un waypoint")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Waypoint supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Waypoint non trouvé")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWaypoint(@PathVariable Integer id) {
        Optional<Waypoint> waypoint = waypointRepository.findById(id);
        if (waypoint.isPresent()) {
            waypointRepository.delete(waypoint.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
