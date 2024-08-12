package com.example.WeCanScapeApi.controler;

import com.example.WeCanScapeApi.modele.ApiResponse;
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
    public Poi createPoi(@RequestBody Poi poi) {
        return poiRepository.save(poi);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Poi> updatePoi(@PathVariable Integer id, @RequestBody Poi poiDetails) {
        Optional<Poi> poi = poiRepository.findById(id);
        if (poi.isPresent()) {
            Poi poiToUpdate = poi.get();
            poiToUpdate.setName(poiDetails.getName());
            poiToUpdate.setAddress(poiDetails.getAddress());
            poiToUpdate.setPicture(poiDetails.getPicture());
            poiToUpdate.setCompany(poiDetails.getCompany());
            Poi updatedPoi = poiRepository.save(poiToUpdate);
            return ResponseEntity.ok(updatedPoi);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePoi(@PathVariable Integer id) {
        System.out.println("Delete request received for POI with id: " + id);

        Optional<Poi> poi = poiRepository.findById(id);

        if (poi.isPresent()) {
            poiRepository.delete(poi.get());
            return ResponseEntity.ok(new ApiResponse<>(true, "POI supprimé avec succès.", null));
        } else {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Échec de la suppression : POI non trouvé.", null));
        }
    }

}
