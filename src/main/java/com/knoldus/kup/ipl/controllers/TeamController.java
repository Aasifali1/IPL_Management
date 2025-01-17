package com.knoldus.kup.ipl.controllers;

import com.knoldus.kup.ipl.models.City;
import com.knoldus.kup.ipl.models.Player;
import com.knoldus.kup.ipl.models.Team;
import com.knoldus.kup.ipl.services.CityService;
import com.knoldus.kup.ipl.services.PlayerService;
import com.knoldus.kup.ipl.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/teams")
public class TeamController {

    /*
    ***************************************** SERVICES ****************************************
     */
    @Autowired
    TeamService teamService;
    @Autowired
    CityService cityService;
    @Autowired
    PlayerService playerService;


    @GetMapping("/addTeam")
    public String addTeamForm(Model model){
        model.addAttribute("team",teamService.getNewTeamObject());
        return "addTeam";
    }

    @PostMapping("/add")
    public String addTeam(Team team, BindingResult result, RedirectAttributes redirectAttributes){
        List<Team> list= teamService.getAllTeams();
        if(list.size()<15) {
            teamService.saveTeam(team);
            redirectAttributes.addFlashAttribute("message", "Team added successfully");
            redirectAttributes.addFlashAttribute("messageType", "team");
            redirectAttributes.addFlashAttribute("alertType", "success");
        }
        else {
            redirectAttributes.addFlashAttribute("message", "Team can not be more than 15");
            redirectAttributes.addFlashAttribute("messageType", "team");
            redirectAttributes.addFlashAttribute("alertType", "error");
        }
        if (result.hasErrors()) {
            redirectAttributes.addAttribute("message", "Failed");
            redirectAttributes.addAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("failed", "failed");
            return "redirect:/ipl/admin";
        }
        return "redirect:/ipl/admin";
    }

    @GetMapping("/edit/{id}")
    public String UpdateForm(@PathVariable("id") long id,
                                 Model model) {
        if(teamService.getTeamById(id).isPresent()) {
            Team team = teamService.getTeamById(id).orElse(null);
            List<City> cities = cityService.getAllCities();
            model.addAttribute("team", team);
            model.addAttribute("cities", cities);
        }
        return "update-team";
    }

    @PostMapping("/update/{id}")
    public String updateTeam(@PathVariable("id") long id, @Valid Team team,
                             BindingResult bindingResult,RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "update-team";
        }
        teamService.saveTeam(team);
        redirectAttributes.addFlashAttribute("message", "Team updated successfully");
        redirectAttributes.addFlashAttribute("messageType", "team");
        redirectAttributes.addFlashAttribute("alertType", "success");

        return "redirect:/ipl/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteTeam(@PathVariable("id") long id, Model model,RedirectAttributes redirectAttributes) {
        teamService.deleteTeam(id);
        redirectAttributes.addFlashAttribute("message", "Team deleted successfully");
        redirectAttributes.addFlashAttribute("messageType", "team");
        redirectAttributes.addFlashAttribute("alertType", "success");
        return "redirect:/ipl/admin";
    }

    @GetMapping("/players/{team_id}")
    public String getPlayers(@PathVariable("team_id") Long team_id,Model model){
        if (playerService.getPlayersByTeamId(team_id)!=null && teamService.getTeamById(team_id).isPresent()){
            Set<Player> players = playerService.getPlayersByTeamId(team_id);
            model.addAttribute("players",players);
            model.addAttribute("team", teamService.getTeamById(team_id).orElse(null));
            return "team-details";
        }
        return "redirect:/ipl";
    }

    @GetMapping("/team/{team_id}")
    public String getTeam(@PathVariable("team_id") Long team_id,Model model, RedirectAttributes redirectAttributes){
        if (playerService.getPlayersByTeamId(team_id)!=null && teamService.getTeamById(team_id).isPresent()){
            Set<Player> players = playerService.getPlayersByTeamId(team_id);
            model.addAttribute("players",players);
            model.addAttribute("team", teamService.getTeamById(team_id).orElse(null));
            return "admin-teams";
        }
        redirectAttributes.addFlashAttribute("message", "Team not found");
        redirectAttributes.addFlashAttribute("messageType", "team");
        redirectAttributes.addFlashAttribute("alertType", "error");
        return "redirect:/ipl/admin";
    }


}
