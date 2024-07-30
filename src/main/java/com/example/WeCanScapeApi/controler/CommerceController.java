package com.example.WeCanScapeApi.controler;

import com.example.WeCanScapeApi.modele.Commerce;
import com.example.WeCanScapeApi.repository.CommerceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/commerces")
public class CommerceController {
    @Autowired
    private CommerceRepository commerceRepository;

    @GetMapping
    public List<Commerce> getAllCommerces() {
        return commerceRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commerce> getCommerceById(@PathVariable Integer id) {
        Optional<Commerce> commerce = commerceRepository.findById(id);
        return commerce.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/entreprise/{entrepriseId}")
    public List<Commerce> getCommercesByEntreprise(@PathVariable Integer entrepriseId) {
        return commerceRepository.findByEntrepriseId(entrepriseId);
    }

    @PostMapping
    public Commerce createCommerce(@RequestBody Commerce commerce) {
        return commerceRepository.save(commerce);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Commerce> updateCommerce(@PathVariable Integer id, @RequestBody Commerce commerceDetails) {
        Optional<Commerce> commerce = commerceRepository.findById(id);
        if (commerce.isPresent()) {
            Commerce commerceToUpdate = commerce.get();
            commerceToUpdate.setName(commerceDetails.getName());
            commerceToUpdate.setAdresse(commerceDetails.getAdresse());
            commerceToUpdate.setPhoto(commerceDetails.getPhoto());
            commerceToUpdate.setEntreprise(commerceDetails.getEntreprise());
            Commerce updatedCommerce = commerceRepository.save(commerceToUpdate);
            return ResponseEntity.ok(updatedCommerce);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommerce(@PathVariable Integer id) {
        Optional<Commerce> commerce = commerceRepository.findById(id);
        if (commerce.isPresent()) {
            commerceRepository.delete(commerce.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
