package com.knoldus.kup.ipl.IPL_Management_System.services;

import com.knoldus.kup.ipl.IPL_Management_System.models.Match;
import com.knoldus.kup.ipl.IPL_Management_System.models.PointTable;
import com.knoldus.kup.ipl.IPL_Management_System.repository.MatchRepository;
import com.knoldus.kup.ipl.IPL_Management_System.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class UpdateResultService {
    @Autowired
    PointRepository pointRepository;

    @Autowired
    MatchRepository matchRepository;

    private static final DecimalFormat df = new DecimalFormat("0.000");

    public void updatePointTable(Match match){

        long team1Id = match.getTeam1().getId();
        long team2Id = match.getTeam2().getId();

        Match preMatch = matchRepository.findById(match.getId()).get();

        PointTable pointTableTeam1 = pointRepository.findByTeamId(team1Id);
        PointTable pointTableTeam2 = pointRepository.findByTeamId(team2Id);

//     --------------------------   pre run rate-------------------------------------------
//        String[] team1Ar=;
//        String[] team2Ar=;
        double team1PreOver = Double.parseDouble(match.getTeam1Over());
        double team2PreOver = Double.parseDouble(match.getTeam2Over());;

        int team1PreRuns = Integer.parseInt(preMatch.getTeam1Score().split("/")[0]);
        int team2PreRuns = Integer.parseInt(preMatch.getTeam2Score().split("/")[0]);

        double preRunRate1 = team1PreRuns/team1PreOver;
        double preRunRate2 = team2PreRuns/team2PreOver;

//  ---------------------------------------------------------------------
        double team1Over = Double.parseDouble(match.getTeam1Over());
        double team2Over = Double.parseDouble(match.getTeam2Over());;

        String[] team1Arr=match.getTeam1Score().split("/");
        String[] team2Arr=match.getTeam2Score().split("/");

        int team1Runs=Integer.parseInt(team1Arr[0]);
        int team2Runs=Integer.parseInt(team2Arr[0]);

        double runRate1 = team1Runs/team1Over;
        double runRate2 = team2Runs/team2Over;

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
//----------------------- If Team-1 Wins pre match-------------------------

        if(preMatch.getTeam1().getName().equals(match.getMatchWinner())){
            System.out.println("dfsfsf------"+pointTableTeam1.getId());
            pointTableTeam1.setWin(pointTableTeam1.getWin()-1);
            pointTableTeam1.setTotalMatch(pointTableTeam1.getTotalMatch()-1);
            pointTableTeam1.setPoints(pointTableTeam1.getPoints()-2);
//            pointTableTeam1.setNetRunRate(0);
//            pointRepository.save(pointTableTeam1);
        }else {
            pointTableTeam1.setTotalMatch(pointTableTeam1.getTotalMatch()-1);
            pointTableTeam1.setLose(pointTableTeam1.getLose()-1);
        }

        if(preMatch.getTeam2().getName().equals(match.getMatchWinner())){
//            pointTableTeam2 = pointRepository.findByTeamId(team2Id);
            System.out.println("winner kkr ------"+pointTableTeam2.getTeam().getName());
            pointTableTeam2.setWin(pointTableTeam2.getWin()-1);
            pointTableTeam2.setTotalMatch(pointTableTeam2.getTotalMatch()-1);
            pointTableTeam2.setPoints(pointTableTeam2.getPoints()-2);
//            pointTableTeam2.setNetRunRate(0);
//            pointRepository.save(pointTableTeam2);
        }else {
            pointTableTeam2.setTotalMatch(pointTableTeam2.getTotalMatch()-1);
            pointTableTeam2.setLose(pointTableTeam2.getLose()-1);
        }
//   -------------------------------------------------------------------------
        if(match.getTeam1().getName().equals(match.getMatchWinner())){
            System.out.println("dfsfsf------"+pointTableTeam1.getId());
            pointTableTeam1.setWin(pointTableTeam1.getWin()+1);
            pointTableTeam1.setTotalMatch(pointTableTeam1.getTotalMatch()+1);
            pointTableTeam1.setPoints(pointTableTeam1.getPoints()+2);
//            pointTableTeam1.setNetRunRate(0);
//            pointRepository.save(pointTableTeam1);
        }else {
            pointTableTeam1.setTotalMatch(pointTableTeam1.getTotalMatch()+1);
            pointTableTeam1.setLose(pointTableTeam1.getLose()+1);
        }

        if(match.getTeam2().getName().equals(match.getMatchWinner())){
//            pointTableTeam2 = pointRepository.findByTeamId(team2Id);
            System.out.println("winner kkr ------"+pointTableTeam2.getTeam().getName());
            pointTableTeam2.setWin(pointTableTeam2.getWin()+1);
            pointTableTeam2.setTotalMatch(pointTableTeam2.getTotalMatch()+1);
            pointTableTeam2.setPoints(pointTableTeam2.getPoints()+2);
//            pointTableTeam2.setNetRunRate(0);
            pointRepository.save(pointTableTeam2);
        }else {
            pointTableTeam2.setTotalMatch(pointTableTeam2.getTotalMatch()+1);
            pointTableTeam2.setLose(pointTableTeam2.getLose()+1);
        }

        System.out.println("Pre:----------- "+pointTableTeam1.getNetRunRate()+"Current: "
                +(runRate1 - runRate2)+"Total: "+ (pointTableTeam1.getNetRunRate()+(runRate1 - runRate2)) );
        System.out.println("Pre:----------- "+pointTableTeam2.getNetRunRate()+"Current: "
                +(runRate2 - runRate1)+ "Total: "+ (pointTableTeam2.getNetRunRate()+(runRate2 - runRate1)) );

        double preNetRunRateTeam1 =(preRunRate1-preRunRate2) - pointTableTeam1.getNetRunRate();
        pointTableTeam1.setNetRunRate(Double.valueOf(df.format(preNetRunRateTeam1)));

        double preNetRunRateTeam2 = (preRunRate2-preRunRate1) - pointTableTeam2.getNetRunRate();
        pointTableTeam2.setNetRunRate(Double.valueOf(df.format(preNetRunRateTeam2)));

        double netRunRateTeam1 = (runRate1 - runRate2) + pointTableTeam1.getNetRunRate() - preNetRunRateTeam1;
        pointTableTeam1.setNetRunRate(Double.valueOf(df.format(netRunRateTeam1)));

        double netRunRateTeam2 = (runRate2 - runRate1) + pointTableTeam2.getNetRunRate() -preNetRunRateTeam2;
        pointTableTeam2.setNetRunRate(Double.valueOf(df.format(netRunRateTeam2)));

        pointRepository.save(pointTableTeam1);
        pointRepository.save(pointTableTeam2);
    }
}
