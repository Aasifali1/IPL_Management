package com.knoldus.kup.ipl.IPL_Management_System.models;

import com.knoldus.kup.ipl.IPL_Management_System.validation.VenueValidate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)


    private Long id;

//    @NotNull(message = "This filed is required")
    private String matchDate;

//    @DateTimeFormat(pattern = "dd/MM/yyyy")
//    private Date birtdate;

    private String tossWinner;
//    private String tossDecision;
    private String matchWinner;
    private String result;
    private String team1Score;
    private String team2Score;

    private String team1Over;
    private String team2Over;

//    private String playerOfMatch;

    @ManyToOne
    @JoinColumn(name = "venue_id")
//    @VenueValidate(message = "This slot is booked")
    private Venue venue;

    @ManyToOne
    @JoinColumn(name = "team_id1")
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "team_id2")
    private Team team2;


    public String getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(String team1Score) {
        this.team1Score = team1Score;
    }

    public String getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(String team2Score) {
        this.team2Score = team2Score;
    }

    public Long getId() {
        return id;
    }

    public String getTeam1Over() {
        return team1Over;
    }

    public void setTeam1Over(String team1Over) {
//        if (!team1Over.equals(null)){
//            this.team1Over = team1Over;
//        }
//        this.team1Over = "Yet to bat";
        this.team1Over = team1Over;

    }

    public String getTeam2Over() {
        return team2Over;
    }

    public void setTeam2Over(String team2Over) {
        this.team2Over = team2Over;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        if(!matchDate.contains("PM") && !matchDate.contains("AM")){
            Date parsedDate = null;
            try {
                parsedDate = inputFormat.parse(matchDate);
                String formattedDate = outputFormat.format(parsedDate);
                this.matchDate = formattedDate;
                System.out.println("hello---------------------------"+formattedDate+"  ----"+this.matchDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else {
            this.matchDate = matchDate;
        }
//        this.matchDate = matchDate;
    }

    public String getTossWinner() {
        return tossWinner;
    }

    public void setTossWinner(String tossWinner) {
        this.tossWinner = tossWinner;
    }

    public String getMatchWinner() {
        return matchWinner;
    }

    public void setMatchWinner(String matchWinner) {
        this.matchWinner = matchWinner;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }
}
