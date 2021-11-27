package com.knoldus.kup.ipl.IPL_Management_System.models;

import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "teams")
@Service
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String state;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "team_id", referencedColumnName = "id")
//    private Team team;

    @OneToMany(mappedBy = "team",fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Player> players=new HashSet<>();

    public Team(String name, Set<Player> players) {
        this.name = name;
        this.players = players;
    }

    public Team() {
    }

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

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Team{" +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", players=" + players +
                '}';
    }
}
