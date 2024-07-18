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
    private String laStart;
    private String laEnd;
    private String loStart;
    private String loEnd;

    @OneToMany(mappedBy = "history")
    private List<Waypoint> waypoints;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLaStart() {
		return laStart;
	}

	public void setLaStart(String la_start) {
		this.laStart = la_start;
	}

	public String getLaEnd() {
		return laEnd;
	}

	public void setLaEnd(String la_end) {
		this.laEnd = la_end;
	}

	public String getLoStart() {
		return loStart;
	}

	public void setLoStart(String lo_start) {
		this.loStart = lo_start;
	}

	public String getLoEnd() {
		return loEnd;
	}

	public void setLoEnd(String lo_end) {
		this.loEnd = lo_end;
	}

	public List<Waypoint> getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(List<Waypoint> waypoints) {
		this.waypoints = waypoints;
	}
    
    
}
