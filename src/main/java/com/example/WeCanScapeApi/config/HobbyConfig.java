package com.example.WeCanScapeApi.config;

import com.example.WeCanScapeApi.modele.Category;
import com.example.WeCanScapeApi.modele.Hobby;
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
public class HobbyConfig {

    @Bean(name = "hobbyCommandLineRunner")
    @Order(2)
    CommandLineRunner hobbyCommandLineRunner(CategoryRepository categoryRepository, HobbyRepository hobbyRepository) {
        return args -> {
            // Fetch existing categories
            List<Category> categories = categoryRepository.findAll();

            Category sports = categories.stream().filter(c -> c.getLabel().equals("Sports")).findFirst().orElse(null);
            Category music = categories.stream().filter(c -> c.getLabel().equals("Musique")).findFirst().orElse(null);
            Category art = categories.stream().filter(c -> c.getLabel().equals("Art")).findFirst().orElse(null);
            Category restaurants = categories.stream().filter(c -> c.getLabel().equals("Restauration")).findFirst().orElse(null);
            Category musees = categories.stream().filter(c -> c.getLabel().equals("Musées")).findFirst().orElse(null);
            Category accommodation = categories.stream().filter(c -> c.getLabel().equals("Hébergement")).findFirst().orElse(null);
            Category hiking = categories.stream().filter(c -> c.getLabel().equals("Randonnée")).findFirst().orElse(null);
            Category cultural = categories.stream().filter(c -> c.getLabel().equals("Culturel")).findFirst().orElse(null);
            Category adventure = categories.stream().filter(c -> c.getLabel().equals("Aventure")).findFirst().orElse(null);
            Category familyActivities = categories.stream().filter(c -> c.getLabel().equals("Activités familiales")).findFirst().orElse(null);
            Category waterActivities = categories.stream().filter(c -> c.getLabel().equals("Activités aquatiques")).findFirst().orElse(null);
            Category transport = categories.stream().filter(c -> c.getLabel().equals("Transport")).findFirst().orElse(null);
            Category health = categories.stream().filter(c -> c.getLabel().equals("Santé")).findFirst().orElse(null);
            Category education = categories.stream().filter(c -> c.getLabel().equals("Éducation")).findFirst().orElse(null);
            Category shopping = categories.stream().filter(c -> c.getLabel().equals("Shopping")).findFirst().orElse(null);
            Category banking = categories.stream().filter(c -> c.getLabel().equals("Banques et guichets automatiques")).findFirst().orElse(null);
            Category publicServices = categories.stream().filter(c -> c.getLabel().equals("Services publics")).findFirst().orElse(null);
            Category leisure = categories.stream().filter(c -> c.getLabel().equals("Loisirs")).findFirst().orElse(null);
            Category natureParks = categories.stream().filter(c -> c.getLabel().equals("Nature et parcs")).findFirst().orElse(null);
            Category hotels = categories.stream().filter(c -> c.getLabel().equals("Hôtels")).findFirst().orElse(null);
            Category gasStations = categories.stream().filter(c -> c.getLabel().equals("Stations-service")).findFirst().orElse(null);

            // Create hobbies and link them to categories
            List<Hobby> hobbies = Arrays.asList(
                    // Sports
                    new Hobby("Football", sports), new Hobby("Basketball", sports), new Hobby("Tennis", sports),
                    new Hobby("Volleyball", sports), new Hobby("Course à pied", sports), new Hobby("Vélo", sports),
                    new Hobby("Natation", sports), new Hobby("Escalade", sports), new Hobby("Surf", sports),
                    new Hobby("Ski", sports), new Hobby("Snowboard", sports), new Hobby("Roller", sports),
                    new Hobby("Skateboard", sports), new Hobby("Golf", sports), new Hobby("Baseball", sports),
                    new Hobby("Boxe", sports), new Hobby("Arts martiaux", sports), new Hobby("Yoga", sports),
                    new Hobby("Fitness en plein air", sports),

                    // Musique
                    new Hobby("Concerts en plein air", music), new Hobby("Festivals de musique", music),
                    new Hobby("Performances de rue", music), new Hobby("Karaoké en plein air", music),

                    // Art
                    new Hobby("Peinture en plein air", art), new Hobby("Photographie", art),
                    new Hobby("Parcs de sculptures", art), new Hobby("Ateliers d'art en plein air", art),

                    // Restauration
                    new Hobby("Dîner en terrasse", restaurants), new Hobby("Festivals de food trucks", restaurants),
                    new Hobby("Pique-nique", restaurants), new Hobby("Dégustation de vin", restaurants),
                    new Hobby("Marchés de producteurs", restaurants),

                    // Musées
                    new Hobby("Musées en plein air", musees), new Hobby("Visites de sites historiques", musees),
                    new Hobby("Jardins botaniques", musees), new Hobby("Expositions en plein air", musees),

                    // Hébergement
                    new Hobby("Camping", accommodation), new Hobby("Glamping", accommodation),
                    new Hobby("Stations balnéaires", accommodation), new Hobby("Chalets de montagne", accommodation),
                    new Hobby("Auberges de jeunesse", accommodation),

                    // Randonnée
                    new Hobby("Randonnée pédestre", hiking), new Hobby("Randonnée en montagne", hiking),
                    new Hobby("Balades en nature", hiking), new Hobby("Observation des oiseaux", hiking),
                    new Hobby("Trekking", hiking),

                    // Culturel
                    new Hobby("Théâtres en plein air", cultural), new Hobby("Reconstitutions historiques", cultural),
                    new Hobby("Fêtes de rue", cultural), new Hobby("Festivals culturels", cultural),
                    new Hobby("Spectacles de danse", cultural),

                    // Aventure
                    new Hobby("Tyrolienne", adventure), new Hobby("Parapente", adventure),
                    new Hobby("Saut en parachute", adventure), new Hobby("Saut à l'élastique", adventure),
                    new Hobby("Rafting en eaux vives", adventure), new Hobby("Escalade en montagne", adventure),
                    new Hobby("Canyoning", adventure),

                    // Activités familiales
                    new Hobby("Parcs d'attractions", familyActivities), new Hobby("Visites de zoos", familyActivities),
                    new Hobby("Visites de fermes", familyActivities), new Hobby("Jardins botaniques", familyActivities),
                    new Hobby("Balades à cheval", familyActivities), new Hobby("Parcs de jeux", familyActivities),

                    // Activités aquatiques
                    new Hobby("Kayak", waterActivities), new Hobby("Canoë", waterActivities),
                    new Hobby("Paddleboard", waterActivities), new Hobby("Randonnée aquatique", waterActivities),
                    new Hobby("Plongée sous-marine", waterActivities), new Hobby("Plongée avec tuba", waterActivities),
                    new Hobby("Ski nautique", waterActivities), new Hobby("Jet ski", waterActivities),
                    new Hobby("Planche à voile", waterActivities), new Hobby("Pêche", waterActivities),

                    // Transport
                    new Hobby("Gare ferroviaire", transport), new Hobby("Aéroport", transport),
                    new Hobby("Arrêt de bus", transport), new Hobby("Station de métro", transport),
                    new Hobby("Vélos en libre-service", transport),

                    // Santé
                    new Hobby("Hôpital", health), new Hobby("Clinique", health),
                    new Hobby("Pharmacie", health), new Hobby("Dentiste", health),

                    // Éducation
                    //  new Hobby("École", education), new Hobby("Université", education),
                    //  new Hobby("Bibliothèque", education), new Hobby("Musée de sciences", education),

                    // Shopping
                    new Hobby("Centre commercial", shopping), new Hobby("Magasin de vêtements", shopping),
                    new Hobby("Épicerie", shopping), new Hobby("Marché", shopping),

                    // Banques et guichets automatiques
                    //    new Hobby("Banque", banking), new Hobby("Distributeur automatique de billets", banking),

                    // Services publics
                    //     new Hobby("Bureau de poste", publicServices), new Hobby("Commissariat de police", publicServices),
                    //     new Hobby("Casernes de pompiers", publicServices),

                    // Loisirs
                    new Hobby("Cinéma", leisure), new Hobby("Théâtre", leisure),
                    new Hobby("Parc d'attractions", leisure), new Hobby("Parc aquatique", leisure),

                    // Nature et parcs
                    new Hobby("Parc", natureParks), new Hobby("Jardin public", natureParks),
                    new Hobby("Réserve naturelle", natureParks), new Hobby("Forêt", natureParks),

                    // Hôtels
                    new Hobby("Hôtel", hotels), new Hobby("Motel", hotels),
                    new Hobby("Auberge", hotels), new Hobby("Chambre d'hôtes", hotels),

                    // Stations-service
                    new Hobby("Station-service", gasStations), new Hobby("Borne de recharge électrique", gasStations)
            );

            for (Hobby hobby : hobbies) {
                Optional<Hobby> existingHobby = hobbyRepository.findByLibelleAndCategory(hobby.getLibelle(), hobby.getCategory());
                if (!existingHobby.isPresent()) {
                    hobbyRepository.save(hobby);
                }
            }
        };
    }
}
