package com.example.WeCanScapeApi.config;

import com.example.WeCanScapeApi.modele.Category;
import com.example.WeCanScapeApi.repository.CategoryRepository;
import com.example.WeCanScapeApi.repository.HobbyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Configuration
public class CategoryConfig {

    @Bean(name = "categoryCommandLineRunner")
    @Order(1)
    CommandLineRunner categoryCommandLineRunner(CategoryRepository categoryRepository, HobbyRepository hobbyRepository) {
        return args -> {
            // Supprimer tous les hobbies pour éviter les contraintes de clé étrangère
            // hobbyRepository.deleteAll();

            // Supprimer toutes les catégories existantes
            // categoryRepository.deleteAll();

            // Créer de nouvelles catégories
            List<Category> categories = Arrays.asList(
                    new Category("Sports", "bike"),
                    new Category("Musique", "music"),
                    new Category("Art", "palette"),
                    new Category("Restauration", "silverware-fork-knife"),
                    new Category("Musées", "bank-outline"),
                    new Category("Hébergement", "home-group"),
                    new Category("Randonnée", "hiking"),
                    new Category("Culturel", "party-popper"),
                    new Category("Aventure", "parachute"),
                    new Category("Activités familiales", "human-male-child"),
                    new Category("Activités aquatiques", "diving-flippers"),
                    new Category("Transport", "train"),
                    new Category("Santé", "hospital-building"),
                    new Category("Shopping", "shopping"),
                    new Category("Loisirs", "ticket"),
                    new Category("Nature et parcs", "pine-tree"),
                    new Category("Hôtels", "bed"),
                    new Category("Stations-service", "gas-station")
            );

            // Vérifier et enregistrer les nouvelles catégories
            for (Category category : categories) {
                Optional<Category> existingCategory = categoryRepository.findByLabel(category.getLabel());
                if (!existingCategory.isPresent()) {
                    categoryRepository.save(category);
                }
            }
        };
    }
}