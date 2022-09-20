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
        Optional<Summoner> summo = Optional.ofNullable(this.Summonerrepository.findByName(name));
        return summo;

    }

    public void changeSummoner(Summoner summoner){
        Optional<Summoner> Su = this.findname(summoner.getName());
        Summoner s = Su.get();
        s.setWin(summoner.getWin());
        s.setLose(summoner.getLose());
        this.Summonerrepository.save(s);
    }
}
