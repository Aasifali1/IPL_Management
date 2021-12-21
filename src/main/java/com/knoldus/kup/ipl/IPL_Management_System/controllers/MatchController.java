package com.knoldus.kup.ipl.IPL_Management_System.controllers;

import com.knoldus.kup.ipl.IPL_Management_System.models.Match;
import com.knoldus.kup.ipl.IPL_Management_System.models.Team;
import com.knoldus.kup.ipl.IPL_Management_System.models.Venue;
import com.knoldus.kup.ipl.IPL_Management_System.repository.MatchRepository;
import com.knoldus.kup.ipl.IPL_Management_System.repository.TeamRepository;
import com.knoldus.kup.ipl.IPL_Management_System.repository.VenueRepository;
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
@RequestMapping("/matches")
public class MatchController {

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    VenueRepository venueRepository;

    @Autowired
    TeamRepository teamRepository;

//    @Autowired
//    ResultService resultService;
//
//    @Autowired
//    PointService pointService;

    @RequestMapping("/addMatch")
    public String getAddForm(Model model){
        List<Venue> venues= (List<Venue>) venueRepository.findAll();
        model.addAttribute("venues",venues);
        List<Team> teams = (List<Team>) teamRepository.findAll();
        model.addAttribute("teams",teams);
        Match match = new Match();
        match.setMatchWinner("NA");
        match.setResult("NA");
        match.setResult("NA");
        model.addAttribute("match",match);

//        Match match = new Match();
//        model.addAttribute("match",match);
        return "add-match";
    }


    @PostMapping("/save")
    public String addMatch(@Valid Match match, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "add-match";
        }
//        if (match.getTeam1Over() == null && match.getTeam2Over()==null){
//            match.setTeam1Over("Yet to bat");
//            match.setTeam2Over("Yet to bat");
//        }
        matchRepository.save(match);
        return "redirect:/ipl/admin";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid match Id:" + id));

        List<Venue> venues= (List<Venue>) venueRepository.findAll();
        model.addAttribute("venues",venues);

        List<Team> teams = (List<Team>) teamRepository.findAll();
        model.addAttribute("teams",teams);

        model.addAttribute("match", match);
        return "update-match";
    }

    @PostMapping("/update/{id}")
    public String matchUpdate(@PathVariable long id, Match match, Model model){
        matchRepository.save(match);
        return "redirect:/ipl/admin";
    }

    @RequestMapping("/list")
    public String getMatches(Model model){

        List<Match> matches = (List<Match>) matchRepository.findAll();
                Match match = new Match();
        model.addAttribute("match",match);
        model.addAttribute("matchList",matches);
//        System.out.println(matches.stream().findFirst().get().getTeam().getPlayers().stream().findFirst().get().getName());
        return "match-details";
    }

    @RequestMapping("/test")
    public String list(Model model){
        List<Match> matches = (List<Match>) matchRepository.findAll();
        model.addAttribute("matches", matches);
        return "match-dashboard";
    }

    @GetMapping("/delete/{id}")
    public String deleteMatch(@PathVariable("id") long id, Model model) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid match Id:" + id));
        matchRepository.delete(match);
        return "redirect:/ipl/admin";
    }

}
