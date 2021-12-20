package com.knoldus.kup.ipl.IPL_Management_System.services;

import com.knoldus.kup.ipl.IPL_Management_System.models.Match;
import com.knoldus.kup.ipl.IPL_Management_System.models.Team;
import com.knoldus.kup.ipl.IPL_Management_System.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultService {
    @Autowired
    MatchRepository matchRepository;

    public void getResult(Match match)
    {
        int team1Runs = Integer.parseInt(match.getTeam1Score());
        int team2Runs = Integer.parseInt(match.getTeam2Score());
        int team1Wickets = Integer.parseInt(match.getTeam1Wickets());
        int team2Wickets = Integer.parseInt(match.getTeam2Wickets());

        String result;
        String teamWinner;
        Team tossWinner = match.getTossWinnerTeam();
        String tossChoice = match.getTossChoice();

        if(team1Runs>team2Runs){
            System.out.println("team1"+tossChoice);
            teamWinner = match.getTeam1().getName();
            if(tossChoice.equals("batting") ){         //won by runs
                System.out.println(teamWinner+" won by "+(team1Runs-team2Runs)+" runs");
                result = teamWinner+" won by "+(team1Runs-team2Runs)+" runs";
            }else { //won by wickets
                result = teamWinner+" won by "+(10-team1Wickets)+" wickets";
            }
        }
        else {
            System.out.println("team2"+tossChoice);
            teamWinner = match.getTeam2().getName();
            if(tossChoice.equals("batting")){       //won by runs
                result = teamWinner+" won by "+(team2Runs-team1Runs)+" runs";
            }else { //won by wickets
                result=teamWinner+" won by "+(10-team2Wickets)+" wickets";
            }
        }

        match.setMatchWinner(teamWinner);
        match.setResult(result);
        matchRepository.save(match);
    }
}
