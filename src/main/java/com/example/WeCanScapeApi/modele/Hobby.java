package com.example.WeCanScapeApi.modele;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

  private String label;

  @ManyToOne
  @JoinColumn(name = "id_category")
  @JsonBackReference
  private Category category;

  public Hobby() {
    // Constructeur par défaut
  }

  public Hobby(String label, Category category) {
    this.label = label;
    this.category = category;
  }

public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public String getLabel() {
	return label;
}

public void setLabel(String label) {
	this.label = label;
}

public Category getCategory() {
	return category;
}

public void setCategory(Category category) {
	this.category = category;
}
  
  
}