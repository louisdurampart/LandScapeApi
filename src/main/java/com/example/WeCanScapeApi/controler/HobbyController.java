package com.example.WeCanScapeApi.controler;

import com.example.WeCanScapeApi.DTO.GetHobbyDTO;
import com.example.WeCanScapeApi.DTO.HobbyDTO;
import com.example.WeCanScapeApi.modele.Hobby;
import com.example.WeCanScapeApi.modele.ApiResponse;
import com.example.WeCanScapeApi.modele.Category;
import com.example.WeCanScapeApi.repository.CategoryRepository;
import com.example.WeCanScapeApi.repository.HobbyRepository;
import com.example.WeCanScapeApi.service.HobbyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hobbies")
public class HobbyController {

    @Autowired
    private HobbyRepository hobbyRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private HobbyService hobbyService;

    @GetMapping
    public List<GetHobbyDTO> getAllHobbies() {
        return hobbyService.findAllHobbies();
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
    public ResponseEntity<ApiResponse<HobbyDTO>> createHobby(@RequestBody HobbyDTO hobbyDTO) {
        try {
            Category category = categoryRepository.findById(hobbyDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            Hobby hobby = new Hobby();
            hobby.setLabel(hobbyDTO.getLabel());
            hobby.setCategory(category);

            hobbyRepository.save(hobby);
            return ResponseEntity.ok(new ApiResponse<>(true, "Hobby créé avec succès.", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Échec de la création du hobby. " + e.getMessage(), null));
        }
    }

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