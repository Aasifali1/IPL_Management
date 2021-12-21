package com.knoldus.kup.ipl.IPL_Management_System.controllers;

import com.knoldus.kup.ipl.IPL_Management_System.models.Country;
import com.knoldus.kup.ipl.IPL_Management_System.models.Team;
import com.knoldus.kup.ipl.IPL_Management_System.repository.CountryRepository;
import com.knoldus.kup.ipl.IPL_Management_System.repository.PlayerRepository;
import com.knoldus.kup.ipl.IPL_Management_System.repository.TeamRepository;
import com.knoldus.kup.ipl.IPL_Management_System.models.Player;

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


@Controller
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private CountryRepository countryRepository;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "team_id", referencedColumnName = "id")
//    private Team team;


    @GetMapping("/addForm")
    public String addForm(Model model){
        Player player = new Player();
        model.addAttribute("player",player);
        return "add-player";
    }

    @PostMapping("/add")
    public String addPlayer(@Valid Player player, BindingResult bindingResult){
        if(bindingResult.hasErrors())
        {
            return "addPlayer";
        }
        else
        {
            Team team=teamRepository.findByName(player.getTeam().getName());
            if(team.getPlayers().size()<15) {
                playerRepository.save(player);
                System.out.println(team.getPlayers().size());
            }else
                System.out.println("Team already full");
        }
        return "redirect:/ipl/admin";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid player Id:" + id));

        List<Team> teams = teamRepository.findAll();
        List<Country> countries = (List<Country>) countryRepository.findAll();

        model.addAttribute("player", player);
        model.addAttribute("teams",teams);
        model.addAttribute("countries",countries);
        return "update-player";
    }

    @PostMapping("/update/{id}")
    public String updatePlayer(@PathVariable("id") long id, @Valid Player player,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update-player";
//            return "redirect:/players/edit/"+id;
        }
        playerRepository.save(player);
        return "redirect:/ipl/admin";
    }

    @GetMapping("/delete/{id}")
    public String deletePlayer(@PathVariable("id") long id, Model model) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid player Id:" + id));
        playerRepository.delete(player);
        return "redirect:/ipl/admin";
    }

}
