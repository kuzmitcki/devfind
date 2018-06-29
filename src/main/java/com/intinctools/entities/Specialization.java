package com.intinctools.entities;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String skills;

    public Specialization(String skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return skills ;
    }
}
