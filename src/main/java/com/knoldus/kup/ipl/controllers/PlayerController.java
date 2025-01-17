package com.knoldus.kup.ipl.controllers;

import com.knoldus.kup.ipl.models.Country;
import com.knoldus.kup.ipl.models.Team;
import com.knoldus.kup.ipl.models.Player;

import com.knoldus.kup.ipl.services.CountryService;
import com.knoldus.kup.ipl.services.PlayerService;
import com.knoldus.kup.ipl.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    PlayerService playerService;
    @Autowired
    TeamService teamService;
    @Autowired
    CountryService countryService;

    @GetMapping("/addForm")
    public String addForm(Model model){
        model.addAttribute("player", playerService.getNewPlayerObject());
        return "add-player";
    }

    @PostMapping("/add")
    public String addPlayer(@Valid Player player, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors())
        { return "addPlayer"; }
        else {
            if(teamService.getByName(player.getTeam().getName()).isPresent()) {
                Team team = teamService.getByName(player.getTeam().getName())
                        .orElseThrow(()->new IllegalArgumentException
                                ("Invalid player name: "+player.getTeam().getName()));
                if (team.getPlayers().size() < 3) {
                    playerService.savePlayer(player);
                    redirectAttributes.addFlashAttribute("message", "Player added successfully");
                    redirectAttributes.addFlashAttribute("messageType", "player");
                    redirectAttributes.addFlashAttribute("alertType", "success");
                    System.out.println(team.getPlayers().size());
                } else {
                    redirectAttributes.addFlashAttribute("message", "Players can not be more than 15");
                    redirectAttributes.addFlashAttribute("messageType", "player");
                    redirectAttributes.addFlashAttribute("alertType", "error");
                }
            }
        }
        return "redirect:/ipl/admin";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Player player = playerService.getPlayerById(id);

        List<Team> teams = teamService.getAllTeams();
        List<Country> countries = countryService.getAllCountries();

        model.addAttribute("player", player);
        model.addAttribute("teams",teams);
        model.addAttribute("countries",countries);
        return "update-player";
    }

    @PostMapping("/update/{id}")
    public String updatePlayer(@PathVariable("id") long id, @Valid Player player,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "update-player";
        }
        playerService.savePlayer(player);
        redirectAttributes.addFlashAttribute("message", "Player updated successfully");
        redirectAttributes.addFlashAttribute("messageType", "player");
        redirectAttributes.addFlashAttribute("alertType", "success");
        return "redirect:/ipl/admin";
    }
    @GetMapping("/delete/{id}")
    public String deletePlayer(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttributes) {
        playerService.deletePlayer(id);
        redirectAttributes.addFlashAttribute("message", "Player deleted successfully");
        redirectAttributes.addFlashAttribute("messageType", "player");
        redirectAttributes.addFlashAttribute("alertType", "success");
        return "redirect:/ipl/admin";
    }
}
