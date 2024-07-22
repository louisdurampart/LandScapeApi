package com.example.WeCanScapeApi.controler;

import com.example.WeCanScapeApi.modele.History;
import com.example.WeCanScapeApi.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/histories")
public class HistoryController {

    @Autowired
    private HistoryRepository historyRepository;

    @GetMapping
    public List<History> getAllHistories() {
        return historyRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<History> getHistoryById(@PathVariable int id) {
        Optional<History> history = historyRepository.findById(id);
        if (history.isPresent()) {
            return ResponseEntity.ok(history.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public History createHistory(@RequestBody History history) {
        return historyRepository.save(history);
    }

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
