package com.instinctools.entities.devEntities;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.GenerationType;


@Entity
@Data
public class Specialization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String skill;

    private String experience;

    @ManyToOne(fetch = FetchType.LAZY)
    private Developer developer;

    @Override
    public String toString() {
        return skill;
    }
}
