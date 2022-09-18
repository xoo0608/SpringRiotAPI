package com.example.chan.search;

import com.example.chan.champion.Champion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SummonerRepository extends JpaRepository<Summoner, Integer> {
    Summoner findByName(String name);

}