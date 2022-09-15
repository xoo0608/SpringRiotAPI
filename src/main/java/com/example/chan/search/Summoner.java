package com.example.chan.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Summoner {
    private int profileIconId;
    private String name;
    private String puuid;
    private long summonerLevel;
    private long revisionDate;
    private String id;
    private String accountId;

    public Summoner() {

    }

    public Summoner(int profileIconId, String name, String puuid, long summonerLevel, long revisionDate, String id,
                    String accountId) {
        super();
        this.profileIconId = profileIconId;
        this.name = name;
        this.puuid = puuid;
        this.summonerLevel = summonerLevel;
        this.revisionDate = revisionDate;
        this.id = id;
        this.accountId = accountId;
    }
    @Override
    public String toString() {
        return "Summoner [profileIconId=" + profileIconId + ", name=" + name + ", puuid=" + puuid + ", summonerLevel="
                + summonerLevel + ", revisionDate=" + revisionDate + ", id=" + id + ", accountId=" + accountId + "]";
    }


}