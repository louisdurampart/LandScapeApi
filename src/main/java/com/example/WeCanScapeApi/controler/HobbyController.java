package com.example.WeCanScapeApi.controler;

import com.example.WeCanScapeApi.modele.Hobby;
import com.example.WeCanScapeApi.repository.HobbyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hobbies")
public class HobbyController {

    @Autowired
    private HobbyRepository hobbyRepository;

    @GetMapping
    public List<Hobby> getAllHobbies() {
        return hobbyRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hobby> getHobbyById(@PathVariable Integer id) {
        Optional<Hobby> hobby = hobbyRepository.findById(id);
        if (hobby.isPresent()) {
            return ResponseEntity.ok(hobby.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Hobby createHobby(@RequestBody Hobby hobby) {
        return hobbyRepository.save(hobby);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hobby> updateHobby(@PathVariable Integer id, @RequestBody Hobby hobbyDetails) {
        Optional<Hobby> hobby = hobbyRepository.findById(id);
        if (hobby.isPresent()) {
            Hobby hobbyToUpdate = hobby.get();
            hobbyToUpdate.setLibelle(hobbyDetails.getLibelle());
            hobbyToUpdate.setCategory(hobbyDetails.getCategory());
            Hobby updatedHobby = hobbyRepository.save(hobbyToUpdate);
            return ResponseEntity.ok(updatedHobby);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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
