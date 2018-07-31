package com.instinctools.entities.devEntities;

import com.instinctools.entities.userEntites.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "developer_id")
    private Set<Specialization> specializations;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.LAZY)
    @JoinColumn(name = "developer_id")
    private Set<Education> education = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "developer_id")
    private Set<WorkExperience> workExperiences;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "desiredJob_id")
    private DesiredJob desiredJob;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Override
    public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Developer developer = (Developer) o;
                return Objects.equals(id, developer.id);
        }

    @Override
    public int hashCode() {
                return Objects.hash(id);
        }
}
