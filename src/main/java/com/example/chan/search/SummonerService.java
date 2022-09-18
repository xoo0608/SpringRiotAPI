package com.example.chan.search;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SummonerService {

    private final SummonerRepository Summonerrepository;

    public void create(Summoner summoner){
        this.Summonerrepository.save(summoner);
    }
    public Optional<Summoner> findname(String name){
        Optional<Summoner> summo = Optional.ofNullable(Summonerrepository.findByName(name));
        return summo;

    }
}
