package com.intinctools.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Data
class WorkDay {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Time start;

    private Time end;

    private int schedule;
    
}
