package com.intinctools.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

@Entity
@Data
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String password;

    @OneToMany(mappedBy = "developer")
    private Set<Specialization> specializations;

    private boolean enable;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private WorkDay workDay;

    @Email
    private String  email;



}
