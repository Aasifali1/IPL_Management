package com.knoldus.kup.ipl.IPL_Management_System.controllers;

import com.knoldus.kup.ipl.IPL_Management_System.dao.PlayerDao;
import com.knoldus.kup.ipl.IPL_Management_System.dao.TeamDao;
import com.knoldus.kup.ipl.IPL_Management_System.models.Player;
import com.knoldus.kup.ipl.IPL_Management_System.models.Team;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.WebParam;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamDao teamDao;

    @Autowired
    PlayerDao playerDao;


    @GetMapping("/addTeam")
    public String addTeamForm(Model model){
        Team team = new Team();
        model.addAttribute("team",team);
        return "addTeam";
    }

    @PostMapping("/add")
    public String addTeam(Team team){
        teamDao.save(team);
        return "redirect:/ipl";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Team team = teamDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid player Id:" + id));

        model.addAttribute("team", team);
        return "update-team";
    }

    @PostMapping("/update/{id}")
    public String updateTeam(@PathVariable("id") long id, Team team, Model model) {
//        if (result.hasErrors()) {
//            user.setId(id);
//            return "update-user";
//        }

        teamDao.save(team);
        return "redirect:/ipl";
    }

    @GetMapping("/delete/{id}")
    public String deleteTeam(@PathVariable("id") long id, Model model) {
        Team team = teamDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid player Id:" + id));
        teamDao.delete(team);
        return "redirect:/ipl";
    }

    @GetMapping("/players/{team_id}")
    public String getPlayers(@PathVariable("team_id") Long team_id,Model model){
        Set<Player> players = playerDao.findByTeamId(Long.valueOf(team_id));
//        System.out.println("working"+players.iterator().next().getName());
        model.addAttribute("players",players);
        return "team-details";
    }
}
