package com.intinctools.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class WorkDay {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int start;

    private int end;

    private int schedule;

    @OneToOne(mappedBy = "workDay")
    private User user;

    public WorkDay(int start, int end, int schedule) {
        this.start = start;
        this.end = end;
        this.schedule = schedule;
    }
}
