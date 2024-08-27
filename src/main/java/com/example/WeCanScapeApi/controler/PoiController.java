package com.example.WeCanScapeApi.controler;

import com.example.WeCanScapeApi.DTO.PoiDTO;
import com.example.WeCanScapeApi.modele.ApiResult;
import com.example.WeCanScapeApi.modele.Category;
import com.example.WeCanScapeApi.modele.Company;
import com.example.WeCanScapeApi.modele.Poi;
import com.example.WeCanScapeApi.repository.CategoryRepository;
import com.example.WeCanScapeApi.repository.CompanyRepository;
import com.example.WeCanScapeApi.repository.PoiRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/poi")
@Tag(name = "POI", description = "Gestion des Points d'Intérêt (POI)")
public class PoiController {
    @Autowired
    private PoiRepository poiRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Operation(summary = "Récupérer tous les POIs")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste de tous les POIs récupérée avec succès")
    })
    @GetMapping
    public List<Poi> getAllPois() {
        return poiRepository.findAll();
    }

    @Operation(summary = "Récupérer un POI par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "POI récupéré avec succès"),
        @ApiResponse(responseCode = "404", description = "POI non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Poi> getPoiById(@PathVariable Integer id) {
        Optional<Poi> poi = poiRepository.findById(id);
        return poi.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Récupérer les POIs d'une entreprise par ID de l'entreprise")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des POIs de l'entreprise récupérée avec succès")
    })
    @GetMapping("/company/{companyId}")
    public List<Poi> getPoisByCompany(@PathVariable Integer companyId) {
        return poiRepository.findByCompanyId(companyId);
    }

    @Operation(summary = "Créer un nouveau POI")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "POI créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Échec de la création du POI")
    })
    @PostMapping
    public ResponseEntity<ApiResult<PoiDTO>> createPoi(@RequestBody PoiDTO poiDTO) {
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
            return ResponseEntity.ok(new ApiResult<>(true, "Commerce créé avec succès.", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResult<>(false, "Échec de la création du commerce. " + e.getMessage(), null));
        }
    }

    @Operation(summary = "Mettre à jour un POI existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "POI mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "POI non trouvé")
    })
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

    @Operation(summary = "Supprimer un POI")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "POI supprimé avec succès"),
        @ApiResponse(responseCode = "400", description = "Échec de la suppression : POI non trouvé")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePoi(@PathVariable Integer id) {
        System.out.println("Delete request received for POI with id: " + id);

        Optional<Poi> poi = poiRepository.findById(id);

        if (poi.isPresent()) {
            poiRepository.delete(poi.get());
            return ResponseEntity.ok(new ApiResult<>(true, "POI supprimé avec succès.", null));
        } else {
            return ResponseEntity.badRequest()
                    .body(new ApiResult<>(false, "Échec de la suppression : POI non trouvé.", null));
        }
    }

    @Operation(summary = "Récupérer les POIs dans un rayon spécifique")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des POIs récupérée avec succès")
    })
    @GetMapping("/within-radius")
    public ResponseEntity<List<Poi>> getPoisWithinRadius(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "1.0") double radius) {

        List<Poi> pois = poiRepository.findPoisWithinRadius(latitude, longitude, radius);
        return ResponseEntity.ok(pois);
    }
}
