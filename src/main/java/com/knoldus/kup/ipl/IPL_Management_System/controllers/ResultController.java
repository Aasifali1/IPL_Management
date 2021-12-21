package com.knoldus.kup.ipl.IPL_Management_System.controllers;

import com.knoldus.kup.ipl.IPL_Management_System.models.*;
import com.knoldus.kup.ipl.IPL_Management_System.repository.*;
import com.knoldus.kup.ipl.IPL_Management_System.services.PointService;
import com.knoldus.kup.ipl.IPL_Management_System.services.ResultService;
import com.knoldus.kup.ipl.IPL_Management_System.services.UpdateResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("result")
public class ResultController {

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    VenueRepository venueRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    ResultService resultService;

    @Autowired
    UpdateResultService updateResultService;

    @Autowired
    PointService pointService;

    @GetMapping("/addScore/{match_id}")
    public String showAddForm(@PathVariable ("match_id") long match_id, Model model) {

        Match match = matchRepository.findById(match_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid match Id:" + match_id));
        System.out.println("result add method"+match.getTeam1().getName());

        model.addAttribute("match",match);

        List<Team> teams = (List<Team>) teamRepository.findAll();
        model.addAttribute("teams",teams);

        List<Venue> venues= (List<Venue>) venueRepository.findAll();
        model.addAttribute("venues",venues);

        model.addAttribute("team1", match.getTeam1().getName());
        model.addAttribute("team2", match.getTeam2().getName());

        return "add-result";
    }

    @GetMapping("/editScore/{match_id}")
    public String showUpdateForm(@PathVariable ("match_id") long match_id, Model model) {

        Match match = matchRepository.findById(match_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid match Id:" + match_id));
        System.out.println("result edit method"+match.getTeam1().getName());

        model.addAttribute("match",match);

        List<Team> teams = (List<Team>) teamRepository.findAll();
        model.addAttribute("teams",teams);

        List<Venue> venues= (List<Venue>) venueRepository.findAll();
        model.addAttribute("venues",venues);
        model.addAttribute("team1", match.getTeam1().getName());
        model.addAttribute("team2", match.getTeam2().getName());

        return "update-result";
    }

    @PostMapping("/add/{id}")
    public String ScoreSave(@PathVariable("id") long id, Match match, Model model){
        System.out.println("add result method---------------------------------"+match.getTeam1Over());
        resultService.getResult(match);
        pointService.updatePointTable(match);
        return "redirect:/ipl/admin";
    }

    @PostMapping("/update/{id}")
    public String ScoreUpdate(@PathVariable("id") long id, Match match, Model model){
        System.out.println("result update method---------------------------------"+match.getTeam1Over());
//        resultService.getResult(match);
        updateResultService.updatePointTable(match);
        return "redirect:/ipl/admin";
    }

}
