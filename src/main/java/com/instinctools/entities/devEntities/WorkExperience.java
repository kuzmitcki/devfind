package com.instinctools.entities.devEntities;


import lombok.Data;
import org.hibernate.annotations.Type;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.GenerationType;

@Entity
@Data
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

    @Override
    public String toString() {
        return "WorkExperience{" +
                "jobTitle='" + jobTitle + '\'' +
                ", company='" + company + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
