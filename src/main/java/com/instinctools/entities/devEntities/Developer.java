package com.instinctools.entities.devEntities;

import com.instinctools.entities.userEntites.User;
import lombok.Data;
import org.hibernate.annotations.Type;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    private boolean enable;

    private boolean jobExperience;

    private String country;

    private String city;

    @Type(type = "text")
    private String summary;

    private String zipPostalCode;

    private String telephone;

    @Type(type = "text")
    private String additionalInformation;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "developer_id")
    private Set<Specialization> specializations;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "developer_id")
    private Set<Education> education = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "developer_id")
    private Set<WorkExperience> workExperiences;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "desiredJob_id")
    private DesiredJob desiredJob;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}


