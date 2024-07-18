package com.example.WeCanScapeApi.modele;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "\"User\"") // Utilisation de guillemets doubles pour échapper le nom réservé dans PostgreSQL
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String firstName;
    private Date birthdate;
    private String uId;

    @ManyToOne
    @JoinColumn(name = "id_right")
    private Right right;
    
    public User() {}
    

	public User(String name, String firstName, Date birthdate, String uId, Right right) {
		super();
		this.name = name;
		this.firstName = firstName;
		this.birthdate = birthdate;
		this.uId = uId;
		this.right = right;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	
	public Right getRight() {
		return right;
	}

	public void setRight(Right right) {
		this.right = right;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}
    
    
}
