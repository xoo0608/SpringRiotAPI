package com.example.chan.champion;

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

    public void setChampionkName(String championkName){
        this.championkName =championkName;
    }
    public void setChampionName(String championName){
        this.championName =championName;
    }
    public void setChampionId(int championId){
        this.championId = championId;
    }
    public void setChampionLevel (int championLevel){
        this.championLevel = championLevel ;
    }
    public void setChampionPoints(int championPoints){
        this.championPoints = championPoints;
    }

    public String getChampionkName(){ return championkName; }
    public String getChampionName(){ return championName; }
    public int getChampionId(){
        return championId;
    }
    public int getChampionLevel(){
        return championLevel;
    }
    public int getChampionPoints(){
        return championPoints;
    }

    @Override
    public String toString(){
        return "Championmastery [championName="+  championName + ", championId" + championId + ", championLevel=" + championLevel + ", championPoints=" + championPoints + "]";
    }
}
