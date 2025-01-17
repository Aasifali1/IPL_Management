package com.knoldus.kup.ipl.services;

import com.knoldus.kup.ipl.models.Team;
import com.knoldus.kup.ipl.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team saveTeam(Team team){
        return teamRepository.save(team);
    }

    public Team getNewTeamObject(){
        return new Team();
    }

    public Optional<Team> getTeamById(Long id){
        return teamRepository.findById(id);
    }

    public Optional<Team> getByName(String name){
        return teamRepository.findByName(name);
    }

    public List<Team> getAllTeams(){
        return teamRepository.findAll();
    }

    public void deleteTeam(Long id){
        teamRepository.deleteById(id);
    }
}
