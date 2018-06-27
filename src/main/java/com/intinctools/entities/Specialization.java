package com.intinctools.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String skills;

    @ManyToOne
    @JoinColumn(name = "dev_id" , nullable = false)
    private Developer developer;


}
