package com.example.chan.champion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChampionService {

    private final ChampionRepository championRepository;

    public void create(Champion champion){
        this.championRepository.save(champion);
    }

    public Optional<Champion> findChamname(int id){
        Optional<Champion> cham = championRepository.findById(id);
        return cham;

    }
}
