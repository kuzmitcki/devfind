package com.intinctools.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Getter
@Setter
public class WorkDay {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Time start;

    private Time end;

    private int schedule;

    @OneToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    public WorkDay(Time start, Time end, int schedule) {
        this.start = start;
        this.end = end;
        this.schedule = schedule;
    }
}
