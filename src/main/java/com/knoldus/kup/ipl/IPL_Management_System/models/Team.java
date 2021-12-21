package com.knoldus.kup.ipl.IPL_Management_System.models;

import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "teams")
@Service
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(min=1,message="Team name can not be null")
    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "team",fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Player> players;

    @OneToMany(mappedBy = "team1",fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Match> team1Maches;

    @OneToOne(mappedBy = "team",fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private PointTable pointTable;

    @OneToMany(mappedBy = "team2",fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Match> team2Maches;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public Set<Match> getTeam1Maches() {
        return team1Maches;
    }

    public void setTeam1Maches(Set<Match> team1Maches) {
        this.team1Maches = team1Maches;
    }

    public Set<Match> getTeam2Maches() {
        return team2Maches;
    }

    public void setTeam2Maches(Set<Match> team2Maches) {
        this.team2Maches = team2Maches;
    }
}
