package com.example.WeCanScapeApi.config;

import com.example.WeCanScapeApi.modele.Right;
import com.example.WeCanScapeApi.repository.RightRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Configuration
public class RightConfig {

    @Bean(name = "rightCommandLineRunner")
    @Order(1)
    CommandLineRunner rightCommandLineRunner(RightRepository rightRepository) {
        return args -> {

            // Créer de nouvelles catégories
            List<Right> rights = Arrays.asList(
                    new Right("User"),
                    new Right("Admin"),
                    new Right("AEL")
                    
            );

            // Vérifier et enregistrer les nouvelles catégories
            for (Right right : rights) {
                Optional<Right> existingRight = rightRepository.findByLabel(right.getLabel());
                if (!existingRight.isPresent()) {
                    rightRepository.save(right);
                }
            }
        };
    }
}