package com.example.chan.search;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Summoner {

    @Column
    private int profileIconId;
    @Id
    @Column(columnDefinition = "TEXT")
    private String name;
    @Column(columnDefinition = "TEXT")
    private String puuid;
    @Column
    private long summonerLevel;
    @Column
    private long revisionDate;
    @Column(columnDefinition = "TEXT")
    private String id;
    @Column(columnDefinition = "TEXT")
    private String accountId;
    @Column
    private int win;
    @Column
    private int lose;

    @Override
    public String toString() {
        return "Summoner [profileIconId=" + profileIconId + ", name=" + name + ", puuid=" + puuid + ", summonerLevel="
                + summonerLevel + ", revisionDate=" + revisionDate + ", id=" + id + ", accountId=" + accountId + "]";
    }


}