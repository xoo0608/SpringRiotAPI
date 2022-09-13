package com.example.chan.champion;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public final class Champion {
    @Id
    private Integer id;

    @Column(length = 200)
    private String ename;

    @Column(length = 200)
    private String kname;

    @Override
    public String toString(){
        return id + ", " + ename  + ", " + kname;
    }
}
