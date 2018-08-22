package com.instinctools.entities.devEntities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.GenerationType;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Specialization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String skill;

    private String experience;

    @ManyToOne(fetch = FetchType.LAZY)
    private Developer developer;

    public Specialization(final  String skill, final  String experience, final Developer developer) {
        this.skill = skill;
        this.experience = experience;
        this.developer = developer;
    }

    @Override
    public String toString() {
        return skill;
    }
}
