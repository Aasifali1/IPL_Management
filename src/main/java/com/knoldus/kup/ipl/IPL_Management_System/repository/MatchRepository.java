package com.knoldus.kup.ipl.IPL_Management_System.repository;

import com.knoldus.kup.ipl.IPL_Management_System.models.Match;
import com.knoldus.kup.ipl.IPL_Management_System.models.Venue;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface MatchRepository extends CrudRepository<Match, Long> {

    public Set<Match> findByVenueId(Long id);
}
