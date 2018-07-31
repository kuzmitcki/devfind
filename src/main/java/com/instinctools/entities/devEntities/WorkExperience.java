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

    @ManyToOne(fetch = FetchType.LAZY)
    private Developer developer;
}
