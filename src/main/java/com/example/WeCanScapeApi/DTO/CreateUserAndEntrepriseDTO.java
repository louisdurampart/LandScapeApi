package com.example.WeCanScapeApi.DTO;

import com.example.WeCanScapeApi.modele.Entreprise;
import com.example.WeCanScapeApi.modele.User;

public class CreateUserAndEntrepriseDTO {
    private User user;
    private Entreprise entreprise;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }
}