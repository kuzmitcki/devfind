package com.intinctools.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String skills;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    private boolean workEnable;


}
