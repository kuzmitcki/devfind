package com.instinctools.entities.devEntities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class WorkExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String jobTitle;

    private String company;

    private String city;

    private String monthFrom;

    private String monthTo;

    private String yearFrom;

    private String yearTo;

    @Type(type = "text")
    private String description;

    @ManyToOne
    private Developer developer;

    public WorkExperience(String jobTitle, String company, String city, String monthFrom, String monthTo,
                          String yearFrom, String yearTo, String description, Developer developer) {
        this.jobTitle = jobTitle;
        this.company = company;
        this.city = city;
        this.monthFrom = monthFrom;
        this.monthTo = monthTo;
        this.yearFrom = yearFrom;
        this.yearTo = yearTo;
        this.description = description;
        this.developer = developer;
    }
}
