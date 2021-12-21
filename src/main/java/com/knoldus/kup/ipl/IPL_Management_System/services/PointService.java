package com.knoldus.kup.ipl.IPL_Management_System.services;

import com.knoldus.kup.ipl.IPL_Management_System.models.Match;
import com.knoldus.kup.ipl.IPL_Management_System.models.PointTable;
import com.knoldus.kup.ipl.IPL_Management_System.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class PointService {

    @Autowired
    PointRepository pointRepository;

    private static final DecimalFormat df = new DecimalFormat("0.000");

    public void updatePointTable(Match match){

        double team1Over = Double.parseDouble(match.getTeam1Over());
        double team2Over = Double.parseDouble(match.getTeam2Over());;

        long team1Id = match.getTeam1().getId();
        long team2Id = match.getTeam2().getId();

        PointTable pointTableTeam1 = pointRepository.findByTeamId(team1Id);
        PointTable pointTableTeam2 = pointRepository.findByTeamId(team2Id);

        String[] team1Arr=match.getTeam1Score().split("/");
        String[] team2Arr=match.getTeam2Score().split("/");

        int team1Runs=Integer.parseInt(team1Arr[0]);
        int team2Runs=Integer.parseInt(team2Arr[0]);

          double runRate1 = team1Runs/team1Over;
          double runRate2 = team2Runs/team2Over;

        if (pointTableTeam1==null) {
            System.out.println("point- on null---------------"+match.getResult());
            pointTableTeam1=new PointTable();
            pointTableTeam1.setTeam(match.getTeam1());
            pointTableTeam1.setTotalMatch(0);
            pointTableTeam1.setWin(0);
            pointTableTeam1.setLose(0);
            pointTableTeam1.setPoints(0);
            pointTableTeam1.setNetRunRate(0);
            pointRepository.save(pointTableTeam1);
        }

        if (pointTableTeam2==null){
            System.out.println("Runnig from pointservice----------------"+match.getResult());
            pointTableTeam2=new PointTable();
            pointTableTeam2.setTeam(match.getTeam2());
            pointTableTeam2.setTotalMatch(0);
            pointTableTeam2.setWin(0);
            pointTableTeam2.setLose(0);
            pointTableTeam2.setNetRunRate(0);
            pointTableTeam2.setPoints(0);
            pointRepository.save(pointTableTeam2);
        }

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
//            pointRepository.save(pointTableTeam2);
        }else {
            pointTableTeam2.setTotalMatch(pointTableTeam2.getTotalMatch()+1);
            pointTableTeam2.setLose(pointTableTeam2.getLose()+1);

        }

        System.out.println("Pre:----------- "+pointTableTeam1.getNetRunRate()+"Current: "
                +(runRate1 - runRate2)+"Total: "+ (pointTableTeam1.getNetRunRate()+(runRate1 - runRate2)) );
        System.out.println("Pre:----------- "+pointTableTeam2.getNetRunRate()+"Current: "
                +(runRate2 - runRate1)+ "Total: "+ (pointTableTeam2.getNetRunRate()+(runRate2 - runRate1)) );


        double netRunRateTeam1 = (runRate1 - runRate2) + pointTableTeam1.getNetRunRate();
        pointTableTeam1.setNetRunRate(Double.valueOf(df.format(netRunRateTeam1)));

        double netRunRateTeam2 = (runRate2 - runRate1) + pointTableTeam2.getNetRunRate();
        pointTableTeam2.setNetRunRate(Double.valueOf(df.format(netRunRateTeam2)));


        pointRepository.save(pointTableTeam1);
        pointRepository.save(pointTableTeam2);

    }
}
