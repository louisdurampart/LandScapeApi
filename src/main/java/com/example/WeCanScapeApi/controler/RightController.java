package com.example.WeCanScapeApi.controler;

import com.example.WeCanScapeApi.modele.Right;
import com.example.WeCanScapeApi.repository.RightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rights")
public class RightController {

    @Autowired
    private RightRepository rightRepository;

    // Get all rights
    @GetMapping
    public ResponseEntity<List<Right>> getAllRights() {
        List<Right> rights = rightRepository.findAll();
        return new ResponseEntity<>(rights, HttpStatus.OK);
    }

    // Get right by id
    @GetMapping("/{id}")
    public ResponseEntity<Right> getRightById(@PathVariable Integer id) {
        Optional<Right> right = rightRepository.findById(id);
        return right.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Create a new right
    @PostMapping
    public ResponseEntity<Right> createRight(@RequestBody Right right) {
        Right createdRight = rightRepository.save(right);
        return new ResponseEntity<>(createdRight, HttpStatus.CREATED);
    }

    // Update a right
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

    // Delete a right
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
