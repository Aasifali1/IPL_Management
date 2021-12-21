package com.knoldus.kup.ipl.IPL_Management_System.controllers;

import com.knoldus.kup.ipl.IPL_Management_System.models.City;
import com.knoldus.kup.ipl.IPL_Management_System.repository.CityRepository;
import com.knoldus.kup.ipl.IPL_Management_System.repository.PlayerRepository;
import com.knoldus.kup.ipl.IPL_Management_System.repository.TeamRepository;
import com.knoldus.kup.ipl.IPL_Management_System.models.Player;
import com.knoldus.kup.ipl.IPL_Management_System.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    CityRepository cityRepository;


    @GetMapping("/addTeam")
    public String addTeamForm(Model model){
        Team team = new Team();
        model.addAttribute("team",team);
        return "addTeam";
    }

    @PostMapping("/add")
    public String addTeam(Team team){
        teamRepository.save(team);
        return "redirect:/ipl/admin";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id,
                                 Model model) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid player Id:" + id));
        List<City> cities = (List<City>) cityRepository.findAll();
        model.addAttribute("team", team);
        model.addAttribute("cities", cities);
        return "update-team";
    }

    @PostMapping("/update/{id}")
    public String updateTeam(@PathVariable("id") long id, @Valid Team team,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update-team";
        }
        teamRepository.save(team);
        return "redirect:/ipl/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteTeam(@PathVariable("id") long id, Model model) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid player Id:" + id));
        teamRepository.delete(team);
        return "redirect:/ipl/admin";
    }

    @GetMapping("/players/{team_id}")
    public String getPlayers(@PathVariable("team_id") Long team_id,Model model){
        Set<Player> players = playerRepository.findByTeamId(Long.valueOf(team_id));
        model.addAttribute("players",players);
        System.out.println("--------------------------"+teamRepository.findById(team_id).get().getName() );
        model.addAttribute("team", teamRepository.findById(team_id).get());
        return "team-details";
    }

    @GetMapping("/team/{team_id}")
    public String getTeam(@PathVariable("team_id") Long team_id,Model model){
        Set<Player> players = playerRepository.findByTeamId(Long.valueOf(team_id));
        model.addAttribute("players",players);
        model.addAttribute("team", teamRepository.findById(team_id).get());
        return "admin-teams";
    }
}
