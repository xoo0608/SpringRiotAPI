package com.example.chan.champion;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Championmastery {

    private String championkName;
    private String championName;
    private int championId;
    private int championLevel;
    private int championPoints;

    public Championmastery(){

    }

    public Championmastery(String championkName ,String championName, int championId, int championLevel, int championPoints){
        super();
        this.championkName = championkName;
        this.championName = championName;
        this.championId = championId;
        this.championLevel = championLevel;
        this.championPoints = championPoints;
    }

    @Override
    public String toString(){
        return "Championmastery [championName="+  championName + ", championId" + championId + ", championLevel=" + championLevel + ", championPoints=" + championPoints + "]";
    }
}
