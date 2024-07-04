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
                    new Category("Sports"),
                    new Category("Musique"),
                    new Category("Art"),
                    new Category("Restauration"),
                    new Category("Musées"),
                    new Category("Hébergement"),
                    new Category("Randonnée"),
                    new Category("Culturel"),
                    new Category("Aventure"),
                    new Category("Activités familiales"),
                    new Category("Activités aquatiques"),
                    new Category("Transport"),
                    new Category("Santé"),
                    new Category("Éducation"),
                    new Category("Shopping"),
                    new Category("Banques et guichets automatiques"),
                    new Category("Services publics"),
                    new Category("Loisirs"),
                    new Category("Nature et parcs"),
                    new Category("Hôtels"),
                    new Category("Stations-service")
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
