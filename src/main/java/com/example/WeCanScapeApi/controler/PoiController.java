package com.example.WeCanScapeApi.controler;

import com.example.WeCanScapeApi.DTO.HobbyDTO;
import com.example.WeCanScapeApi.DTO.LocationRequest;
import com.example.WeCanScapeApi.DTO.PoiDTO;
import com.example.WeCanScapeApi.modele.ApiResponse;
import com.example.WeCanScapeApi.modele.Category;
import com.example.WeCanScapeApi.modele.Company;
import com.example.WeCanScapeApi.modele.Hobby;
import com.example.WeCanScapeApi.modele.Poi;
import com.example.WeCanScapeApi.repository.CategoryRepository;
import com.example.WeCanScapeApi.repository.CompanyRepository;
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

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CompanyRepository companyRepository;

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
    public ResponseEntity<ApiResponse<PoiDTO>> createHobby(@RequestBody PoiDTO poiDTO) {
        try {
            Category category = categoryRepository.findById(poiDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            Company company = companyRepository.findById(poiDTO.getCompanyId())
                    .orElseThrow(() -> new RuntimeException("Company not found"));

            Poi poi = new Poi();
            poi.setAddress(poiDTO.getAddress());
            poi.setName(poiDTO.getName());
            poi.setDescription(poiDTO.getDescription());
            poi.setLat(poiDTO.getLat());
            poi.setLon(poiDTO.getLon());
            poi.setCategory(category);
            poi.setCompany(company);

            poiRepository.save(poi);
            return ResponseEntity.ok(new ApiResponse<>(true, "Commerce créé avec succès.", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Échec de la création du commerce. " + e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Poi> updatePoi(@PathVariable Integer id, @RequestBody Poi poiDetails) {
        Optional<Poi> poi = poiRepository.findById(id);
        if (poi.isPresent()) {
            Poi poiToUpdate = poi.get();
            poiToUpdate.setName(poiDetails.getName());
            poiToUpdate.setAddress(poiDetails.getAddress());
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

    @GetMapping("/within-radius")
    public ResponseEntity<List<Poi>> getPoisWithinRadius(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "1.0") double radius) {

        List<Poi> pois = poiRepository.findPoisWithinRadius(latitude, longitude, radius);
        return ResponseEntity.ok(pois);
    }

}
