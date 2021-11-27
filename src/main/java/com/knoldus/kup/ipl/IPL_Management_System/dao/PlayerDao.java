package com.knoldus.kup.ipl.IPL_Management_System.dao;

import com.knoldus.kup.ipl.IPL_Management_System.models.Player;
import com.knoldus.kup.ipl.IPL_Management_System.models.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface PlayerDao extends CrudRepository<Player, Long> {
    Set<Player> findByTeamId(long team_id);

}
