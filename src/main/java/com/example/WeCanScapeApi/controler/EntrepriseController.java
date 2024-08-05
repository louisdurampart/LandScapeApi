package com.example.WeCanScapeApi.controler;

import com.example.WeCanScapeApi.DTO.CreateUserAndEntrepriseDTO;
import com.example.WeCanScapeApi.modele.Entreprise;
import com.example.WeCanScapeApi.repository.EntrepriseRepository;
import com.example.WeCanScapeApi.repository.UserRepository;
import com.example.WeCanScapeApi.modele.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/entreprises")
public class EntrepriseController {
    @Autowired
    private EntrepriseRepository entrepriseRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Entreprise> getAllEntreprises() {
        return entrepriseRepository.findAll();
    }
    //création de user et d'entreprise
    @PostMapping("/createWithUser")
    public ResponseEntity<Entreprise> createEntrepriseWithUser(@RequestBody CreateUserAndEntrepriseDTO dto) {
        // Créer et sauvegarder l'utilisateur
        User user = dto.getUser();
        user = userRepository.save(user);

        // Créer et sauvegarder l'entreprise, en associant l'utilisateur
        Entreprise entreprise = dto.getEntreprise();
        entreprise.setUser(user);
        entreprise = entrepriseRepository.save(entreprise);

        return ResponseEntity.ok(entreprise);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entreprise> getEntrepriseById(@PathVariable Integer id) {
        Optional<Entreprise> entreprise = entrepriseRepository.findById(id);
        return entreprise.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Entreprise createEntreprise(@RequestBody Entreprise entreprise) {
        return entrepriseRepository.save(entreprise);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entreprise> updateEntreprise(@PathVariable Integer id,
            @RequestBody Entreprise entrepriseDetails) {
        Optional<Entreprise> entreprise = entrepriseRepository.findById(id);
        if (entreprise.isPresent()) {
            Entreprise entrepriseToUpdate = entreprise.get();
            entrepriseToUpdate.setNom(entrepriseDetails.getNom());
            entrepriseToUpdate.setSiegeSocial(entrepriseDetails.getSiegeSocial());
            entrepriseToUpdate.setSiret(entrepriseDetails.getSiret());
            entrepriseToUpdate.setPhotos(entrepriseDetails.getPhotos());
            entrepriseToUpdate.setUser(entrepriseDetails.getUser());
            Entreprise updatedEntreprise = entrepriseRepository.save(entrepriseToUpdate);
            return ResponseEntity.ok(updatedEntreprise);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntreprise(@PathVariable Integer id) {
        Optional<Entreprise> entreprise = entrepriseRepository.findById(id);
        if (entreprise.isPresent()) {
            entrepriseRepository.delete(entreprise.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
