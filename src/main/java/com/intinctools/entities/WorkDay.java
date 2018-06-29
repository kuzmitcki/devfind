package com.intinctools.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class WorkDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Time start;

    private Time end;

    private String schedule;


    public WorkDay(Time start, Time end, String schedule) {
        this.start = start;
        this.end = end;
        this.schedule = schedule;
    }
}
