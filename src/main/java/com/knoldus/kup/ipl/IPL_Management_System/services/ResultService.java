package com.knoldus.kup.ipl.IPL_Management_System.services;

import com.knoldus.kup.ipl.IPL_Management_System.models.Match;
import com.knoldus.kup.ipl.IPL_Management_System.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultService {
    @Autowired
    MatchRepository matchRepository;

    public void getResult(Match match)
    {
//        String team1Score = match.get
        String[] team1Arr=match.getTeam1Score().split("/");
        String[] team2Arr=match.getTeam2Score().split("/");

        int team1Runs=Integer.parseInt(team1Arr[0]);
        int team2Runs=Integer.parseInt(team2Arr[0]);
        int team1Wickets=Integer.parseInt(team1Arr[1]);
        int team2Wickets=Integer.parseInt(team2Arr[1]);

        String result;
        String teamWinner;

        //won by wickets
        if(team1Runs>team2Runs){
            result = match.getTeam1().getName()+" won by "+(team1Runs-team2Runs)+" runs";
            teamWinner = match.getTeam1().getName();
        }
        else {
            teamWinner = match.getTeam2().getName();
            result=match.getTeam2().getName()+" won by "+(10-team2Wickets)+" wickets";
        }
        match.setMatchWinner(teamWinner);
        match.setResult(result);
        matchRepository.save(match);
    }
}
