package com.example.WeCanScapeApi.DTO;

import com.example.WeCanScapeApi.modele.Company;
import com.example.WeCanScapeApi.modele.User;

public class CreateUserAndCompanyDTO {
    private User user;
    private Company company;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}