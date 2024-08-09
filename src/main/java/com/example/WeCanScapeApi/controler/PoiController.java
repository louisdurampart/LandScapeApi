package com.example.WeCanScapeApi.controler;

import com.example.WeCanScapeApi.modele.Poi;
import com.example.WeCanScapeApi.repository.PoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/poi")
public class PoiController {

    @Autowired
    private PoiRepository poiRepository;

    @GetMapping
    public List<Poi> getAllPois() {
        return poiRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Poi> getPoiById(@PathVariable Integer id) {
        Optional<Poi> poi = poiRepository.findById(id);
        return poi.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/company/{companyId}")
    public List<Poi> getPoisByCompany(@PathVariable Integer companyId) {
        return poiRepository.findByCompanyId(companyId);
    }

    @PostMapping
    public ResponseEntity<Poi> createPoi(@RequestBody Poi poi) {
        try {
            // Vérification ou transformation des données JSON si nécessaire
            String picturesJson = poi.getPictures();
            // On peut faire des validations ou transformations sur picturesJson ici si nécessaire
            poi.setPictures(picturesJson); // Assurez-vous que les données sont correctement formatées
            Poi createdPoi = poiRepository.save(poi);
            return ResponseEntity.ok(createdPoi);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Gérer les erreurs en fonction des besoins
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Poi> updatePoi(@PathVariable Integer id, @RequestBody Poi poiDetails) {
        Optional<Poi> poi = poiRepository.findById(id);
        if (poi.isPresent()) {
            Poi poiToUpdate = poi.get();
            poiToUpdate.setName(poiDetails.getName());
            poiToUpdate.setAddress(poiDetails.getAddress());
            poiToUpdate.setPictures(poiDetails.getPictures()); // Mise à jour avec la chaîne JSON
            poiToUpdate.setCompany(poiDetails.getCompany());
            Poi updatedPoi = poiRepository.save(poiToUpdate);
            return ResponseEntity.ok(updatedPoi);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoi(@PathVariable Integer id) {
        Optional<Poi> poi = poiRepository.findById(id);
        if (poi.isPresent()) {
            poiRepository.delete(poi.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
