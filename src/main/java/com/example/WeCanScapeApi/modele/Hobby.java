package com.example.WeCanScapeApi.modele;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Hobby")
@Getter
@Setter
public class Hobby {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String libelle;

  @ManyToOne
  @JoinColumn(name = "id_category")
  private Category category;

  public Hobby() {
    // Constructeur par défaut
  }

  public Hobby(String libelle, Category category) {
    this.libelle = libelle;
    this.category = category;
  }
}
