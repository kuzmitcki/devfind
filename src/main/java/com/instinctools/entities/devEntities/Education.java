package com.instinctools.entities.devEntities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String degree;

    private String place;

    private String fieldOfStudy;

    private String cityOfEducation;

    private String monthFrom;

    private String monthTo;

    private String yearFrom;

    private String yearTo;

    @ManyToOne(fetch = FetchType.LAZY)
    private Developer developer;

    @Override
    public String toString() {
        return "Education{" +
                "id=" + id +
                ", degree='" + degree + '\'' +
                ", place='" + place + '\'' +
                ", fieldOfStudy='" + fieldOfStudy + '\'' +
                ", cityOfEducation='" + cityOfEducation + '\'' +
                ", monthFrom='" + monthFrom + '\'' +
                ", monthTo='" + monthTo + '\'' +
                ", yearFrom='" + yearFrom + '\'' +
                ", yearTo='" + yearTo + '\'' +
                '}';
    }
}