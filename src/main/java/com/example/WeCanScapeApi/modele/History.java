package com.example.WeCanScapeApi.modele;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "History")
@Getter
@Setter
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    private Date date;

    // Informations de départ et d'arrivée de l'historique
    private String latitude_depart;
    private String latitude_arrive;
    private String longitude_depart;
    private String longitude_arrive;

    @OneToMany(mappedBy = "history")
    private List<Waypoint> waypoints;
}
