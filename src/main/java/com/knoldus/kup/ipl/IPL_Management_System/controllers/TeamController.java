package com.knoldus.kup.ipl.IPL_Management_System.controllers;

import com.knoldus.kup.ipl.IPL_Management_System.dao.TeamDao;
import com.knoldus.kup.ipl.IPL_Management_System.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamDao teamDao;


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
}
