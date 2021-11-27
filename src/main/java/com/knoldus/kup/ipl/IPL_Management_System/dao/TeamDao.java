package com.knoldus.kup.ipl.IPL_Management_System.dao;

import com.knoldus.kup.ipl.IPL_Management_System.models.Player;
import com.knoldus.kup.ipl.IPL_Management_System.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

//public interface TeamDao extends CrudRepository<Team, Long> {
////    Team findByTeamName(String name);
//
//}
public interface TeamDao extends JpaRepository<Team, Long> {
//    Team findByTeamName(String name);

}