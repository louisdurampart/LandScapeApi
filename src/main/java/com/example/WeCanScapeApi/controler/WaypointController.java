package com.example.WeCanScapeApi.controler;

import com.example.WeCanScapeApi.modele.Waypoint;
import com.example.WeCanScapeApi.repository.WaypointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/waypoints")
public class WaypointController {

    @Autowired
    private WaypointRepository waypointRepository;

    // Get all waypoints
    @GetMapping
    public ResponseEntity<List<Waypoint>> getAllWaypoints() {
        List<Waypoint> waypoints = waypointRepository.findAll();
        return new ResponseEntity<>(waypoints, HttpStatus.OK);
    }

    // Get waypoint by id
    @GetMapping("/{id}")
    public ResponseEntity<Waypoint> getWaypointById(@PathVariable Integer id) {
        Optional<Waypoint> waypoint = waypointRepository.findById(id);
        return waypoint.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Create a new waypoint
    @PostMapping
    public ResponseEntity<Waypoint> createWaypoint(@RequestBody Waypoint waypoint) {
        Waypoint createdWaypoint = waypointRepository.save(waypoint);
        return new ResponseEntity<>(createdWaypoint, HttpStatus.CREATED);
    }

    // Update a waypoint
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
            return new ResponseEntity<>(updatedWaypoint, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a waypoint
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWaypoint(@PathVariable Integer id) {
        Optional<Waypoint> waypoint = waypointRepository.findById(id);
        if (waypoint.isPresent()) {
            waypointRepository.delete(waypoint.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
