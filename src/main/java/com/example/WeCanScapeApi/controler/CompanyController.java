package com.example.WeCanScapeApi.controler;

import com.example.WeCanScapeApi.DTO.CreateUserAndCompanyDTO;
import com.example.WeCanScapeApi.modele.Company;
import com.example.WeCanScapeApi.repository.CompanyRepository;
import com.example.WeCanScapeApi.repository.UserRepository;
import com.example.WeCanScapeApi.modele.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @PostMapping("/createWithUser")
    public ResponseEntity<Company> createCompanyWithUser(@RequestBody CreateUserAndCompanyDTO dto) {
        User user = dto.getUser();
        user = userRepository.save(user);

        Company company = dto.getCompany();
        company.setUser(user);
        company = companyRepository.save(company);

        return ResponseEntity.ok(company);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Integer id) {
        Optional<Company> company = companyRepository.findById(id);
        return company.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Company> getCompanyByUserId(@PathVariable Integer userId) {
        Optional<Company> company = companyRepository.findByUserId(userId);
        return company.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Company createCompany(@RequestBody Company company) {
        return companyRepository.save(company);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Integer id,
                                                 @RequestBody Company companyDetails) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            Company companyToUpdate = company.get();
            companyToUpdate.setName(companyDetails.getName());
            companyToUpdate.setHeadOffice(companyDetails.getHeadOffice());
            companyToUpdate.setSiret(companyDetails.getSiret());
            companyToUpdate.setPicture(companyDetails.getPicture());
            companyToUpdate.setUser(companyDetails.getUser());
            Company updatedCompany = companyRepository.save(companyToUpdate);
            return ResponseEntity.ok(updatedCompany);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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
