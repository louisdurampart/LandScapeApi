package com.example.WeCanScapeApi.controler;

import com.example.WeCanScapeApi.DTO.CreateUserAndCompanyDTO;
import com.example.WeCanScapeApi.DTO.PoiDTO;
import com.example.WeCanScapeApi.modele.ApiResult;
import com.example.WeCanScapeApi.modele.Company;
import com.example.WeCanScapeApi.repository.CompanyRepository;
import com.example.WeCanScapeApi.repository.UserRepository;
import com.example.WeCanScapeApi.modele.User;
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
@RequestMapping("/api/companies")
@Tag(name = "Company", description = "Gestion des entreprises")
public class CompanyController {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Récupérer toutes les entreprises")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste de toutes les entreprises récupérée avec succès")
    })
    @GetMapping
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Operation(summary = "Créer une entreprise avec un utilisateur associé")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Compte créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Échec de la création du compte")
    })
    @PostMapping("/createWithUser")
    public ResponseEntity<ApiResult<Company>> createCompanyWithUser(@RequestBody CreateUserAndCompanyDTO dto) {
        try {
            User user = dto.getUser();
            user = userRepository.save(user);

            Company company = dto.getCompany();
            company.setUser(user);
            company = companyRepository.save(company);

            return ResponseEntity.ok(new ApiResult<>(true, "Compte créé avec succès.", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResult<>(false, "Échec de la création du compte. " + e.getMessage(), null));
        }
    }

    @Operation(summary = "Récupérer une entreprise par ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entreprise récupérée avec succès"),
        @ApiResponse(responseCode = "404", description = "Entreprise non trouvée")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Integer id) {
        Optional<Company> company = companyRepository.findById(id);
        return company.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Récupérer une entreprise par l'ID de l'utilisateur associé")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entreprise récupérée avec succès"),
        @ApiResponse(responseCode = "404", description = "Entreprise non trouvée")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<Company> getCompanyByUserId(@PathVariable Integer userId) {
        Optional<Company> company = companyRepository.findByUserId(userId);
        return company.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Créer une nouvelle entreprise")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entreprise créée avec succès"),
        @ApiResponse(responseCode = "400", description = "Échec de la création de l'entreprise")
    })
    @PostMapping
    public ResponseEntity<ApiResult<PoiDTO>> createCompany(@RequestBody Company company) {
        try {
            companyRepository.save(company);
            return ResponseEntity.ok(new ApiResult<>(true, "Entreprise créée avec succès.", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResult<>(false, "Échec de la création de l'entreprise. " + e.getMessage(), null));
        }
    }

    @Operation(summary = "Mettre à jour une entreprise existante")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entreprise mise à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Entreprise non trouvée")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Integer id,
            @RequestBody Company companyDetails) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            Company companyToUpdate = company.get();
            companyToUpdate.setName(companyDetails.getName());
            companyToUpdate.setSiret(companyDetails.getSiret());
            companyToUpdate.setUser(companyDetails.getUser());
            Company updatedCompany = companyRepository.save(companyToUpdate);
            return ResponseEntity.ok(updatedCompany);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Supprimer une entreprise par ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Entreprise supprimée avec succès"),
        @ApiResponse(responseCode = "404", description = "Entreprise non trouvée")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Integer id) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            companyRepository.delete(company.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
